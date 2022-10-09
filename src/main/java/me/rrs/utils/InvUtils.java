package me.rrs.utils;

import me.rrs.EnderPlus;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.EnderChest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class InvUtils {
    Serializers utils = new Serializers();
    public static EnderChest Echest;
    public static Inventory enderPlus;

    public void ownEnderInv(final Player player, final int size, final Block block) {
        Echest = (EnderChest) block.getState();
        Echest.open();
        enderPlus = Bukkit.createInventory(player, size, EnderPlus.getConfiguration().getString("EnderChest.Name"));
        inv(player, player, enderPlus);

    }

    public void ownEnderInv(final Player player, final int size) {
        enderPlus = Bukkit.createInventory(player, size, EnderPlus.getConfiguration().getString("EnderChest.Name"));
        inv(player, player, enderPlus);

    }

    public void otherEnderInv(final Player sender, final Player player, final int size) {
        if (!player.getOpenInventory().getTitle().equalsIgnoreCase(EnderPlus.getConfiguration().getString("EnderChest.Name"))){
            enderPlus = Bukkit.createInventory(sender, size, player.getName() + "'s " + EnderPlus.getConfiguration().getString("EnderChest.Name"));
            inv(sender, player, enderPlus);
        }

    }


    protected void inv(final Player sender, final Player player, final Inventory enderPlus) {
        CompletableFuture.runAsync(() -> {
            final ArrayList<ItemStack> enderItems = utils.getItems(player);
            enderItems.forEach(enderPlus::addItem);
        }).thenRun(() -> new BukkitRunnable() {
            @Override
            public void run() {
                sender.openInventory(enderPlus);
            }
        }.runTask(EnderPlus.getInstance()));
    }

}
