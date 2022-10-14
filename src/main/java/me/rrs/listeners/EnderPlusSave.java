package me.rrs.listeners;

import me.rrs.EnderPlus;
import me.rrs.database.EnderData;
import me.rrs.database.Listeners;
import me.rrs.utils.Serializers;
import me.rrs.utils.InvUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class EnderPlusSave implements Listener {

    Serializers utils = new Serializers();


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
            Player player = (Player) e.getInventory().getHolder();
            items(e.getInventory(), player);
            if (null != InvUtils.getEchest()) InvUtils.getEchest().close();
        }
    }


    private void items(final Inventory inv, final Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                utils.storeItems(inv, player);
                if (Boolean.TRUE.equals(EnderPlus.getConfiguration().getBoolean("Database.Enable"))) {
                    try {
                        final EnderData enderData = Listeners.getPlayerFromDatabase(player);
                        enderData.setData(Serializers.getEncodedItem());
                        Listeners.database.updateEnderData(enderData);
                    } catch (final SQLException exception) {
                        exception.printStackTrace();
                        Bukkit.getLogger().severe("[EnderPlus] Could not update EnderChest items after chest close!");
                    }
                }
            }
        }.runTaskAsynchronously(EnderPlus.getInstance());
    }
}