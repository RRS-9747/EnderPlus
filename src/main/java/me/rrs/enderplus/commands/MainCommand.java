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
            if (args.length == 0) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bEnderPlus " + EnderPlus.getInstance().getDescription().getVersion() + "&r by RRS"));
            } else {
                switch (args[0]) {
                    case "help":
                        player.sendMessage("" + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "----------------------------------------------------");
                        player.sendMessage(ChatColor.BLUE + " EnderPlus " + ChatColor.GOLD + "by RRS" + ChatColor.GRAY + " | " + ChatColor.RED + "Ultimate plugin");
                        player.sendMessage("" + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "----------------------------------------------------");
                        player.sendMessage(ChatColor.GREEN + " Commands:");
                        player.sendMessage(ChatColor.GRAY + "  /enderplus help" + ChatColor.YELLOW + " -> Obvious, right?");
                        player.sendMessage(ChatColor.GRAY + "  /enderplus reload" + ChatColor.YELLOW + " -> Refresh the plugin");
                        player.sendMessage(ChatColor.GRAY + "  /enderchest" + ChatColor.YELLOW + " -> Your very own enderchest");
                        player.sendMessage(ChatColor.GRAY + "  /enderchest <player>" + ChatColor.YELLOW + " -> Peek into someone else's enderchest");
                        player.sendMessage("" + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "----------------------------------------------------");
                        break;
                    case "reload":
                        if (player.hasPermission("enderplus.reload")) {
                            try {
                                EnderPlus.getLang().reload();
                                EnderPlus.getConfiguration().reload();
                                lang.msg("&a&l" + EnderPlus.getLang().getString("Prefix") + "&r", "Reload", player);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            lang.noPerm(player);
                        }
                        break;

                    default:
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bEnderPlus " + EnderPlus.getInstance().getDescription().getVersion() + "&r by RRS"));
                        break;
                }
            }
        } else {
            if (args.length > 0) {
                if (args[0].equals("reload")) {
                    try {
                        EnderPlus.getLang().reload();
                        EnderPlus.getConfiguration().reload();
                        Bukkit.getLogger().info(EnderPlus.getLang().getString("Reload"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                Bukkit.getLogger().info("EnderPlus " + EnderPlus.getInstance().getDescription().getVersion() + " by RRS");
            }
        }
        return true;
    }
}