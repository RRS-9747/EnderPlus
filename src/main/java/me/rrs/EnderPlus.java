package me.rrs;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.Pattern;
import dev.dejvokep.boostedyaml.dvs.segment.Segment;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import me.rrs.commands.EnderChest;
import me.rrs.commands.MainCommand;
import me.rrs.listeners.EnderChestOpen;
import me.rrs.listeners.InventoryClose;
import me.rrs.listeners.PlayerJoin;
import me.rrs.utils.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class EnderPlus extends JavaPlugin {

    private static EnderPlus plugin;
    private static YamlDocument config;
    private static YamlDocument lang;

    public static EnderPlus getInstance() {
        return plugin;
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


        plugin = this;

        getCommand("enderchest").setExecutor(new EnderChest());
        getCommand("enderplus").setExecutor(new MainCommand());

        getCommand("enderplus").setTabCompleter(new TabComplete());

        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new InventoryClose(), this);
        getServer().getPluginManager().registerEvents(new EnderChestOpen(), this);


    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().warning("EnderPlus Disabled.");
    }

}
