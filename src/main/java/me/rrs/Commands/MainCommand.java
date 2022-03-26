package me.rrs.Commands;

import me.rrs.EnderPlus;
import me.rrs.Util.Lang;
import me.rrs.Util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Util util = new Util();
        Lang lang = new Lang();

        if (sender instanceof Player){
            Player player = (Player) sender;

            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("help")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3EnderPlus&r plugin by RRS."));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&9>&r &l/enderplus help&r -> You already discovered it!"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&9>&r &l/enderplus reload&r -> Reload plugin"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&9>&r &l/enderchest&r -> Access your enderchest"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&9>&r &l/enderchest <player>&r -> Access other players enderchest"));
                }
                if (args[0].equalsIgnoreCase("reload")){
                    if (player.hasPermission("enderplus.reload")) {
                        try {
                            EnderPlus.getLang().reload();
                            EnderPlus.getConfiguration().reload();
                            lang.msg("&a&l[EnderPlus]&r", "Reload", player);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }else lang.noPerm(player);
                }

            }else player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&bEnderPlus "+ EnderPlus.getInstance().getDescription().getVersion()+ "&r by RRS"));


        }else{
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")){
                try {
                    EnderPlus.getLang().reload();
                    EnderPlus.getConfiguration().reload();
                    Bukkit.getLogger().info(EnderPlus.getLang().getString("Reload"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else Bukkit.getLogger().warning("&bEnderPlus "+ EnderPlus.getInstance().getDescription().getVersion()+ "&r by RRS");

        }

        return true;
    }
}
