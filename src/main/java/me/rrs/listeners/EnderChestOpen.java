package me.rrs.listeners;

import me.rrs.utils.EnderUtils;
import me.rrs.utils.InvUtils;
import me.rrs.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class EnderChestOpen implements Listener {

    Inventory enderplus;
    Lang lang = new Lang();

    @EventHandler
    public void onEnderChestOpen(PlayerInteractEvent event){

        if (event.getClickedBlock() != null) {
            if (event.getClickedBlock().getType() == Material.ENDER_CHEST) {
                if (!event.isCancelled()) {
                    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        event.setCancelled(true);

                        Player p = event.getPlayer();

                        if (p.hasPermission("enderplus.lvl.6")) {
                            InvUtils.enderInv(p, 54);

                        } else if (p.hasPermission("enderplus.lvl.5")) {
                            InvUtils.enderInv(p, 45);

                        } else if (p.hasPermission("enderplus.lvl.4")) {
                            InvUtils.enderInv(p, 36);

                        } else if (p.hasPermission("enderplus.lvl.3")) {
                            InvUtils.enderInv(p, 27);

                        } else if (p.hasPermission("enderplus.lvl.2")) {
                            InvUtils.enderInv(p, 18);

                        } else if (p.hasPermission("enderplus.lvl.1")) {
                            InvUtils.enderInv(p, 9);

                        } else lang.msg("&c&l[EnderPlus]&r", "No-Echest", p);

                    }
                }
            }
        }
    }
}
