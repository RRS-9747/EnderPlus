package me.rrs.commands;

import me.rrs.EnderPlus;
import me.rrs.utils.InvUtils;
import me.rrs.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderChest implements CommandExecutor {
    Lang lang = new Lang();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {

            Player p = (Player) sender;
            InvUtils invUtils = new InvUtils();

            if (args.length > 0) {
                if (p.hasPermission("enderplus.ec.other")) {
                    Player player = Bukkit.getPlayer(args[0]);

                    if (player != null) {
                        boolean opened = false;

                        for (int i = 6; i > 0; i--) {
                            if(p.hasPermission("enderplus.lvl." + i)) {
                                invUtils.otherEnderInv(p, player, i * 9);
                                opened = true;
                                break;
                            }
                        }

                        if(!opened) {
                            lang.msg("&c&l" + EnderPlus.getLang().getString("Prefix") +"&r", "No-Echest", p);
                        }

                    } else lang.msg("&c&l" + EnderPlus.getLang().getString("Prefix") +"&r", "NoPlayer", p);
                } else lang.noPerm(p);

            }else {

                if (p.hasPermission("enderplus.ec.own")) {
                    boolean opened = false;

                    for (int i = 6; i > 0; i--) {
                        if(p.hasPermission("enderplus.lvl." + i)) {
                            invUtils.ownEnderInv(p, i * 9);
                            opened = true;
                            break;
                        }
                    }

                    if(!opened) {
                        lang.msg("&c&l" + EnderPlus.getLang().getString("Prefix") +"&r", "No-Echest", p);
                    }

                }
            }

        } else lang.pcmd();
        return true;
    }
}
