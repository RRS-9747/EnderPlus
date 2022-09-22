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

                        if (player.hasPermission("enderplus.lvl.6")) {
                            invUtils.otherEnderInv(p, player,54);
                        } else if (player.hasPermission("enderplus.lvl.5")) {
                            invUtils.otherEnderInv(p, player, 45);
                        } else if (player.hasPermission("enderplus.lvl.4")) {
                            invUtils.otherEnderInv(p, player, 36);
                        } else if (player.hasPermission("enderplus.lvl.3")) {
                            invUtils.otherEnderInv(p, player,27);
                        } else if (player.hasPermission("enderplus.lvl.2")) {
                            invUtils.otherEnderInv(p, player, 18);
                        } else if (player.hasPermission("enderplus.lvl.1")) {
                            invUtils.otherEnderInv(p, player, 9);

                        } else lang.msg("&c&l" + EnderPlus.getLang().getString("Prefix") +"&r", "No-Echest", p);

                    } else lang.msg("&c&l" + EnderPlus.getLang().getString("Prefix") +"&r", "NoPlayer", p);
                } else lang.noPerm(p);

            }else {

                if (p.hasPermission("enderplus.ec.own")) {

                    if (p.hasPermission("enderplus.lvl.6")) {
                        invUtils.ownEnderInv(p, 54);
                    } else if (p.hasPermission("enderplus.lvl.5")) {
                        invUtils.ownEnderInv(p, 45);
                    } else if (p.hasPermission("enderplus.lvl.4")) {
                        invUtils.ownEnderInv(p, 36);
                    } else if (p.hasPermission("enderplus.lvl.3")) {
                        invUtils.ownEnderInv(p, 27);
                    } else if (p.hasPermission("enderplus.lvl.2")) {
                        invUtils.ownEnderInv(p, 18);
                    } else if (p.hasPermission("enderplus.lvl.1")) {
                        invUtils.ownEnderInv(p, 9);

                    } else lang.msg("&c&l" + EnderPlus.getLang().getString("Prefix") +"&r", "No-Echest", p);

                }
            }

        } else lang.pcmd();
        return true;
    }
}
