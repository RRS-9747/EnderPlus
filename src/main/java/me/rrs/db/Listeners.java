package me.rrs.db;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

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
            database.createEnderData(enderData);
        }

        return enderData;
    }
}
