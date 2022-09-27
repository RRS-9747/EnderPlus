package me.rrs.database;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.rrs.EnderPlus;
import org.bukkit.Bukkit;

import java.sql.*;


public class Database{

    private Connection connection;

    private final YamlDocument config = EnderPlus.getConfiguration();

    private Connection getConnection() throws SQLException {
        if (null != this.connection) {
            return connection;
        }

        final String url = "jdbc:mysql://" + config.getString("Database.Url") + "/" + config.getString("Database.Name");
        final String user = EnderPlus.getConfiguration().getString("Database.User");
        final String password = EnderPlus.getConfiguration().getString("Database.Password");
        final Connection connection = DriverManager.getConnection(url, user, password);


        this.connection = connection;

        Bukkit.getLogger().info("Connected to database.");

        return connection;

    }

    public final void initializeDatabase() throws SQLException {

        Statement statement = getConnection().createStatement();

        final String sql = "CREATE TABLE IF NOT EXISTS enderplusdata (uuid varchar(36) primary key, data LONGTEXT)";

        statement.execute(sql);

        statement.close();

    }


    public final EnderData findEnderDataByUUID(String uuid) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM enderplusdata WHERE uuid = ?");
        statement.setString(1, uuid);

        ResultSet resultSet = statement.executeQuery();

        EnderData enderData;

        if (resultSet.next()) {

            enderData = new EnderData(resultSet.getString("uuid"), resultSet.getString("data"));

            statement.close();

            return enderData;
        }

        statement.close();

        return null;
    }

    public final void createEnderData(EnderData enderData) throws SQLException {

        PreparedStatement statement = getConnection()
                .prepareStatement("INSERT INTO enderplusdata(uuid, data) VALUES (?, ?)");
        statement.setString(1, enderData.getPlayerUUID());
        statement.setString(2, enderData.getData());

        statement.executeUpdate();

        statement.close();

    }


    public final void updateEnderData(EnderData enderData) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement(
                "UPDATE enderplusdata SET data = ? WHERE uuid = ?");
        statement.setString(1, enderData.getData());
        statement.setString(2, enderData.getPlayerUUID());

        statement.executeUpdate();

        statement.close();
    }

}
