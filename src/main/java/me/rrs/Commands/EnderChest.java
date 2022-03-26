package me.rrs.Commands;

import me.rrs.Util.Lang;
import me.rrs.Util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderChest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Util util = new Util();
        Lang lang = new Lang();

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                if (player.hasPermission("enderplus.ec.own")) {
                    if (!player.isOp()) {

                        if (player.hasPermission("enderplus.lvl.1")) {
                            util.inv(player, 9);
                        }
                        if (player.hasPermission("enderplus.lvl.2")) {
                            util.inv(player, 18);
                        }
                        if (player.hasPermission("enderplus.lvl.3")) {
                            util.inv(player, 27);
                        }
                        if (player.hasPermission("enderplus.lvl.4")) {
                            util.inv(player, 36);
                        }
                        if (player.hasPermission("enderplus.lvl.5")) {
                            util.inv(player, 45);
                        }
                        if (player.hasPermission("enderplus.lvl.6")) {
                            util.inv(player, 54);
                        }

                    } else {
                        util.inv(player, 54);
                    }
                } else player.sendMessage("You don't have permission to run this command!");

            }
            if (args.length > 0){
                if (player.hasPermission("enderplus.ec.other")) {
                    Player p = Bukkit.getPlayer(args[0]);
                    if (p != null) {
                        if (!p.isOp()) {
                            if (p.hasPermission("enderplus.lvl.1")) {
                                util.inv(p, 9, player);
                            }
                            if (p.hasPermission("enderplus.lvl.2")) {
                                util.inv(p, 18, player);
                            }
                            if (p.hasPermission("enderplus.lvl.3")) {
                                util.inv(p, 27, player);
                            }
                            if (p.hasPermission("enderplus.lvl.4")) {
                                util.inv(p, 36, player);
                            }
                            if (p.hasPermission("enderplus.lvl.5")) {
                                util.inv(p, 45, player);
                            }
                            if (p.hasPermission("enderplus.lvl.6")) {
                                util.inv(p, 54, player);
                            }
                        } else util.inv(p, 54, player);
                    }
                }else player.sendMessage("You don't have permission to run this command!");
            }





        }else lang.pcmd();

        return true;
    }
}
