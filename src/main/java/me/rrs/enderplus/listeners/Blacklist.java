package me.rrs.enderplus.listeners;

import me.rrs.enderplus.EnderPlus;
import me.rrs.enderplus.utils.InvUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import java.util.List;

public class Blacklist implements Listener {

    @EventHandler
    public void onItemDrag(InventoryDragEvent event){
        Material material = event.getOldCursor().getType();
        List<String> blacklist = EnderPlus.getConfiguration().getStringList("EnderChest.Blacklist");

        if (event.getInventory().equals(InvUtils.getEnderPlus())){
            for (String item : blacklist){
                if (item.equalsIgnoreCase(material.toString())){
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && !event.getWhoClicked().isOp()) {
            Material material = event.getCurrentItem().getType();
            List<String> blacklist = EnderPlus.getConfiguration().getStringList("EnderChest.Blacklist");

            if (event.getView().getTopInventory().equals(InvUtils.getEnderPlus())) {
                for (String item : blacklist) {
                    if (item.equalsIgnoreCase(material.toString())) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }


}
