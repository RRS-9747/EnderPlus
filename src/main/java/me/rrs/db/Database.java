package me.rrs.db;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.rrs.EnderPlus;
import org.bukkit.Bukkit;

import java.sql.*;

public class Database {

    private Connection connection;

    YamlDocument config = EnderPlus.getConfiguration();

    public Connection getConnection() throws SQLException {
        if(connection != null){
            return connection;
        }

        String url = "jdbc:mysql://" + config.getString( "Database.Url") + "/" + config.getString("Database.Name");
        String user = EnderPlus.getConfiguration().getString("Database.User");
        String password = EnderPlus.getConfiguration().getString("Database.Password");
        Connection connection = DriverManager.getConnection(url, user, password);

        this.connection = connection;

        Bukkit.getLogger().info("Connected to database.");

        return connection;

    }

    public void initializeDatabase() throws SQLException {

        Statement statement = getConnection().createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS enderplusdata (uuid varchar(36) primary key, data LONGTEXT)";

        statement.execute(sql);

        statement.close();

    }

    public EnderData findEnderDataByUUID(String uuid) throws SQLException {
        
        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM enderplusdata WHERE uuid = ?");
        statement.setString(1, uuid);

        ResultSet resultSet = statement.executeQuery();

        EnderData enderData;

        if(resultSet.next()){

            enderData = new EnderData(resultSet.getString("uuid"), resultSet.getString("data"));

            statement.close();

            return enderData;
        }

        statement.close();

        return null;
    }

    public void createEnderData(EnderData enderData) throws SQLException {

        PreparedStatement statement = getConnection()
                .prepareStatement("INSERT INTO enderplusdata(uuid, data) VALUES (?, ?)");
        statement.setString(1, enderData.getPlayerUUID());
        statement.setString(2, enderData.getdata());

        statement.executeUpdate();

        statement.close();

    }


    public void updateEnderData(EnderData enderData) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("UPDATE enderplusdata SET data = ? WHERE uuid = ?");
        statement.setString(1, enderData.getdata());
        statement.setString(2, enderData.getPlayerUUID());

        statement.executeUpdate();

        statement.close();
    }

}
