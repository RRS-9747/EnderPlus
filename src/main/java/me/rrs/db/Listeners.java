package me.rrs.db;

import me.rrs.EnderPlus;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class Listeners implements Listener {
    public static Database database;

    public Listeners(Database database) {
        Listeners.database = database;
    }

    public static EnderData getPlayerFromDatabase(Player player) throws SQLException {

        EnderData enderData = database.findEnderDataByUUID(player.getUniqueId().toString());

        if (enderData == null) {
            enderData = new EnderData(player.getUniqueId().toString(), "");
            EnderData finalEnderData = enderData;

                    try {
                        database.createEnderData(finalEnderData);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

        return enderData;
    }
}
