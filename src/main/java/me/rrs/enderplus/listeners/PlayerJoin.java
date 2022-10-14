package me.rrs.enderplus.listeners;

import me.rrs.enderplus.EnderPlus;
import me.rrs.enderplus.database.EnderData;
import me.rrs.enderplus.database.Listeners;
import me.rrs.enderplus.utils.Serializers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PlayerJoin implements Listener {


    protected final Date date = new Date();
    protected final DateFormat dateFormat = new SimpleDateFormat("dd/MM");
    protected final String currentDate = dateFormat.format(date);
    final Serializers utils = new Serializers();


    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player p = event.getPlayer();
        final PersistentDataContainer data = p.getPersistentDataContainer();

        if (!data.has(new NamespacedKey(EnderPlus.getInstance(), "EnderPlus"), PersistentDataType.STRING)) {
            data.set(new NamespacedKey(EnderPlus.getInstance(), "EnderPlus"), PersistentDataType.STRING, "");
        }

        if ("23/3".equals(currentDate) && p.hasPermission("enderplus.notify")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[EnderPlus]&r Today is my Birthday :D Leave a review on spigot as a gift :3"));
        }


        if (Boolean.TRUE.equals(EnderPlus.getConfiguration().getBoolean("EnderChest.Convert-onJoin")) && !p.getEnderChest().isEmpty()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    utils.storeItems(p.getEnderChest(), p);
                    if (Boolean.TRUE.equals(EnderPlus.getConfiguration().getBoolean("Database.Enable"))) {
                        try {
                            final EnderData enderData = Listeners.getPlayerFromDatabase(p);
                            enderData.setData(Serializers.getEncodedItem());
                            Listeners.getDatabase().updateEnderData(enderData);
                        } catch (final SQLException exception) {
                            exception.printStackTrace();
                            Bukkit.getLogger().severe("[EnderPlus] Could not update EnderChest items after chest close!");
                        }
                    }
                    p.getEnderChest().clear();
                }
            }.runTaskAsynchronously(EnderPlus.getInstance());
        }
    }
}
