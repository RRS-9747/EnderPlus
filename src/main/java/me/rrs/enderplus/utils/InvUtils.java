package me.rrs.enderplus.utils;

import me.rrs.enderplus.EnderPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.CompletableFuture;

public class InvUtils {
    private static Inventory enderPlus;
    Lang lang = new Lang();
    public static Inventory getEnderPlus() {
        return enderPlus;
    }

    public void openEnderInv(final Player sender, final Player holder, final int size, String name) {
        EnderPlus.STATUS.putIfAbsent(holder, false);
        if (EnderPlus.STATUS.get(holder)){
            lang.msg("&c&l" + EnderPlus.getLang().getString("Prefix"), "Saving", sender);
            return;
        }

        if (EnderPlus.ENDER_PLUS.get(holder) == null){
            enderPlus = Bukkit.createInventory(holder, size, ChatColor.translateAlternateColorCodes('&', name));
            EnderPlus.ENDER_PLUS.put(holder, enderPlus);
        }else enderPlus = EnderPlus.ENDER_PLUS.get(holder);



        CompletableFuture.runAsync(() -> {
            String encodedData;
            if (EnderPlus.getConfiguration().getBoolean("Config.Online")){
                encodedData = EnderPlus.getDatabase().getDataByUuid(holder.getUniqueId().toString());
            }else encodedData = EnderPlus.getDatabase().getDataByPlayerName(holder.getName());

            ItemStack[] deserializedItems = Serializers.deserialize(encodedData);
            enderPlus.setContents(deserializedItems);
        }).thenRun(() -> new BukkitRunnable() {
            @Override
            public void run() {
                sender.openInventory(enderPlus);

                EnderPlus.STATUS.replace(holder, true);
            }
        }.runTask(EnderPlus.getInstance()));
    }




}
