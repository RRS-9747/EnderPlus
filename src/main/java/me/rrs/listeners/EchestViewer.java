package me.rrs.listeners;

import me.rrs.utils.InvUtils;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EchestViewer implements Listener {

    @EventHandler
    public void onInvView(final InventoryClickEvent event) {
        if (event.getView().getTopInventory().equals(InvUtils.getEnderPlus())){
            for (HumanEntity p : event.getViewers()) {
                if (p.getOpenInventory().getTopInventory().equals(InvUtils.getEnderPlus()) &&
                        p.hasPermission("enderplus.ec.other")) {
                    Player player = (Player) event.getInventory().getHolder();
                    if (player != null && !player.equals(p)) event.setCancelled(true);
                }
            }
        }
    }
}
