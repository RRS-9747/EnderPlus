package me.rrs;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.rrs.utils.EnderUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class EnderPlusAPI {

    public YamlDocument getConfig(){
        return EnderPlus.getConfiguration();
    }

    public YamlDocument getLang(){
        return EnderPlus.getLang();
    }

    public int getRow(Player player){
        int i;
        final int maxColumns = 6;
        for (i = maxColumns; 0 < i; i--) {
            if (player.hasPermission("enderplus.lvl." + i)) {
                break;
            }
        }
        return i;
    }

    public Inventory getEnderChest(Player player, Player sender){
        final EnderUtils utils = new EnderUtils();
        final Inventory enderPlus = Bukkit.createInventory(player, getRow(player), EnderPlus.getConfiguration().getString("EnderChest.Name"));
        CompletableFuture.runAsync(() -> {
            final ArrayList<ItemStack> enderItems = utils.getItems(player);
            enderItems.forEach(enderPlus::addItem);
        }).thenRun(() -> new BukkitRunnable() {
            @Override
            public void run() {
                sender.openInventory(enderPlus);
            }
        }.runTask(EnderPlus.getInstance()));

        return enderPlus;
    }




}
