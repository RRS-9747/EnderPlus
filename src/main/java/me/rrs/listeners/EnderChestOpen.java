package me.rrs.listeners;

import me.rrs.EnderPlus;
import me.rrs.utils.InvUtils;
import me.rrs.utils.Lang;
import org.bukkit.Material;
import org.bukkit.block.EnderChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnderChestOpen implements Listener {
    Lang lang = new Lang();
    public static EnderChest Echest;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEnderChestOpen(PlayerInteractEvent event){

        if (event.getClickedBlock() != null) {
            if (event.getClickedBlock().getType() == Material.ENDER_CHEST) {
                if (!event.isCancelled()) {
                    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        event.setCancelled(true);
                        Player p = event.getPlayer();
                        InvUtils invUtils = new InvUtils();

                        if (p.hasPermission("enderplus.lvl.6")) {
                            invUtils.ownEnderInv(p, 54, event.getClickedBlock());

                        } else if (p.hasPermission("enderplus.lvl.5")) {
                            invUtils.ownEnderInv(p, 45, event.getClickedBlock());

                        } else if (p.hasPermission("enderplus.lvl.4")) {
                            invUtils.ownEnderInv(p, 36, event.getClickedBlock());

                        } else if (p.hasPermission("enderplus.lvl.3")) {
                            invUtils.ownEnderInv(p, 27, event.getClickedBlock());

                        } else if (p.hasPermission("enderplus.lvl.2")) {
                            invUtils.ownEnderInv(p, 18, event.getClickedBlock());

                        } else if (p.hasPermission("enderplus.lvl.1")) {
                            invUtils.ownEnderInv(p, 9, event.getClickedBlock());

                        } else lang.msg("&c&l" + EnderPlus.getLang().getString("Prefix") +"&r", "No-Echest", p);

                    }
                }
            }
        }

    }
}
