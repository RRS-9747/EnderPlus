package me.rrs.Listener;

import me.rrs.EnderPlus;
import me.rrs.Util.UpdateAPI;
import me.rrs.Util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PlayerJoin implements Listener {

    UpdateAPI updateAPI = new UpdateAPI();
    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("dd/MM");
    String currentDate = dateFormat.format(date);

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {

        if (EnderPlus.getConfiguration().getBoolean("Config.Enable")) {
            if (EnderPlus.getConfiguration().getBoolean("Enderchest.Convert-onJoin")) {
                if (event.getPlayer().getEnderChest().getContents() != null) {
                    ItemStack[] itemstacks = event.getPlayer().getEnderChest().getContents();
                    Util.Echest.put(event.getPlayer().getName(), itemstacks);
                    event.getPlayer().getEnderChest().clear();
                }
            }
        }


        if (event.getPlayer().hasPermission("enderplus.notify")) {
            if (EnderPlus.getConfiguration().getBoolean("Config.Update-Checker")) {
                if (updateAPI.hasGithubUpdate("RRS-9747", "EnderPlus")) {
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "You are using EnderPlus" + EnderPlus.getInstance().getDescription().getVersion()));
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "However version " + updateAPI.getGithubVersion("RRS-9747", "EnderPlus") + " is available."));
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "You can download it from: https://bit.ly/enderplus"));
                }

            }
        }


        if (currentDate.equals("23/3")){
            if (event.getPlayer().hasPermission("enderplus.notify")){
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[EnderPlus]&r Today is my Birthday :D Leave a review on spigot as a gift :3"));
            }
        }


    }



}
