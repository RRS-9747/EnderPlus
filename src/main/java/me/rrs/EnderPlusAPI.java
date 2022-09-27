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

    public EnderPlus getInstance(){
        return EnderPlus.getInstance();
    }
    public YamlDocument getConfig(){
        return EnderPlus.getConfiguration();
    }
    public YamlDocument getLang(){
        return EnderPlus.getLang();
    }
    public boolean getPremium(){
        if ("%%__POLYMART__%%".equalsIgnoreCase("1")){
            return true;
        }else{
            return false;
        }
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
        final Inventory enderPlus = Bukkit.createInventory(player, getRow(player), getConfig().getString("EnderChest.Name"));
        CompletableFuture.runAsync(() -> {
            final ArrayList<ItemStack> enderItems = EnderUtils.getItems(player);
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
