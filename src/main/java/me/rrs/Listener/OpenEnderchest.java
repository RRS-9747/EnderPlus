package me.rrs.Listener;

import me.rrs.EnderPlus;
import me.rrs.Util.Lang;
import me.rrs.Util.Util;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OpenEnderchest implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent e) {

        Util util = new Util();
        Lang lang = new Lang();

        if (EnderPlus.getConfiguration().getBoolean("Config.Enable")) {

            if (e.getClickedBlock() != null) {
                if (e.getClickedBlock().getType() == Material.ENDER_CHEST) {
                    if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        e.setCancelled(true);
                            if (e.getPlayer().hasPermission("enderplus.lvl.6")) {
                                util.inv(e.getPlayer(), 54);

                            }else if (e.getPlayer().hasPermission("enderplus.lvl.5")) {
                                util.inv(e.getPlayer(), 45);

                            }else if (e.getPlayer().hasPermission("enderplus.lvl.4")) {
                                util.inv(e.getPlayer(), 36);

                            }else if (e.getPlayer().hasPermission("enderplus.lvl.3")) {
                                util.inv(e.getPlayer(), 27);

                            }else if (e.getPlayer().hasPermission("enderplus.lvl.2")) {
                                util.inv(e.getPlayer(), 18);

                            }else if (e.getPlayer().hasPermission("enderplus.lvl.1")) {
                                util.inv(e.getPlayer(), 9);
                            }else lang.msg("&a&l[EnderPlus]&r", "No-Echest", e.getPlayer());

                    }
                }
            }
        }
    }
}
