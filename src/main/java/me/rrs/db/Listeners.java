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

        EnderData playerData = database.findEnderDataByUUID(player.getUniqueId().toString());

        if (playerData == null) {
            playerData = new EnderData(player.getUniqueId().toString(), "");
            EnderData finalEnderData = playerData;

                    try {
                        database.createEnderData(finalEnderData);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

        return playerData;
    }
}
