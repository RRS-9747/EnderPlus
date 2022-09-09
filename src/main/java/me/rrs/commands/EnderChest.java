package me.rrs.commands;

import me.rrs.utils.EnderUtils;
import me.rrs.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class EnderChest implements CommandExecutor {

    Inventory enderplus;

    Lang lang = new Lang();
    EnderUtils enderUtils = new EnderUtils();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {

            Player p = (Player) sender;



            if (args.length > 0) {
                if (p.hasPermission("enderplus.ec.other")) {
                    Player player = Bukkit.getPlayer(args[0]);
                    if (player != null) {
                        ArrayList<ItemStack> enderItems = enderUtils.getItems(player);

                        if (player.hasPermission("enderplus.lvl.6")) {
                            enderplus = Bukkit.createInventory(p, 54, "Ender Chest");

                        } else if (player.hasPermission("enderplus.lvl.5")) {
                            enderplus = Bukkit.createInventory(p, 45, "Ender Chest");

                        } else if (player.hasPermission("enderplus.lvl.4")) {
                            enderplus = Bukkit.createInventory(p, 36, "Ender Chest");

                        } else if (player.hasPermission("enderplus.lvl.3")) {
                            enderplus = Bukkit.createInventory(p, 27, "Ender Chest");

                        } else if (player.hasPermission("enderplus.lvl.2")) {
                            enderplus = Bukkit.createInventory(p, 18, "Ender Chest");

                        } else if (player.hasPermission("enderplus.lvl.1")) {
                            enderplus = Bukkit.createInventory(p, 9, "Ender Chest");

                        } else lang.msg("&c&l[EnderPlus]&r", "No-Echest", p);

                        enderItems
                                .forEach(itemStack -> enderplus.addItem(itemStack));

                        p.openInventory(enderplus);

                    } else lang.msg("&c&l[EnderPlus]&r", "NoPlayer", p);
                } else lang.noPerm(p);

            }else {

                if (p.hasPermission("enderplus.ec.own")) {
                    ArrayList<ItemStack> enderItems = enderUtils.getItems(p);

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

                    } else lang.msg("&c&l[EnderPlus]&r", "No-Echest", p);

                    enderItems
                            .forEach(itemStack -> enderplus.addItem(itemStack));

                    p.openInventory(enderplus);

                }
            }

        } else lang.pcmd();
        return true;
    }
}
