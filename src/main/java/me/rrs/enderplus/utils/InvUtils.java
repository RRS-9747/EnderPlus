package me.rrs.enderplus.utils;

import me.rrs.enderplus.EnderPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.EnderChest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class InvUtils {

    private static EnderChest Echest;
    private static Inventory enderPlus;
    Serializers utils = new Serializers();


    public static EnderChest getEchest() {
        return Echest;
    }

    public static Inventory getEnderPlus() {
        return enderPlus;
    }


    public void ownEnderInv(final Player player, final int size, final Block block, String name) {
        Echest = (EnderChest) block.getState();
        Echest.open();
        enderPlus = Bukkit.createInventory(player, size, ChatColor.translateAlternateColorCodes('&', name));
        inv(player, player);

    }

    public void ownEnderInv(final Player player, final int size, String name) {
        enderPlus = Bukkit.createInventory(player, size, ChatColor.translateAlternateColorCodes('&', name));
        inv(player, player);

    }

    public void otherEnderInv(final Player sender, final Player holder, final int size, String name) {
        if (!holder.getOpenInventory().getTitle().equalsIgnoreCase(name)){
            enderPlus = Bukkit.createInventory(sender, size, holder.getName() + "'s " + ChatColor.translateAlternateColorCodes('&', name));
            inv(sender, holder);
        }

    }

    private void inv(final Player sender, final Player holder) {
        CompletableFuture.runAsync(() -> {
            final ArrayList<ItemStack> enderItems = utils.getItems(holder);
            enderItems.forEach(enderPlus::addItem);
        }).thenRun(() -> new BukkitRunnable() {
            @Override
            public void run() {
                sender.openInventory(enderPlus);
            }
        }.runTask(EnderPlus.getInstance()));
    }

}
