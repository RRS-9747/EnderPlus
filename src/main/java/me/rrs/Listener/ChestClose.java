package me.rrs.Listener;

import me.rrs.EnderPlus;
import me.rrs.Util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.scheduler.BukkitRunnable;

public class ChestClose implements Listener {


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEchestClose(InventoryCloseEvent event){
        Player p = (Player) event.getPlayer();
        if (event.getInventory().getType() == InventoryType.CHEST){
            if (event.getInventory() == Util.inventory){
                if (event.getView().getTitle().endsWith("'s Ender Chest")){

                    String version = Bukkit.getServer().getVersion();
                    if (version.contains("1.8")) {
                        p.playSound(p.getLocation(), Sound.valueOf("CHEST_CLOSE"), 1, 1);

                    } else if (version.contains("1.9") || version.contains("1.10") || version.contains("1.11") || version.contains("1.12")) {
                        p.playSound(p.getLocation(), Sound.valueOf("BLOCK_ENDERCHEST_CLOSE"), 1, 1);

                    }else p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1, 1);

                    String title = event.getView().getTitle();
                    String name = title.replaceAll("'s Ender Chest", "").trim();
                    Player player = Bukkit.getPlayer(name);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Util.Echest.put(player.getName(), event.getInventory().getContents());
                            Util.saveInvs();
                        }
                    }.runTaskAsynchronously(EnderPlus.getInstance());


                }
            }
        }
    }
}
