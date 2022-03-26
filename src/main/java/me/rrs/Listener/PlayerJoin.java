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
    String newVersion = updateAPI.getGithubVersion("RRS-9747", "EnderPlus");
    boolean hasUpdateGitHub = updateAPI.hasGithubUpdate("RRS-9747", "EnderPlus");
    boolean updateChecker = EnderPlus.getConfiguration().getBoolean("Config.Update-Checker");

    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("dd/MM");
    String currentDate = dateFormat.format(date);

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {

        if (event.getPlayer().getEnderChest().getContents() != null){
            ItemStack[] itemstacks = event.getPlayer().getEnderChest().getContents();
            Util.Echest.put(event.getPlayer().getName(), itemstacks);
            event.getPlayer().getEnderChest().clear();
        }


        if (event.getPlayer().hasPermission("enderplus.notify")) {
            if (updateChecker && hasUpdateGitHub) {
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "You are using EnderPlus" + EnderPlus.getInstance().getDescription().getVersion()));
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "However version " + newVersion + " is available."));
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "You can download it from: https://bit.ly/enderplus"));
                if (event.getPlayer().hasPermission("enderplus.update") && Bukkit.getPluginManager().isPluginEnabled("RRSUpdater")) {
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "You can also do /update to update the plugin, but suggest to download from spigot ;)"));
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
