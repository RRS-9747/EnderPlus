package me.rrs.enderplus.commands;

import me.rrs.enderplus.EnderPlus;
import me.rrs.enderplus.utils.Lang;
import me.rrs.enderplus.utils.InvUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EnderChest implements CommandExecutor {

    private final Lang lang = new Lang();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        if (sender instanceof Player) {
            final Player p = (Player) sender;
            final InvUtils invUtils = new InvUtils();

            if (0 < args.length) {
                if (p.hasPermission("enderplus.ec.other") || p.hasPermission("enderplus.ec.other.edit")) {
                    final Player holder = Bukkit.getPlayer(args[0]);
                    if (null != holder) {
                        boolean opened = false;
                        for (int i = 6; 0 < i; i--) {
                            if (holder.hasPermission("enderplus.lvl." + i)) {
                                invUtils.otherEnderInv(p, holder, i * 9, EnderPlus.getConfiguration().getString("EnderChest.Name.row-" + i));
                                opened = true;
                                break;
                            }
                        }
                        if (!opened) lang.msg("&c&l" + EnderPlus.getLang().getString("Prefix") + "&r", "No-Echest", p);
                    } else lang.msg("&c&l" + EnderPlus.getLang().getString("Prefix") + "&r", "NoPlayer", p);
                } else lang.noPerm(p);

            } else {
                if (p.hasPermission("enderplus.ec.own")) {
                    boolean opened = false;
                    for (int i = 6; 0 < i; i--) {
                        if (p.hasPermission("enderplus.lvl." + i)) {
                            invUtils.ownEnderInv(p, i * 9, EnderPlus.getConfiguration().getString("EnderChest.Name.row-" + i));
                            opened = true;
                            break;
                        }
                    }
                    if (!opened) lang.msg("&c&l" + EnderPlus.getLang().getString("Prefix") + "&r", "No-Echest", p);
                }else lang.noPerm(p);
            }
        } else lang.pcmd();

        return true;
    }
}
