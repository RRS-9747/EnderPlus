package me.rrs.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class InvUtils {

    public static void enderInv(Player player, int size){

        Inventory enderplus = Bukkit.createInventory(player, size, "Ender Chest");
        player.openInventory(enderplus);

        String version = Bukkit.getServer().getVersion();
        if (version.contains("1.8")) {
            player.playSound(player.getLocation(), Sound.valueOf("CHEST_OPEN"), 1, 1);

        } else if (version.contains("1.9") || version.contains("1.10") || version.contains("1.11") || version.contains("1.12")) {
            player.playSound(player.getLocation(), Sound.valueOf("BLOCK_ENDERCHEST_OPEN"), 1, 1);

        }else player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1, 1);

        ArrayList<ItemStack> enderItems = EnderUtils.getItems(player);
        enderItems.forEach(itemStack -> enderplus.addItem(itemStack));
    }
}
