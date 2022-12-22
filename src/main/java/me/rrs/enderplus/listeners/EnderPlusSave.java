package me.rrs.enderplus.listeners;

import me.rrs.enderplus.EnderPlus;
import me.rrs.enderplus.database.EnderData;
import me.rrs.enderplus.database.Listeners;
import me.rrs.enderplus.utils.Serializers;
import me.rrs.enderplus.utils.InvUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

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
        final ArrayList<ItemStack> prunedItems = new ArrayList<>();

        Arrays.stream(inv.getContents())
                .filter(Objects::nonNull)
                .forEach(prunedItems::add);

        new BukkitRunnable() {

            @Override
            public void run() {
                utils.storeItems(prunedItems, player);
                if (EnderPlus.getConfiguration().getBoolean("Database.Enable")) {
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