package me.rrs.listeners;

import me.rrs.EnderPlus;
import me.rrs.utils.InvUtils;
import me.rrs.utils.Lang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnderChestOpen implements Listener {

    Player p;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEnderChestOpen(final PlayerInteractEvent event) {
        if (null != event.getClickedBlock()) {
            if (Material.ENDER_CHEST == event.getClickedBlock().getType()) {
                if (!event.isCancelled()) {
                    if (Action.RIGHT_CLICK_BLOCK == event.getAction()) {
                        event.setCancelled(true);
                         p = event.getPlayer();
                        InvUtils invUtils = new InvUtils();
                        final int maxColumns = 6;
                        boolean opened = false;
                        for (int i = maxColumns; 0 < i; i--) {
                            if (p.hasPermission("enderplus.lvl." + i)) {
                                invUtils.ownEnderInv(p, i * 9, event.getClickedBlock());
                                opened = true;
                                break;
                            }
                        }
                        if (!opened) {
                            Lang.msg("&c&l" + EnderPlus.getLang().getString("Prefix") + "&r", "No-Echest", p);
                        }
                    }
                }
            }
        }
    }
}