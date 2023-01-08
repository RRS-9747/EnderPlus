package me.rrs.enderplus.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.rrs.enderplus.EnderPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Lang {


    private final boolean usePlaceholderAPI = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");

    public void msg(String prefix, String path, Player player) {
        String message = ChatColor.translateAlternateColorCodes('&', prefix + "&r " + EnderPlus.getLang().getString(path));
        if (usePlaceholderAPI) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }
        player.sendMessage(message);
    }

    public void noPerm(Player player) {
        msg("&c&l" + EnderPlus.getLang().getString("Prefix") + "&r", "Permission-Error", player);
    }

    public void pcmd() {
        String message = EnderPlus.getLang().getString("Player-Command");
        Bukkit.getLogger().severe(message);
    }
}