package me.rrs.enderplus;

import me.rrs.enderplus.utils.Serializers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class EnderPlusAPI {

    public int getRow(Player player){
        int i;
        for (i = 6; 0 < i; i--) {
            if (player.hasPermission("enderplus.lvl." + i)) {
                break;
            }
        }
        return i;
    }

    public Inventory openEnderChest(Player holder, Player sender, String name, Plugin instance){
        final Serializers utils = new Serializers();
        final Inventory enderPlus = Bukkit.createInventory(holder, getRow(holder), name);
        CompletableFuture.runAsync(() -> {
            ArrayList<ItemStack> items = utils.getItems(holder);
            if (items.size() > getRow(holder)) {
                enderPlus.forEach(enderPlus::addItem);
            }
        }).thenRun(() -> new BukkitRunnable() {
            @Override
            public void run() {
                sender.openInventory(enderPlus);
            }
        }.runTask(instance));

        return enderPlus;
    }


}
