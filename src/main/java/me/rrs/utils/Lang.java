package me.rrs.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.rrs.EnderPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Lang {


    public void msg(String prefix, String path, Player player) {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            player.sendMessage(PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', prefix + " " + EnderPlus.getLang().getString(path))));
        } else player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + EnderPlus.getLang().getString(path)));
    }

    public void noPerm(Player player) {
        msg("&c&l" + EnderPlus.getLang().getString("Prefix") + "&r", "Permission-Error", player);
    }

    public void pcmd() {
        Bukkit.getLogger().severe(EnderPlus.getLang().getString("Player-Command"));

    }
}