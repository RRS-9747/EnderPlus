package me.rrs.enderplus.listeners;

import me.rrs.enderplus.EnderPlus;
import me.rrs.enderplus.utils.Lang;
import me.rrs.enderplus.utils.InvUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.EnderChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnderPlusOpen implements Listener {

    protected final Lang lang = new Lang();

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEnderChestOpen(final PlayerInteractEvent event) {
        if (null != event.getClickedBlock()) {
            if (Material.ENDER_CHEST == event.getClickedBlock().getType()) {
                if (!event.isCancelled()) {
                    if (Action.RIGHT_CLICK_BLOCK == event.getAction()) {
                        event.setCancelled(true);
                        final Player p = event.getPlayer();
                        final InvUtils invUtils = new InvUtils();
                        boolean opened = false;
                        for (int i = 6; 0 < i; i--) {
                            if (p.hasPermission("enderplus.lvl." + i)) {
                                invUtils.ownEnderInv(p, i * 9, event.getClickedBlock(), EnderPlus.getConfiguration().getString("EnderChest.Name.row-" + i));
                                opened = true;
                                break;
                            }
                        }
                        if (!opened){
                            lang.msg("&c&l" + EnderPlus.getLang().getString("Prefix") + "&r", "No-Echest", p);
                        }
                    }
                }
            }
        }
    }
}