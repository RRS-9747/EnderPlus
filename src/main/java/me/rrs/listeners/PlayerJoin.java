package me.rrs.listeners;

import me.rrs.EnderPlus;
import me.rrs.EnderPlusAPI;
import me.rrs.database.EnderData;
import me.rrs.database.Listeners;
import me.rrs.utils.EnderUtils;
import me.rrs.utils.UpdateAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PlayerJoin implements Listener {
    private final Date date = new Date();
    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM");
    private final String currentDate = this.dateFormat.format(this.date);


    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {

        final Player p = event.getPlayer();

        final PersistentDataContainer data = p.getPersistentDataContainer();

        if (!data.has(new NamespacedKey(EnderPlus.getInstance(), "EnderPlus"), PersistentDataType.STRING)) {
            data.set(new NamespacedKey(EnderPlus.getInstance(), "EnderPlus"), PersistentDataType.STRING, "");
        }


        if (event.getPlayer().hasPermission("enderplus.notify")) {
            if (EnderPlus.getConfiguration().getBoolean("Config.Update-Checker")) {
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if ("%%__POLYMART__%%".equalsIgnoreCase("1")) {
                            UpdateAPI.getPolyMartUpdate(version -> {
                                if (!EnderPlus.getInstance().getDescription().getVersion().equals(version)) {
                                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "You are using EnderPlus-Premium" + EnderPlus.getInstance().getDescription().getVersion()));
                                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "However version " + version + "is available."));
                                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "You can download it from: https://polymart.org/resource/2865/"));
                                }
                            });

                        } else {
                            if (UpdateAPI.hasGithubUpdate("RRS-9747", "EnderPlus")) {
                                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "You are using EnderPlus" + EnderPlus.getInstance().getDescription().getVersion()));
                                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "However version " + UpdateAPI.getGithubVersion("RRS-9747", "EnderPlus") + " is available."));
                                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "You can download it from: " + "https://www.spigotmc.org/resources/100897/"));
                            }
                        }
                    }
                }.runTaskAsynchronously(EnderPlus.getInstance());
            }
        }


        if ("23/3".equals(this.currentDate)) {
            if (event.getPlayer().hasPermission("enderplus.notify")) {
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[EnderPlus]&r Today is my Birthday :D Leave a review on spigot as a gift :3"));
            }
        }



        if (EnderPlus.getConfiguration().getBoolean("EnderChest.Convert-onJoin")) {
            if (!event.getPlayer().getEnderChest().isEmpty()) {

                final ArrayList<ItemStack> prunedItems = new ArrayList<>();

                Arrays.stream(event.getPlayer().getEnderChest().getContents())
                        .filter(Objects::nonNull)
                        .forEach(prunedItems::add);

                new MyBukkitRunnable(prunedItems, p).runTaskAsynchronously(EnderPlus.getInstance());
                event.getPlayer().getEnderChest().clear();
            }
        }
    }



    private static class MyBukkitRunnable extends BukkitRunnable {
        private final ArrayList<? extends ItemStack> prunedItems;
        private final Player p;

        private MyBukkitRunnable(final ArrayList<? extends ItemStack> prunedItems, final Player p) {
            this.prunedItems = prunedItems;
            this.p = p;
        }

        @Override
        public void run() {
            EnderUtils.storeItems(this.prunedItems, this.p);
            if (EnderPlus.getConfiguration().getBoolean("Database.Enable")) {
                try {
                    final EnderData enderData = Listeners.getPlayerFromDatabase(this.p);

                    if (this.prunedItems.isEmpty()) {
                        enderData.setData("");
                    } else enderData.setData(EnderUtils.encodedItem);

                    Listeners.database.updateEnderData(enderData);
                } catch (final SQLException exception) {
                    exception.printStackTrace();
                    Bukkit.getLogger().severe("Could not update EnderChest items after chest close!");
                }
            }
        }
    }
}
