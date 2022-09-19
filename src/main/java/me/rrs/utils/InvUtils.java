package me.rrs.utils;

import me.rrs.EnderPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class InvUtils {

    public static void ownEnderInv(Player player, int size){
        inv(player, player, size);
    }

    public static void otherEnderInv(Player sender, Player player, int size){
        inv(sender, player, size);
    }

    private static void inv(Player sender, Player player, int size) {
        Inventory enderPlus = Bukkit.createInventory(sender, size, ChatColor.translateAlternateColorCodes('&', EnderPlus.getConfiguration().getString("EnderChest.Name")));

        CompletableFuture.runAsync(() -> {
            ArrayList<ItemStack> enderItems = EnderUtils.getItems(player);
            enderItems.forEach(enderPlus::addItem);
        }).thenRun(() -> {
            new BukkitRunnable(){
                @Override
                public void run() {
                    sender.openInventory(enderPlus);
                    String version = Bukkit.getServer().getVersion();
                    if (version.contains("1.8")) {
                        sender.playSound(sender.getLocation(), Sound.valueOf("CHEST_OPEN"), 1, 1);

                    } else if (version.contains("1.9") || version.contains("1.10") || version.contains("1.11") || version.contains("1.12")) {
                        sender.playSound(sender.getLocation(), Sound.valueOf("BLOCK_ENDERCHEST_OPEN"), 1, 1);

                    }else sender.playSound(sender.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1, 1);
                }
            }.runTask(EnderPlus.getInstance());
        });


    }
}
