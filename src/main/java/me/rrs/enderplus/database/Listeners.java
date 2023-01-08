package me.rrs.enderplus.database;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.sql.SQLException;

public class Listeners implements Listener {

    public static Database database;

    public Listeners(final Database database) {
        Listeners.database = database;
    }

    public static EnderData getPlayerFromDatabase(final Player player) throws SQLException {

        EnderData playerData = database.findEnderDataByUUID(player.getUniqueId().toString());

        if (null == playerData) {
            playerData = new EnderData(player.getUniqueId().toString(), "");
            final EnderData finalEnderData = playerData;
            try {
                database.createEnderData(finalEnderData);
            } catch (final SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return playerData;
    }

}
