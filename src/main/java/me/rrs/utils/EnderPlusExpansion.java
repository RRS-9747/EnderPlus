package me.rrs.utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EnderPlusExpansion extends PlaceholderExpansion {

    public @NotNull String getIdentifier() {
        return "enderplus";
    }

    public @NotNull String getAuthor() {
        return "RRS";
    }

    public @NotNull String getVersion() {
        return "1.0";
    }


    public boolean persist() {
        return true;
    }

    public String onRequest(OfflinePlayer player, String params) {
        if (!params.equalsIgnoreCase("row")) {
            Player p = player.getPlayer();
            int i;
            for (i = 6; 0 < i; i--) {
                if (p != null && p.hasPermission("enderplus.lvl." + i)) {
                    break;
                }
            }
            return String.valueOf(i);
        }
        return null;
    }
}
