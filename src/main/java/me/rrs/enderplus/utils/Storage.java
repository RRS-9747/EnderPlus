package me.rrs.enderplus.utils;

import org.bukkit.block.EnderChest;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Storage {

    public static Map<Player, EnderChest> ENDER_CHEST = new HashMap<>();
    public static Map<Player, Boolean> STATUS = new HashMap<>();
}
