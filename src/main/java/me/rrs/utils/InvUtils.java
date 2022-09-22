package me.rrs.utils;

import me.rrs.EnderPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

    public static EnderChest Echest;

    public void ownEnderInv(Player player, int size, Block block){
        Echest = (EnderChest) block.getState();
        Echest.open();
        Inventory enderPlus = Bukkit.createInventory(player, size,  EnderPlus.getConfiguration().getString("EnderChest.Name"));

        inv(player, player, enderPlus);
    }
    public void ownEnderInv(Player player, int size){
        Inventory enderPlus = Bukkit.createInventory(player, size,  EnderPlus.getConfiguration().getString("EnderChest.Name"));
        inv(player, player, enderPlus);
    }

    public void otherEnderInv(Player sender, Player player, int size){
        Inventory enderPlus = Bukkit.createInventory(sender, size,player.getName() + "'s " + EnderPlus.getConfiguration().getString("EnderChest.Name"));

        inv(sender, player, enderPlus);
    }

    private void inv(Player sender, Player player, Inventory enderPlus) {
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
