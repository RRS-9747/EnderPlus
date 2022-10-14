package me.rrs.listeners;

import me.rrs.EnderPlus;
import me.rrs.utils.InvUtils;
import me.rrs.utils.Lang;
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

    private final Lang lang = new Lang();


    @EventHandler(priority = EventPriority.NORMAL)
    public void onEnderChestOpen(final PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            Block block = event.getClickedBlock();
            if (Material.ENDER_CHEST == block.getType() && Action.RIGHT_CLICK_BLOCK == event.getAction()) {
                event.setCancelled(true);
                final Player p = event.getPlayer();
                final InvUtils invUtils = new InvUtils();
                boolean opened = false;

                for (int i = 6; 0 < i; i--) {
                    if (p.hasPermission("enderplus.lvl." + i)) {
                        invUtils.ownEnderInv(p, i * 9, "EnderChest.Name.row-" + i);
                        InvUtils.setEchest((EnderChest) block.getState());
                        InvUtils.getEchest().open();

                        opened = true;
                        break;
                    }
                }
                if (!opened) {
                    lang.msg("&c&l" + EnderPlus.getLang().getString("Prefix") + "&r", "No-Echest", p);
                }
            }
        }
    }
}