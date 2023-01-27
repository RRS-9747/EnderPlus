package me.rrs.enderplus.listeners;

import me.rrs.enderplus.EnderPlus;
import me.rrs.enderplus.utils.InvUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Blacklist implements Listener {

    @EventHandler
    public void onItemDrag(InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();
        Player holder = (Player) event.getInventory().getHolder();
        if (EnderPlus.ENDER_PLUS.get(holder) == null ||
                !event.getInventory().equals(EnderPlus.ENDER_PLUS.get(holder)) ||
                player.hasPermission("enderplus.blacklist.bypass")) {
            return;
        }
        List<String> blacklist = EnderPlus.getConfiguration().getStringList("EnderChest.Blacklist");
        String item = event.getOldCursor().getType().toString();
        if (blacklist.contains(item)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        Player holder = (Player) event.getInventory().getHolder();

        if (EnderPlus.ENDER_PLUS.get(holder) == null ||
                !event.getInventory().equals(EnderPlus.ENDER_PLUS.get(holder)) ||
                item == null ||
                player.hasPermission("enderplus.blacklist.bypass")) {
            return;
        }
        if (!event.getView().getTopInventory().equals(InvUtils.getEnderPlus())) {
            return;
        }
        List<String> blacklist = EnderPlus.getConfiguration().getStringList("EnderChest.Blacklist");
        String itemType = item.getType().toString();
        if (blacklist.contains(itemType)) {
            event.setCancelled(true);
        }
    }


}
