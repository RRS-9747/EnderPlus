package me.rrs.commands;

import me.rrs.Util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class enderchest implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                if (player.hasPermission("enderplus.ec.own")) {
                    if (!player.isOp()) {

                        if (player.hasPermission("enderplus.lvl.1")) {
                            Util.inv(player, 9);
                        }
                        if (player.hasPermission("enderplus.lvl.2")) {
                            Util.inv(player, 18);
                        }
                        if (player.hasPermission("enderplus.lvl.3")) {
                            Util.inv(player, 27);
                        }
                        if (player.hasPermission("enderplus.lvl.4")) {
                            Util.inv(player, 36);
                        }
                        if (player.hasPermission("enderplus.lvl.5")) {
                            Util.inv(player, 45);
                        }
                        if (player.hasPermission("enderplus.lvl.6")) {
                            Util.inv(player, 54);
                        }

                    } else {
                        Util.inv(player, 54);
                    }
                } else player.sendMessage("You don't have permission to run this command!");

            }
            if (args.length > 0){
                if (player.hasPermission("enderplus.ec.other")) {
                    Player p = Bukkit.getPlayer(args[0]);
                    if (p != null)
                        if (!p.isOp()) {
                            if (p.hasPermission("enderplus.lvl.1")) {
                                Util.inv(p, 9, player);
                            }
                            if (p.hasPermission("enderplus.lvl.2")) {
                                Util.inv(p, 18, player);
                            }
                            if (p.hasPermission("enderplus.lvl.3")) {
                                Util.inv(p, 27, player);
                            }
                            if (p.hasPermission("enderplus.lvl.4")) {
                                Util.inv(p, 36, player);
                            }
                            if (p.hasPermission("enderplus.lvl.5")) {
                                Util.inv(p, 45, player);
                            }
                            if (p.hasPermission("enderplus.lvl.6")) {
                                Util.inv(p, 54, player);
                            }
                        }else Util.inv(p, 54, player);

                }else player.sendMessage("You don't have permission to run this command!");
            }





        }else Bukkit.getLogger().severe("This is a player only command!");

        return true;
    }
}
