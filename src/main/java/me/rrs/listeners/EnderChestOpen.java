package me.rrs.listeners;

import me.rrs.utils.EnderUtils;
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
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    event.setCancelled(true);

                    Player p = event.getPlayer();
                    ArrayList<ItemStack> enderItems = EnderUtils.getItems(p);

                    if (p.hasPermission("enderplus.lvl.6")) {
                        enderplus = Bukkit.createInventory(p, 54, "Ender Chest");

                    } else if (p.hasPermission("enderplus.lvl.5")) {
                        enderplus = Bukkit.createInventory(p, 45, "Ender Chest");

                    } else if (p.hasPermission("enderplus.lvl.4")) {
                        enderplus = Bukkit.createInventory(p, 36, "Ender Chest");

                    } else if (p.hasPermission("enderplus.lvl.3")) {
                        enderplus = Bukkit.createInventory(p, 27, "Ender Chest");

                    } else if (p.hasPermission("enderplus.lvl.2")) {
                        enderplus = Bukkit.createInventory(p, 18, "Ender Chest");

                    } else if (p.hasPermission("enderplus.lvl.1")) {
                        enderplus = Bukkit.createInventory(p, 9, "Ender Chest");

                    }else lang.msg("&c&l[EnderPlus]&r", "No-Echest", p);

                    enderItems.forEach(itemStack -> enderplus.addItem(itemStack));

                    p.openInventory(enderplus);

                    String version = Bukkit.getServer().getVersion();
                    if (version.contains("1.8")) {
                        p.playSound(p.getLocation(), Sound.valueOf("CHEST_OPEN"), 1, 1);

                    } else if (version.contains("1.9") || version.contains("1.10") || version.contains("1.11") || version.contains("1.12")) {
                        p.playSound(p.getLocation(), Sound.valueOf("BLOCK_ENDERCHEST_OPEN"), 1, 1);

                    }else p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1, 1);

                }
            }
        }
    }
}
