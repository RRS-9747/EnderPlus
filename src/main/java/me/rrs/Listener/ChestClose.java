package me.rrs.Listener;

import me.rrs.EnderPlus;
import me.rrs.Util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class ChestClose implements Listener {

    @EventHandler
    public void onEchestClose(InventoryCloseEvent event){
        if (event.getView().getTitle().contains(event.getPlayer().getName() + "'s Ender Chest")){
            Util.Echest.put(event.getPlayer().getName(), event.getInventory().getContents());
            Util util = new Util();
            util.saveInvs();
        }
    }
}
