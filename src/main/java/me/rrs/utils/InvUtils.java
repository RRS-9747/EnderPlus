package me.rrs.utils;

import me.rrs.EnderPlus;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.EnderChest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class InvUtils {
    EnderUtils utils = new EnderUtils();

    public static EnderChest Echest;

    public void ownEnderInv(final Player player, final int size, final Block block) {
        Echest = (EnderChest) block.getState();
        Echest.open();
        final Inventory enderPlus = Bukkit.createInventory(player, size, EnderPlus.getConfiguration().getString("EnderChest.Name"));
        inv(player, player, enderPlus);

    }

    public void ownEnderInv(final Player player, final int size) {
        final Inventory enderPlus = Bukkit.createInventory(player, size, EnderPlus.getConfiguration().getString("EnderChest.Name"));
        inv(player, player, enderPlus);

    }

    public void otherEnderInv(final Player sender, final Player player, final int size) {
        if (!player.getOpenInventory().getTitle().equalsIgnoreCase(EnderPlus.getConfiguration().getString("EnderChest.Name"))){
            final Inventory enderPlus = Bukkit.createInventory(sender, size, player.getName() + "'s " + EnderPlus.getConfiguration().getString("EnderChest.Name"));
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
                final String version = Bukkit.getServer().getVersion();
                if (version.contains("1.8")) {
                    sender.playSound(sender.getLocation(), Sound.valueOf("CHEST_OPEN"), 1.0F, 1.0F);

                } else if (version.contains("1.9") || version.contains("1.10") || version.contains("1.11") || version.contains("1.12")) {
                    sender.playSound(sender.getLocation(), Sound.valueOf("BLOCK_ENDERCHEST_OPEN"), 1.0F, 1.0F);

                } else sender.playSound(sender.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1.0F, 1.0F);
            }
        }.runTask(EnderPlus.getInstance()));
    }

}
