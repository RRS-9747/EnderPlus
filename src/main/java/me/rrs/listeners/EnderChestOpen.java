package me.rrs.listeners;

import me.rrs.utils.InvUtils;
import me.rrs.utils.Lang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnderChestOpen implements Listener {
    Lang lang = new Lang();

    @EventHandler
    public void onEnderChestOpen(PlayerInteractEvent event){

        if (event.getClickedBlock() != null) {
            if (event.getClickedBlock().getType() == Material.ENDER_CHEST) {
                if (!event.isCancelled()) {
                    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        event.setCancelled(true);

                        Player p = event.getPlayer();

                        if (p.hasPermission("enderplus.lvl.6")) {
                            InvUtils.ownEnderInv(p, 54);

                        } else if (p.hasPermission("enderplus.lvl.5")) {
                            InvUtils.ownEnderInv(p, 45);

                        } else if (p.hasPermission("enderplus.lvl.4")) {
                            InvUtils.ownEnderInv(p, 36);

                        } else if (p.hasPermission("enderplus.lvl.3")) {
                            InvUtils.ownEnderInv(p, 27);

                        } else if (p.hasPermission("enderplus.lvl.2")) {
                            InvUtils.ownEnderInv(p, 18);

                        } else if (p.hasPermission("enderplus.lvl.1")) {
                            InvUtils.ownEnderInv(p, 9);

                        } else lang.msg("&c&l[EnderPlus]&r", "No-Echest", p);

                    }
                }
            }
        }
    }
}
