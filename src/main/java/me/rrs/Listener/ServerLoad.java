package me.rrs.Listener;

import me.rrs.EnderPlus;
import me.rrs.Util.UpdateAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;


public class ServerLoad implements Listener {



    @EventHandler
    public void onLoad(ServerLoadEvent event){


        boolean hasUpdateGitHub = UpdateAPI.hasGithubUpdate("RRS-9747", "EnderPlus");
        boolean updateChecker = EnderPlus.getConfiguration().getBoolean("Config.Update-Checker");




        if (hasUpdateGitHub){
                if (updateChecker) {
                    Bukkit.getLogger().warning("EnderPlus new version is available!");
                    Bukkit.getLogger().warning("Download it from: https://bit.ly/3D8VM19");

                }
        }
    }
}
