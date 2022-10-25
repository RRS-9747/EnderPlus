package me.rrs.enderplus.commands;

import me.rrs.enderplus.EnderPlus;
import me.rrs.enderplus.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MainCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        final Lang lang = new Lang();
        if (sender instanceof Player) {
            final Player player = (Player) sender;

            if (0 < args.length) {
                if ("help".equalsIgnoreCase(args[0])) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3EnderPlus&r by RRS."));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9>&r &l/enderplus help&r -> You already discovered it!"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9>&r &l/enderplus reload&r -> Reload plugin"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9>&r &l/enderchest&r -> Access your enderchest"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9>&r &l/enderchest <player>&r -> Access other players enderchest"));
                }
                if ("reload".equalsIgnoreCase(args[0])) {
                    if (player.hasPermission("enderplus.reload")) {
                        try {
                            EnderPlus.getLang().reload();
                            EnderPlus.getConfiguration().reload();
                            lang.msg("&a&l" + EnderPlus.getLang().getString("Prefix") + "&r", "Reload", player);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else lang.noPerm(player);
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bEnderPlus " + EnderPlus.getInstance().getDescription().getVersion() + "&r by RRS"));
            }
        } else {
            if (0 < args.length && "reload".equalsIgnoreCase(args[0])) {
                try {
                    EnderPlus.getLang().reload();
                    EnderPlus.getConfiguration().reload();
                    Bukkit.getLogger().info(EnderPlus.getLang().getString("Reload"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Bukkit.getLogger().info("EnderPlus " + EnderPlus.getInstance().getDescription().getVersion() + " by RRS");
            }
        }


        return true;
    }
}
