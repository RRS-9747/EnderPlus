package me.rrs.enderplus.listeners;

import me.rrs.enderplus.EnderPlus;
import me.rrs.enderplus.utils.Serializers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PlayerJoin implements Listener {


    private final Date date = new Date();
    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM");
    private final String currentDate = dateFormat.format(date);


    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        if ("23/3".equals(this.currentDate)) {
            if (player.hasPermission("enderplus.notify")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[EnderPlus]&r Today is my Birthday :D Leave a review on spigot as a gift :3"));
            }
        }

        PersistentDataContainer container = player.getPersistentDataContainer();
        if (container.has(Serializers.NAMESPACED_KEY, PersistentDataType.STRING)){
            ArrayList<ItemStack> enderItems = Serializers.retrieveItems(player);
            if (!enderItems.isEmpty()) {
                Inventory tempInv = Bukkit.createInventory(player, 54);
                enderItems.forEach(tempInv::addItem);
                if (EnderPlus.getConfiguration().getBoolean("Config.Online")){
                    EnderPlus.getDatabase().storeDataByUuid(player.getName(), player.getUniqueId().toString(), Serializers.serialize(tempInv.getContents()));
                }else EnderPlus.getDatabase().storeDataByName(player.getName(), player.getUniqueId().toString(), Serializers.serialize(tempInv.getContents()));
                container.remove(Serializers.NAMESPACED_KEY);
                return;
            }
        }


        if (EnderPlus.getConfiguration().getBoolean("EnderChest.Convert-onJoin")) {
            if (!player.getEnderChest().isEmpty()) {
                if (EnderPlus.getConfiguration().getBoolean("Config.Online")) {
                    EnderPlus.getDatabase().storeDataByUuid(player.getName(), player.getUniqueId().toString(), Serializers.serialize(player.getEnderChest().getContents()));
                } else {
                    EnderPlus.getDatabase().storeDataByName(player.getName(), player.getUniqueId().toString(), Serializers.serialize(player.getEnderChest().getContents()));
                }
                player.getEnderChest().clear();
            }

        }
    }

}
