package me.rrs.commands;

import me.rrs.EnderPlus;
import me.rrs.utils.InvUtils;
import me.rrs.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EnderChest implements CommandExecutor {

    final Lang lang = new Lang();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        if (sender instanceof Player) {

            final Player p = (Player) sender;

            InvUtils invUtils = new InvUtils();

            final int maxColumns = 6;
            if (0 < args.length) {
                if (p.hasPermission("enderplus.ec.other")) {
                    Player player = Bukkit.getPlayer(args[0]);

                    if (null != player) {
                        boolean opened = false;

                        for (int i = maxColumns; 0 < i; i--) {
                            if (player.hasPermission("enderplus.lvl." + i)) {
                                invUtils.otherEnderInv(p, player, i * 9);
                                opened = true;
                                break;
                            }
                        }
                        if (!opened) Lang.msg("&c&l" + EnderPlus.getLang().getString("Prefix") + "&r", "No-Echest", p);
                    } else Lang.msg("&c&l" + EnderPlus.getLang().getString("Prefix") + "&r", "NoPlayer", p);
                } else lang.noPerm(p);

            } else {
                if (p.hasPermission("enderplus.ec.own")) {
                    boolean opened = false;

                    for (int i = maxColumns; 0 < i; i--) {
                        if (p.hasPermission("enderplus.lvl." + i)) {
                            invUtils.ownEnderInv(p, i * 9);
                            opened = true;
                            break;
                        }
                    }
                    if (!opened) Lang.msg("&c&l" + EnderPlus.getLang().getString("Prefix") + "&r", "No-Echest", p);
                }else lang.noPerm(p);
            }
        } else this.lang.pcmd();
        return true;
    }
}
