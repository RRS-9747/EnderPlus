package me.rrs.enderplus;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.Pattern;
import dev.dejvokep.boostedyaml.dvs.segment.Segment;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import me.rrs.enderplus.commands.EnderChest;
import me.rrs.enderplus.commands.MainCommand;
import me.rrs.enderplus.database.Database;
import me.rrs.enderplus.database.Listeners;
import me.rrs.enderplus.listeners.*;
import me.rrs.enderplus.utils.UpdateAPI;
import me.rrs.enderplus.utils.EnderPlusExpansion;
import me.rrs.enderplus.utils.TabComplete;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public final class EnderPlus extends JavaPlugin {

    private static EnderPlus instance;
    private static YamlDocument lang;
    private static YamlDocument config;


    public static EnderPlus getInstance(){
        return instance;
    }
    public static YamlDocument getConfiguration() {
        return config;
    }
    public static YamlDocument getLang() {
        return lang;
    }


    @Override
    public void onLoad(){
        try {
            config = YamlDocument.create(new File(getDataFolder(), "config.yml"), getResource("config.yml"),
                    GeneralSettings.DEFAULT,
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder().setAutoSave(true).setVersioning(new Pattern(Segment.range(1, Integer.MAX_VALUE),
                            Segment.literal("."), Segment.range(0, 10)), "Config.Version").build());

            lang = YamlDocument.create(new File(getDataFolder(), "lang.yml"), getResource("lang.yml"),
                    GeneralSettings.DEFAULT,
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder().setAutoSave(true).setVersioning(new Pattern(Segment.range(1, Integer.MAX_VALUE),
                            Segment.literal("."), Segment.range(0, 10)), "Version").build());
        } catch (final IOException e) {
            e.printStackTrace();
        }
        instance = this;
    }

    @Override
    public void onEnable() {
        if (!"EnderPlus".equals(this.getDescription().getName())) {
            Bukkit.getLogger().severe("Something wrong! Please download a fresh jar file from https://www.spigotmc.org/resources/100897/");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        Bukkit.getLogger().info("");
        Bukkit.getLogger().info("███████╗███╗  ██╗██████╗ ███████╗██████╗ ██████╗ ██╗     ██╗   ██╗ ██████╗");
        Bukkit.getLogger().info("██╔════╝████╗ ██║██╔══██╗██╔════╝██╔══██╗██╔══██╗██║     ██║   ██║██╔════╝");
        Bukkit.getLogger().info("█████╗  ██╔██╗██║██║  ██║█████╗  ██████╔╝██████╔╝██║     ██║   ██║╚█████╗ ");
        Bukkit.getLogger().info("██╔══╝  ██║╚████║██║  ██║██╔══╝  ██╔══██╗██╔═══╝ ██║     ██║   ██║ ╚═══██╗");
        Bukkit.getLogger().info("███████╗██║ ╚███║██████╔╝███████╗██║  ██║██║     ███████╗╚██████╔╝██████╔╝");
        Bukkit.getLogger().info("╚══════╝╚═╝  ╚══╝╚═════╝ ╚══════╝╚═╝  ╚═╝╚═╝     ╚══════╝ ╚═════╝ ╚═════╝ ");
        Bukkit.getLogger().info("");
        Bukkit.getLogger().info("--------------------------------------------------------------------------");
        Bukkit.getLogger().info("[EnderPlus] EnderPlus " + getDescription().getVersion()+ " by RRS");

        new Metrics(this, 14719);

        getServer().getPluginManager().registerEvents(new EnderPlusOpen(), this);
        getServer().getPluginManager().registerEvents(new EnderPlusSave(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new EchestViewer(), this);
        getServer().getPluginManager().registerEvents(new Blacklist(), this);
        getCommand("enderchest").setExecutor(new EnderChest());
        getCommand("enderplus").setExecutor(new MainCommand());
        getCommand("enderplus").setTabCompleter(new TabComplete());
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            (new EnderPlusExpansion()).register();
        }

        databaseChecker();
        updateChecker();

        Bukkit.getLogger().info("[EnderPlus] Enabled successfully!");
        Bukkit.getLogger().info("--------------------------------------------------------------------------");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().warning("EnderPlus Disabled.");
    }



    public void updateChecker(){
        UpdateAPI updateAPI = new UpdateAPI();
        new BukkitRunnable(){
            @Override
            public void run() {
                if (updateAPI.hasGithubUpdate("RRS-9747", "EnderPlus")) {
                    String version = updateAPI.getGithubVersion("RRS-9747", "EnderPlus");
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.hasPermission("enderplus.notify")) {
                            p.sendMessage("--------------------------------");
                            p.sendMessage("You are using EnderPlus " + EnderPlus.getInstance().getDescription().getVersion());
                            p.sendMessage("However version " + version + " is available.");
                            p.sendMessage("You can download it from: " + "https://www.spigotmc.org/resources/100897/");
                            p.sendMessage("--------------------------------");
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(this, 0L, 20L * 60 * config.getInt("Config.Update-Checker-Interval"));
    }

    public void databaseChecker(){
        if (Boolean.TRUE.equals(config.getBoolean("Database.Enable"))) {
            Bukkit.getLogger().info("[EnderPlus] Enabling Database");
            Database database;
            try {
                database = new Database();
                database.initializeDatabase();
            } catch (final SQLException e) {
                e.printStackTrace();
                Bukkit.getLogger().severe("[EnderPlus] Could not initialize database.");
                Bukkit.getLogger().info("[EnderPlus] Please check database info and make sure you have internet connection.");
                Bukkit.getLogger().severe("[EnderPlus] Disabling now");
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }
            getServer().getPluginManager().registerEvents(new Listeners(database), this);
        }
    }

}



