package me.rrs.Listener;

import me.rrs.Util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

public class ChestClose implements Listener {

    Util util = new Util();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEchestClose(InventoryCloseEvent event){
        if (event.getInventory().getType() == InventoryType.CHEST){
            if (event.getInventory() == Util.inventory){

                if (event.getView().getTitle().endsWith("'s Ender Chest")){
                    String title = event.getView().getTitle();
                    String name = title.replaceAll("'s Ender Chest", "").trim();
                    Player player = Bukkit.getPlayer(name);
                    Util.Echest.put(player.getName(), event.getInventory().getContents());
                    util.saveInvs();
                }
            }

        }

    }
}
