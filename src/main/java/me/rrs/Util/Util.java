package me.rrs.Util;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {

    public static HashMap<String, ItemStack[]> Echest = new HashMap<>();
    public static Inventory inventory;

    public void inv(Player player, int size){

        inventory = Bukkit.createInventory(player, size, player.getName() + "'s Ender Chest");
        if (Echest.containsKey(player.getName())){
            inventory.setContents(Echest.get(player.getName()));
        }
        player.openInventory(inventory);
        try{
            player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1, 1);
        }catch (Error ignore){
        }
    }


    public void inv(Player player, int size, Player sender){
        inventory = Bukkit.createInventory(player, size, player.getName() + "'s Ender Chest");
        if (Echest.containsKey(player.getName())){
            inventory.setContents(Echest.get(player.getName()));
        }
        sender.openInventory(inventory);
    }


    public static void saveInvs(){
        for (Map.Entry<String, ItemStack[]> entry : Echest.entrySet()){
            Data.getData().set("data." + entry.getKey(), entry.getValue());
        }
            Data.save();
    }


    public void restoreInvs() {
            Data.getData().getConfigurationSection("data").getKeys(false).forEach(key -> {
                ItemStack[] content = ((List<ItemStack>) Data.getData().get("data." + key)).toArray(new ItemStack[0]);
                Util.Echest.put(key, content);
            });
    }
}
