package me.rrs.enderplus.utils;

import me.rrs.enderplus.EnderPlus;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

public class Serializers {

    public static final NamespacedKey NAMESPACED_KEY = new NamespacedKey(EnderPlus.getInstance(), "EnderPlus");

    public static String serialize(ItemStack[] itemStacks) {
        try {
            ByteArrayOutputStream io = new ByteArrayOutputStream();
            BukkitObjectOutputStream in = new BukkitObjectOutputStream(io);

            in.writeInt(itemStacks.length);

            for (ItemStack itemStack : itemStacks) {
                in.writeObject(itemStack);
            }

            in.close();
            io.close();

            return Base64.getEncoder().encodeToString(io.toByteArray());
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    public static ItemStack[] deserialize(String encodedData) {
        if(encodedData == null || encodedData.isEmpty()) return new ItemStack[0];
        try {
            ByteArrayInputStream io = new ByteArrayInputStream(Base64.getDecoder().decode(encodedData));
            BukkitObjectInputStream in = new BukkitObjectInputStream(io);
            ItemStack[] items = new ItemStack[in.readInt()];

            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) in.readObject();
            }

            io.close();
            return items;
        } catch (Exception e) {
            e.printStackTrace();
            return new ItemStack[0];
        }
    }

    public static ArrayList<ItemStack> retrieveItems(final Player p) {

        String encodedItems;

        final PersistentDataContainer data = p.getPersistentDataContainer();
        final ArrayList<ItemStack> items = new ArrayList<>();

        if (!data.has(NAMESPACED_KEY, PersistentDataType.STRING)) {
            return items;
        }

        if (EnderPlus.getConfiguration().getBoolean("Database.Enable")) {
            try {
                encodedItems = EnderPlus.getDatabase().getDataByUuid(p.getUniqueId().toString());
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            encodedItems = data.get(NAMESPACED_KEY, PersistentDataType.STRING);
        }
        if (encodedItems != null && !encodedItems.isEmpty()) {
            final byte[] rawData = Base64.getDecoder().decode(encodedItems);
            try {
                final ByteArrayInputStream io = new ByteArrayInputStream(rawData);
                final BukkitObjectInputStream in = new BukkitObjectInputStream(io);
                int itemsCount = in.readInt();
                for (int i = 0; i < itemsCount; i++) {
                    items.add((ItemStack) in.readObject());
                }
                in.close();
                io.close();
            } catch (final IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return items;
    }
}