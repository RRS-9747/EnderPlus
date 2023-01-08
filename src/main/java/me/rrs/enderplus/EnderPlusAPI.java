package me.rrs.enderplus;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.rrs.enderplus.utils.InvUtils;
import me.rrs.enderplus.utils.Storage;
import me.rrs.enderplus.utils.UpdateAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EnderPlusAPI {

    public int getRow(Player player){
        int i;
        for (i = 6; i > 0; i--) {
            if (player.hasPermission("enderplus.lvl." + i)) {
                break;
            }
        }
        return i;
    }

    public void openEnderChest(Player sender, Player holder, boolean isOwnInv) {
        YamlDocument config = EnderPlus.getConfiguration();
        InvUtils invUtils = new InvUtils();
        int i;
        for (i = 6; i > 0; i--) {
            if (holder.hasPermission(String.format("enderplus.lvl.%d", i)) || holder.hasPermission("enderplus.lvl.*")) {
                String rowName = config.getString("EnderChest.Name.row-" + i);
                invUtils.openEnderInv(sender, holder, i * 9, rowName, isOwnInv);
                break;
            }
        }
    }

    public boolean getStatus(Player holder){
        return Storage.STATUS.get(holder) != null;
    }

    public List<ItemStack> getBlacklist(){
        List<String> blacklist = EnderPlus.getConfiguration().getStringList("EnderChest.Blacklist");
        List<ItemStack> itemStacks = new ArrayList<>();
        for (String name : blacklist) {
            ItemStack itemStack = new ItemStack(Material.getMaterial(name));
            itemStacks.add(itemStack);
        }
        return itemStacks;
    }

    public Inventory getEnderPlus(){
      return InvUtils.getEnderPlus();
    }

    public boolean isLatest(){
        UpdateAPI api = new UpdateAPI();
        return !api.hasGithubUpdate("RRS-9747", "EnderPlus");
    }

    public boolean isUsingDatabase(){
        return Boolean.TRUE.equals(EnderPlus.getConfiguration().getBoolean("Database.Enable"));
    }


}
