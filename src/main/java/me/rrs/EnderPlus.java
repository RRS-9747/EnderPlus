package me.rrs;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.Pattern;
import dev.dejvokep.boostedyaml.dvs.segment.Segment;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import me.rrs.Listener.ChestClose;
import me.rrs.Listener.OpenEnderchest;
import me.rrs.Listener.PlayerJoin;
import me.rrs.Listener.ServerLoad;
import me.rrs.Util.Metrics;
import me.rrs.Util.Util;
import me.rrs.Util.Data;
import me.rrs.commands.enderchest;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public final class EnderPlus extends JavaPlugin {

    Util util = new Util();



    private static EnderPlus Instance;
    private static YamlDocument config;


    public static EnderPlus getInstance() {
        return Instance;
    }
    public static YamlDocument getConfiguration() {
        return config;
    }

    @Override
    public void onEnable() {

        if (!this.getDescription().getName().equals("EnderPlus")){
            Bukkit.getLogger().severe("You can't change my name!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        Metrics metrics = new Metrics(this, 14719);
        Instance = this;
        Data data = new Data();
        data.createDataConfig();

        try {
            config = YamlDocument.create(new File(getDataFolder(), "config.yml"), getResource("config.yml"),
                    GeneralSettings.DEFAULT,
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder().setAutoSave(true).setVersioning(new Pattern(Segment.range(1, Integer.MAX_VALUE),
                            Segment.literal("."), Segment.range(0, 10)), "Config.Version").build());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Data.getData().contains("data")) {
            Bukkit.getLogger().warning("Restoring EnderChest...");
            util.restoreInvs();
        }

        getServer().getPluginManager().registerEvents(new OpenEnderchest(), this);
        getServer().getPluginManager().registerEvents(new ChestClose(), this);
        getServer().getPluginManager().registerEvents(new ServerLoad(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getCommand("enderchest").setExecutor(new enderchest());

    }

    @Override
    public void onDisable() {
        if (!Util.Echest.isEmpty()) {
            Bukkit.getLogger().warning("Saving EnderChest...");
            util.saveInvs();
        }

        Bukkit.getLogger().warning("EnderPlus Disabled.");
    }

    @Override
    public void onLoad(){
        Bukkit.getLogger().warning("Join Discord server for support");
        Bukkit.getLogger().warning("https://discord.gg/fV4P2yMSgR");
    }


}