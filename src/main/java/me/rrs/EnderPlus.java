package me.rrs;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.Pattern;
import dev.dejvokep.boostedyaml.dvs.segment.Segment;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import me.rrs.Commands.*;
import me.rrs.Listener.*;
import me.rrs.Util.Metrics;
import me.rrs.Util.Util;
import me.rrs.Util.Data;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

import static org.bukkit.permissions.PermissionDefault.TRUE;


public final class EnderPlus extends JavaPlugin {

    Util util = new Util();
    Data data = new Data();


    private static EnderPlus Instance;
    private static YamlDocument config;
    private static YamlDocument lang;


    public static EnderPlus getInstance() {
        return Instance;
    }
    public static YamlDocument getConfiguration() {
        return config;
    }
    public static YamlDocument getLang() {
        return lang;
    }

    @Override
    public void onEnable() {

        if (!this.getDescription().getName().equals("EnderPlus")){
            Bukkit.getLogger().severe("Something wrong! Please download a fresh jar file from https://spigotmc.org");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        Metrics metrics = new Metrics(this, 14719);
        Instance = this;
        data.createDataConfig();

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
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Data.getData().contains("data")) {
            Bukkit.getLogger().warning("Restoring EnderChest...");
            util.restoreInvs();
        }

        getServer().getPluginManager().registerEvents(new OpenEnderchest(), this);
        getServer().getPluginManager().registerEvents(new ChestClose(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getCommand("enderchest").setExecutor(new EnderChest());
        getCommand("enderplus").setExecutor(new MainCommand());
        getCommand("enderplus").setTabCompleter(new me.rrs.TabCompleter.EnderPlus());

    }

    @Override
    public void onDisable() {
        if (!Util.Echest.isEmpty()) {
            Bukkit.getLogger().warning("Saving EnderChest...");
            Util.saveInvs();
        }

        Bukkit.getLogger().warning("EnderPlus Disabled.");
    }


}