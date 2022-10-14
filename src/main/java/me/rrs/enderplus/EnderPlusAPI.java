package me.rrs.enderplus;

import me.rrs.enderplus.utils.Serializers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

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

    public Inventory getEnderChest(Player holder, Player sender, Plugin instance){
        final Serializers utils = new Serializers();
        final Inventory enderPlus = Bukkit.createInventory(holder, getRow(holder), EnderPlus.getConfiguration().getString("EnderChest.Name"));
        CompletableFuture.runAsync(() -> {
            ItemStack[] items = utils.getItems(holder);
            if (items.length > getRow(holder)) {
                for (int i = 0; i < getRow(holder); i++) {
                    enderPlus.setItem(i, items[i]);
                }
            }else enderPlus.setContents(utils.getItems(holder));

        }).thenRun(() -> new BukkitRunnable() {
            @Override
            public void run() {
                sender.openInventory(enderPlus);
            }
        }.runTask(instance));

        return enderPlus;
    }




}
