package me.rrs.listeners;

import me.rrs.EnderPlus;
import me.rrs.db.EnderData;
import me.rrs.db.Listeners;
import me.rrs.utils.EnderUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class InventoryClose implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {

        Player p = (Player) e.getPlayer();

        if (e.getView().getTitle().equalsIgnoreCase("Ender Chest")) {

            String version = Bukkit.getServer().getVersion();
            if (version.contains("1.8")) {
                p.playSound(p.getLocation(), Sound.valueOf("CHEST_CLOSE"), 1, 1);

            } else if (version.contains("1.9") || version.contains("1.10") || version.contains("1.11") || version.contains("1.12")) {
                p.playSound(p.getLocation(), Sound.valueOf("BLOCK_ENDERCHEST_CLOSE"), 1, 1);

            } else p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1, 1);

            ArrayList<ItemStack> prunedItems = new ArrayList<>();

            Arrays.stream(e.getInventory().getContents())
                    .filter(itemStack -> {
                        return itemStack != null;
                    })
                    .forEach(prunedItems::add);


            EnderUtils.storeItems(prunedItems, p);

            if (EnderPlus.getConfiguration().getBoolean("Database.Enable")) {
                try {
                    EnderData enderData = Listeners.getPlayerFromDatabase(p);

                    if (prunedItems.size() == 0) {
                        enderData.setdata("");
                    } else enderData.setdata(EnderUtils.encodedItem);

                    Listeners.database.updateEnderData(enderData);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                    Bukkit.getLogger().severe("Could not update Enderchest items after chest close!");
                }
            }
        }
    }

}
