package me.rrs.listeners;

import me.rrs.EnderPlus;
import me.rrs.db.EnderData;
import me.rrs.db.Listeners;
import me.rrs.utils.EnderUtils;
import me.rrs.utils.InvUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class InventoryClose implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClose(InventoryCloseEvent e) {

        Player p = (Player) e.getPlayer();

        if (e.getView().getTitle().equalsIgnoreCase( EnderPlus.getConfiguration().getString("EnderChest.Name"))) {
            items(e, p);
            String version = Bukkit.getServer().getVersion();
            if (version.contains("1.8")) {
                p.playSound(p.getLocation(), Sound.valueOf("CHEST_CLOSE"), 1, 1);

            } else if (version.contains("1.9") || version.contains("1.10") || version.contains("1.11") || version.contains("1.12")) {
                p.playSound(p.getLocation(), Sound.valueOf("BLOCK_ENDERCHEST_CLOSE"), 1, 1);

            } else p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1, 1);
            if (InvUtils.Echest != null) InvUtils.Echest.close();

        } else if (e.getView().getTitle().endsWith(ChatColor.translateAlternateColorCodes('&', EnderPlus.getConfiguration().getString("EnderChest.Name")))) {
            Player player = Bukkit.getPlayer(e.getView().getTitle().replaceAll("'s " + EnderPlus.getConfiguration().getString("EnderChest.Name"), "").trim());
            if (player != null) {
                items(e, player);
            }
        }
    }

    private void items(InventoryCloseEvent e, Player player) {
        ArrayList<ItemStack> prunedItems = new ArrayList<>();

        Arrays.stream(e.getInventory().getContents())
                .filter(Objects::nonNull)
                .forEach(prunedItems::add);

        new BukkitRunnable() {

            @Override
            public void run() {
                EnderUtils.storeItems(prunedItems, player);
                if (EnderPlus.getConfiguration().getBoolean("Database.Enable")) {
                    try {
                        EnderData enderData = Listeners.getPlayerFromDatabase(player);

                        if (prunedItems.size() == 0) {
                            enderData.setdata("");
                        } else enderData.setdata(EnderUtils.encodedItem);

                        Listeners.database.updateEnderData(enderData);
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                        Bukkit.getLogger().severe("Could not update EnderChest items after chest close!");
                    }
                }
            }
        }.runTaskAsynchronously(EnderPlus.getInstance());
    }
}
