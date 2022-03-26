package me.rrs.Util;

import me.clip.placeholderapi.PlaceholderAPI;
import me.rrs.EnderPlus;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Lang {

    public void msg(String prefix, String path, Player player){
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            player.sendMessage(PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', prefix + " " + EnderPlus.getLang().getString(path))));
        }else player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + " " + EnderPlus.getLang().getString(path)));
    }

    public void noPerm(Player player){
        this.msg("&c&l[HeadDrop]&r", "Permission-Error", player);
    }

    public void pcmd(){
        Bukkit.getLogger().severe(ChatColor.translateAlternateColorCodes('&', EnderPlus.getLang().getString("Player-Command")));

    }
}
