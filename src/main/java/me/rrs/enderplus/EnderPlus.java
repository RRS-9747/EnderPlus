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
import me.rrs.enderplus.listeners.*;
import me.rrs.enderplus.utils.Database;
import me.rrs.enderplus.utils.UpdateAPI;
import me.rrs.enderplus.utils.EnderPlusExpansion;
import me.rrs.enderplus.utils.TabComplete;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public final class EnderPlus extends JavaPlugin {

    private static EnderPlus instance;
    private static YamlDocument lang;
    private static YamlDocument config;
    private static Database database;


    public static EnderPlus getInstance(){
        return instance;
    }
    public static YamlDocument getConfiguration() {
        return config;
    }
    public static YamlDocument getLang() {
        return lang;
    }
    public static Database getDatabase(){
        return database;
    }


    public static Map<Player, Boolean> STATUS;
    public static Map<Player, org.bukkit.block.EnderChest> ENDER_CHEST;


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
        STATUS = new HashMap<>();
        ENDER_CHEST = new HashMap<>();


        Bukkit.getLogger().info("");
        Bukkit.getLogger().info("███████╗███╗  ██╗██████╗ ███████╗██████╗ ██████╗ ██╗     ██╗   ██╗ ██████╗");
        Bukkit.getLogger().info("██╔════╝████╗ ██║██╔══██╗██╔════╝██╔══██╗██╔══██╗██║     ██║   ██║██╔════╝");
        Bukkit.getLogger().info("█████╗  ██╔██╗██║██║  ██║█████╗  ██████╔╝██████╔╝██║     ██║   ██║╚█████╗ ");
        Bukkit.getLogger().info("██╔══╝  ██║╚████║██║  ██║██╔══╝  ██╔══██╗██╔═══╝ ██║     ██║   ██║ ╚═══██╗");
        Bukkit.getLogger().info("███████╗██║ ╚███║██████╔╝███████╗██║  ██║██║     ███████╗╚██████╔╝██████╔╝");
        Bukkit.getLogger().info("╚══════╝╚═╝  ╚══╝╚═════╝ ╚══════╝╚═╝  ╚═╝╚═╝     ╚══════╝ ╚═════╝ ╚═════╝ ");
        Bukkit.getLogger().info("");
        log("\u00A7c--------------------------------------------------------------------------");
        log("&4[&r&dEnderPlus&4] &r&dEnderPlus &bv" + getDescription().getVersion()+ " &4by&r &bRRS&r");

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

        database = new me.rrs.enderplus.utils.Database();
        database.setupDataSource();
        database.createTable();
        updateChecker();
        log("&4[&r&dEnderPlus&4] &aEnabled successfully!&r");
        log("\u00A7c--------------------------------------------------------------------------");
    }

    @Override
    public void onDisable() {
    }



    private void log(String log){
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', log));
    }

    public void updateChecker() {
        UpdateAPI updateAPI = new UpdateAPI();
        String updateMessage = String.format(
                "&7&m==============&r &b&lEnderPlus Update &r&7&m==============\n" +
                        "&a&lHey there! &r&7You are using EnderPlus &7%s\n" +
                        "&c&lWhoa! &r&7A &e&lnew version &ris available: &7%s\n" +
                        "&a&lDon't miss out! &r&7Get the &d&lupdate &rat: &7https://www.spigotmc.org/resources/100897/\n" +
                        "&7&m==============&r &b&lEnderPlus Update &r&7&m==============",
                EnderPlus.getInstance().getDescription().getVersion(),
                updateAPI.getGithubVersion("RRS-9747", "EnderPlus")
        );
        Runnable updateCheckTask = () -> {
            if (updateAPI.hasGithubUpdate("RRS-9747", "EnderPlus")) {
                String message = ChatColor.translateAlternateColorCodes('&', updateMessage);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPermission("enderplus.notify")) {
                        p.sendMessage(message);
                    }
                }
            }
        };
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, updateCheckTask, 0, 20L * 60 * config.getInt("Config.Update-Checker-Interval"));
    }


}



