package me.rrs.enderplus.listeners;

import me.rrs.enderplus.EnderPlus;
import me.rrs.enderplus.database.EnderData;
import me.rrs.enderplus.database.Listeners;
import me.rrs.enderplus.utils.Serializers;
import me.rrs.enderplus.utils.InvUtils;
import me.rrs.enderplus.utils.Storage;
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
import org.bukkit.scheduler.BukkitScheduler;

import java.sql.SQLException;
import java.util.*;

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
            Player holder = (Player) e.getInventory().getHolder();

            BukkitScheduler scheduler = Bukkit.getScheduler();
            items(e.getInventory(), holder);
            scheduler.runTask(EnderPlus.getInstance(), () -> Storage.STATUS.remove(holder));

            EnderChest enderChest = Storage.ENDER_CHEST.get(holder);
            if (enderChest != null){
                enderChest.close();
                Storage.ENDER_CHEST.remove(holder);

            }
        }
    }


    private void items(final Inventory inv, final Player player) {
        final ArrayList<ItemStack> prunedItems = new ArrayList<>();

        Arrays.stream(inv.getContents())
                .filter(Objects::nonNull)
                .forEach(prunedItems::add);

        new BukkitRunnable() {

            @Override
            public void run() {
                utils.storeItems(prunedItems, player);
                if (Boolean.TRUE.equals(EnderPlus.getConfiguration().getBoolean("Database.Enable"))) {
                    try {
                        final EnderData enderData = Listeners.getPlayerFromDatabase(player);
                        if (prunedItems.isEmpty()) {
                            enderData.setData("");
                        } else enderData.setData(Serializers.getEncodedItem());
                        Listeners.database.updateEnderData(enderData);
                    } catch (final SQLException exception) {
                        exception.printStackTrace();
                        Bukkit.getLogger().severe("Could not update EnderChest items after chest close!");
                    }
                }
            }
        }.runTaskAsynchronously(EnderPlus.getInstance());
    }
}