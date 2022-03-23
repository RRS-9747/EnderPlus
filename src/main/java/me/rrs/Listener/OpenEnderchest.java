package me.rrs.Listener;

import me.rrs.Util.Util;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OpenEnderchest implements Listener {


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() != null) {
            if (e.getClickedBlock().getType() == Material.ENDER_CHEST) {
                if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (!e.getPlayer().isOp()) {
                        e.setCancelled(true);


                        if (e.getPlayer().hasPermission("enderplus.lvl.1")) {
                            Util.inv(e.getPlayer(), 9);
                        }
                        if (e.getPlayer().hasPermission("enderplus.lvl.2")) {
                            Util.inv(e.getPlayer(), 18);
                        }
                        if (e.getPlayer().hasPermission("enderplus.lvl.3")) {
                            Util.inv(e.getPlayer(), 27);
                        }
                        if (e.getPlayer().hasPermission("enderplus.lvl.4")) {
                            Util.inv(e.getPlayer(), 36);
                        }
                        if (e.getPlayer().hasPermission("enderplus.lvl.5")) {
                            Util.inv(e.getPlayer(), 45);
                        }
                        if (e.getPlayer().hasPermission("enderplus.lvl.6")) {
                            Util.inv(e.getPlayer(), 54);
                        }

                    }else{
                        Util.inv(e.getPlayer(), 54);
                    }
                }
            }
        }
    }
}
