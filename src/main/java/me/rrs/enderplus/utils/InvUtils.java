package me.rrs.enderplus.utils;

import me.rrs.enderplus.EnderPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.EnderChest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class InvUtils{

    public static EnderChest getEchest() {
        return Echest;
    }

    public static void setEchest(EnderChest echest) {
        Echest = echest;
    }
    public static Inventory getEnderPlus() {
        return enderPlus;
    }

    private static EnderChest Echest;
    private static Inventory enderPlus;
    final Serializers utils = new Serializers();

    public void ownEnderInv(Player player, int size, String name) {

        //TODO -> Remove soon
        enderPlus = Bukkit.createInventory(player, size, ChatColor.translateAlternateColorCodes('&', name));
        List<ItemStack> enderItems = utils.getItem(player);


        if (enderItems.isEmpty()){
            inv(player, player, size);
        } else {
            inv(player, enderItems);
        }
    }

    public void otherEnderInv(Player sender, Player holder, int size, String name) {
        if (!holder.getOpenInventory().getTopInventory().equals(enderPlus)) {
            enderPlus = Bukkit.createInventory(holder, size, ChatColor.translateAlternateColorCodes('&', holder.getName() + "'s " + EnderPlus.getConfiguration().getString(name)));
            inv(holder, sender, size);
        }else sender.sendMessage("[EnderPlus] You can't open " + holder.getName() + "'s EnderChest now");
    }

    private void inv(Player holder, Player sender, int size){
        CompletableFuture.runAsync(() -> {
            ItemStack[] items = utils.getItems(holder);
            if (items.length > size) {
                for (int i = 0; i < size; i++) {
                    enderPlus.setItem(i, items[i]);
                }
            }else enderPlus.setContents(utils.getItems(holder));

        }).thenRun(() -> new BukkitRunnable() {
            @Override
            public void run() {
                sender.openInventory(enderPlus);
            }
        }.runTask(EnderPlus.getInstance()));
    }


    ///TODO -> Remove soon
    protected void inv(final Player sender, List<ItemStack> itemStacks) {
        CompletableFuture.runAsync(() -> itemStacks.forEach(enderPlus::addItem)).thenRun(() -> new BukkitRunnable() {
            @Override
            public void run() {
                sender.openInventory(enderPlus);
            }
        }.runTask(EnderPlus.getInstance()));
    }

}
