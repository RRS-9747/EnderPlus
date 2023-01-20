package me.rrs.enderplus.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.dejvokep.boostedyaml.YamlDocument;
import me.rrs.enderplus.EnderPlus;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    private final YamlDocument config = EnderPlus.getConfiguration();
    private HikariDataSource dataSource;

    public void createTable() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS enderplus (name VARCHAR(16), uuid VARCHAR(36), data LONGTEXT, PRIMARY KEY (name, uuid));")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void storeDataByUuid(String name, String uuid, String data) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO enderplus (name, uuid, data) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE name = ?, data = ?;")) {
            statement.setString(1, name);
            statement.setString(2, uuid);
            statement.setString(3, data);
            statement.setString(4, name);
            statement.setString(5, data);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void storeDataByName(String name, String uuid, String data) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO enderplus (name, uuid, data) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE uuid = ?, data = ?;")) {
            statement.setString(1, name);
            statement.setString(2, uuid);
            statement.setString(3, data);
            statement.setString(4, uuid);
            statement.setString(5, data);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getDataByUuid(String uuid) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM enderplus WHERE uuid = ?;")) {
            statement.setString(2, uuid);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getString("data");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDataByPlayerName(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM enderplus WHERE name = ?;")) {
            statement.setString(1, name);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getString("data");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setupDataSource() {
        String connectionString = config.getString("Database.URL");
        String username = config.getString("Database.User");
        String password = config.getString("Database.Password");
        if (connectionString.endsWith(".db")) {
            File dbFile = new File(EnderPlus.getInstance().getDataFolder(), connectionString);
            if (!dbFile.exists()) {
                try {
                    dbFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connectionString = "jdbc:sqlite:" + dbFile.getAbsolutePath();
        }
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(connectionString);
        config.setUsername(username);
        config.setPassword(password);
        this.dataSource = new HikariDataSource(config);
    }
}