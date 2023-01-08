package me.rrs.enderplus.listeners;

import me.rrs.enderplus.utils.InvUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.stream.Collectors;

public class EchestViewer implements Listener {


    @EventHandler
    public void onInvView(InventoryClickEvent event) {
        Inventory topInv = event.getView().getTopInventory();
        if (!topInv.equals(InvUtils.getEnderPlus())) return;

        Player holder = (Player) event.getInventory().getHolder();
        if (holder == null) return;

        List<Player> viewers = event.getViewers().stream()
                .filter(Player.class::isInstance)
                .map(Player.class::cast)
                .filter(viewer -> viewer.getOpenInventory().getTopInventory().equals(topInv))
                .filter(viewer -> viewer.hasPermission("enderplus.ec.other") && !viewer.hasPermission("enderplus.ec.other.edit"))
                .filter(viewer -> !viewer.equals(holder))
                .collect(Collectors.toList());

        if (!viewers.isEmpty()) {
            event.setCancelled(true);
        }
    }
}
