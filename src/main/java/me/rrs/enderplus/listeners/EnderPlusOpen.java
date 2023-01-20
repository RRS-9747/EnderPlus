package me.rrs.enderplus.listeners;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.rrs.enderplus.EnderPlus;
import me.rrs.enderplus.utils.Lang;
import me.rrs.enderplus.utils.InvUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.EnderChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnderPlusOpen implements Listener {



    private final Lang lang = new Lang();

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEnderChestOpen(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.isCancelled()) return;
        if (event.isBlockInHand() && event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().isSneaking()) return;
        Block block = event.getClickedBlock();
        if (block == null || block.getType() != Material.ENDER_CHEST) return;

        event.setCancelled(true);
        Player player = event.getPlayer();
        InvUtils invUtils = new InvUtils();
        String prefix = EnderPlus.getLang().getString("Prefix");
        YamlDocument config = EnderPlus.getConfiguration();
        int i;
        for (i = 6; i > 0; i--) {
            if (player.hasPermission(String.format("enderplus.lvl.%d", i)) || player.hasPermission("enderplus.lvl.*")) {
                String rowName = config.getString("EnderChest.Name.row-" + i);
                invUtils.openEnderInv(player, player, i * 9, rowName, true);

                EnderPlus.ENDER_CHEST.put(player, (EnderChest)block.getState());
                EnderChest enderChest = EnderPlus.ENDER_CHEST.get(player);
                if (enderChest != null) {
                    enderChest.open();
                }
                break;
            }
        }
        if (i == 0) {
            lang.msg("&c&l" + prefix + "&r", "No-Echest", player);
        }
    }

}