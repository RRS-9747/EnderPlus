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

    public static String getEncodedItem() {
        return encodedItem;
    }

    private static String encodedItem;


    private final NamespacedKey NAMESPACED_KEY = new NamespacedKey(EnderPlus.getInstance(), "EnderPlus");
    private final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();

    public void storeItems(ArrayList<? extends ItemStack> items, Player p) {
        final PersistentDataContainer data = p.getPersistentDataContainer();

        if (items.isEmpty()) {
            data.set(NAMESPACED_KEY, PersistentDataType.STRING, "");
        } else {
            try {
                final ByteArrayOutputStream io = new ByteArrayOutputStream();
                final BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
                os.writeInt(items.size());
                for (ItemStack item : items) {
                    os.writeObject(item);
                }
                os.flush();
                io.flush();

                byte[] rawData = io.toByteArray();

                String encodedData = BASE64_ENCODER.encodeToString(rawData);
                encodedItem = encodedData;

                data.set(NAMESPACED_KEY, PersistentDataType.STRING, encodedData);

                os.close();
                io.close();

            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public ArrayList<ItemStack> retrieveItems(final Player p) {

        String encodedItems;

        final PersistentDataContainer data = p.getPersistentDataContainer();
        final ArrayList<ItemStack> items = new ArrayList<>();

        if (!data.has(NAMESPACED_KEY, PersistentDataType.STRING)) {
            return items;
        }

        if (Boolean.TRUE.equals(EnderPlus.getConfiguration().getBoolean("Database.Enable"))) {
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
