package me.rrs.listeners;

import me.rrs.EnderPlus;
import me.rrs.database.EnderData;
import me.rrs.database.Listeners;
import me.rrs.utils.Serializers;
import me.rrs.utils.InvUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

public class InventoryClose implements Listener {

    Serializers utils = new Serializers();

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (event.getPlayer().getOpenInventory().getTitle().equals(ChatColor.translateAlternateColorCodes('&', EnderPlus.getConfiguration().getString("EnderChest.Name")))){
            if (event.getPlayer().getOpenInventory().getTopInventory().equals(InvUtils.enderPlus)) {
                Player player = event.getPlayer();
                player.closeInventory();
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClose(final InventoryCloseEvent e) {
        final Player p = (Player) e.getPlayer();
        if (e.getView().getTitle().equalsIgnoreCase(EnderPlus.getConfiguration().getString("EnderChest.Name"))) {
            if (e.getInventory().equals(InvUtils.enderPlus)) {
                items(e.getInventory(), p);
                if (null != InvUtils.Echest) InvUtils.Echest.close();
            }

        } else if (e.getView().getTitle().endsWith(ChatColor.translateAlternateColorCodes('&', EnderPlus.getConfiguration().getString("EnderChest.Name")))) {
            final Player player = Bukkit.getPlayer(e.getView().getTitle().replaceAll("'s " + EnderPlus.getConfiguration().getString("EnderChest.Name"), "").trim());
            if (null != player) {
                items(e.getInventory(), player);
            }
        }
    }

    private void items(final Inventory inv, final Player player) {
        final ArrayList<ItemStack> prunedItems = new ArrayList<>();
        Arrays.stream(inv.getContents()).filter(Objects::nonNull).forEach(prunedItems::add);

        new BukkitRunnable() {

            @Override
            public void run() {
                utils.storeItems(prunedItems, player);
                if (EnderPlus.getConfiguration().getBoolean("Database.Enable")) {
                    try {
                        final EnderData enderData = Listeners.getPlayerFromDatabase(player);
                        if (prunedItems.isEmpty()) {
                            enderData.setData("");
                        } else enderData.setData(Serializers.encodedItem);
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
