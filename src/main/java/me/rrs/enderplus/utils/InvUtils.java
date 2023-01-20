package me.rrs.enderplus.utils;

import me.rrs.enderplus.EnderPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class InvUtils {
    private static Inventory enderPlus;
    Serializers utils = new Serializers();
    Lang lang = new Lang();
    public static Inventory getEnderPlus() {
        return enderPlus;
    }

    public void openEnderInv(final Player sender, final Player holder, final int size, String name, boolean isOwnInv) {
        EnderPlus.STATUS.putIfAbsent(holder, false);
        if (EnderPlus.STATUS.get(holder)){
            lang.msg("&c&l" + EnderPlus.getLang().getString("Prefix"), "Saving", sender);
            return;
        }
        if (isOwnInv) {
            enderPlus = Bukkit.createInventory(holder, size, ChatColor.translateAlternateColorCodes('&', name));
        } else {
            enderPlus = Bukkit.createInventory(holder, size, holder.getName() + "'s " + ChatColor.translateAlternateColorCodes('&', name));
        }

        CompletableFuture.runAsync(() -> {
            ArrayList<ItemStack> enderItems = utils.retrieveItems(holder);
            enderItems.forEach(enderPlus::addItem);
                }).thenRun(() -> new BukkitRunnable() {
            @Override
            public void run() {
                sender.openInventory(enderPlus);

                EnderPlus.STATUS.replace(holder, true);
            }
        }.runTask(EnderPlus.getInstance()));
    }

}
