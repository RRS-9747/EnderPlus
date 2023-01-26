package me.rrs.enderplus.listeners;

import me.rrs.enderplus.EnderPlus;
import me.rrs.enderplus.utils.Serializers;
import me.rrs.enderplus.utils.InvUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.EnderChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class EnderPlusSave implements Listener {


    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (event.getPlayer().getOpenInventory().getTopInventory().equals(InvUtils.getEnderPlus())) {
            Player player = event.getPlayer();
            player.closeInventory();
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClose(final InventoryCloseEvent e) {
        if (e.getInventory().equals(InvUtils.getEnderPlus())) {
            Player holder = (Player) e.getInventory().getHolder();

            Bukkit.getScheduler().runTaskAsynchronously(EnderPlus.getInstance(), () -> {
                String encodedData = Serializers.serialize(e.getInventory().getContents());
                if (EnderPlus.getConfiguration().getBoolean("Config.Online")){
                    EnderPlus.getDatabase().storeDataByUuid(holder.getName(), holder.getUniqueId().toString(), encodedData);
                }else EnderPlus.getDatabase().storeDataByName(holder.getName(), holder.getUniqueId().toString(), encodedData);
            });
            EnderPlus.STATUS.replace(holder, false);

            EnderChest enderChest = EnderPlus.ENDER_CHEST.get(holder);
            if (enderChest != null){
                enderChest.close();
                EnderPlus.ENDER_CHEST.remove(holder);

            }
        }
    }

}