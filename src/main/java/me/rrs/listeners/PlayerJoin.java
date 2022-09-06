package me.rrs.listeners;

import me.rrs.EnderPlus;
import me.rrs.utils.EnderUtils;
import me.rrs.utils.UpdateAPI;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class PlayerJoin implements Listener {
    Date date = new Date();
    UpdateAPI updateAPI = new UpdateAPI();
    DateFormat dateFormat = new SimpleDateFormat("dd/MM");
    String currentDate = dateFormat.format(date);

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        Player p = event.getPlayer();

        PersistentDataContainer data = p.getPersistentDataContainer();

        if (!data.has(new NamespacedKey(EnderPlus.getInstance(), "EnderPlus"), PersistentDataType.STRING)){
            data.set(new NamespacedKey(EnderPlus.getInstance(), "EnderPlus"), PersistentDataType.STRING, "");
        }


        if (event.getPlayer().hasPermission("enderplus.notify")) {
            if (EnderPlus.getConfiguration().getBoolean("Config.Update-Checker")) {
                if (updateAPI.hasGithubUpdate("RRS-9747", "EnderPlus")) {
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "You are using EnderPlus" + EnderPlus.getInstance().getDescription().getVersion()));
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "However version " + updateAPI.getGithubVersion("RRS-9747", "EnderPlus") + " is available."));
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "You can download it from: https://bit.ly/enderplus"));
                }

            }
        }


        if (currentDate.equals("23/3")){
            if (event.getPlayer().hasPermission("enderplus.notify")){
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[EnderPlus]&r Today is my Birthday :D Leave a review on spigot as a gift :3"));
            }
        }

        if (EnderPlus.getConfiguration().getBoolean("Enderchest.Convert-onJoin")) {
            if (!event.getPlayer().getEnderChest().isEmpty()) {
                ArrayList<ItemStack> prunedItems = new ArrayList<>();

                Arrays.stream(event.getPlayer().getEnderChest().getContents())
                        .filter(Objects::nonNull)
                        .forEach(prunedItems::add);

                EnderUtils.storeItems(prunedItems, p);
            }
        }

    }
}
