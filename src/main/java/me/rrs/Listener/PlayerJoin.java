package me.rrs.Listener;

import me.rrs.EnderPlus;
import me.rrs.Util.UpdateAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PlayerJoin implements Listener {

    String newVersion;
    boolean hasUpdateGitHub, updateChecker;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {

        newVersion = UpdateAPI.getGithubVersion("RRS-9747", "EnderPlus");
        hasUpdateGitHub = UpdateAPI.hasGithubUpdate("RRS-9747", "EnderPlus");
        updateChecker = EnderPlus.getConfiguration().getBoolean("Config.Update-Checker");

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM");
        String currentDate = dateFormat.format(date);


        if (hasUpdateGitHub && updateChecker) {
            if (event.getPlayer().hasPermission("enderplus.notify")) {
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "You are using EnderPlus" + EnderPlus.getInstance().getDescription().getVersion()));
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "However version " + newVersion + " is available."));
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "You can download it from: https://bit.ly/3D8VM19"));
                if (event.getPlayer().hasPermission("enderplus.update") && Bukkit.getPluginManager().isPluginEnabled("EPUpdater")) {
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
