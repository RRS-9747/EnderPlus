package me.rrs.enderplus.utils;

import me.rrs.enderplus.EnderPlus;
import me.rrs.enderplus.database.EnderData;
import me.rrs.enderplus.database.Listeners;
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



    public void storeItems(ArrayList<? extends ItemStack> items, Player p) {

        final PersistentDataContainer data = p.getPersistentDataContainer();

        if (items.isEmpty()) {
            data.set(new NamespacedKey(EnderPlus.getInstance(), "EnderPlus"), PersistentDataType.STRING, "");
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

                String encodedData = Base64.getEncoder().encodeToString(rawData);
                encodedItem = encodedData;

                data.set(new NamespacedKey(EnderPlus.getInstance(), "EnderPlus"), PersistentDataType.STRING, encodedData);

                os.close();
                io.close();

            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    public ArrayList<ItemStack> getItems(final Player p) {

        String encodedItems;

        final PersistentDataContainer data = p.getPersistentDataContainer();
        final ArrayList<ItemStack> items = new ArrayList<>();

        if (Boolean.TRUE.equals(EnderPlus.getConfiguration().getBoolean("Database.Enable"))) {
            try {
                EnderData ed = Listeners.getPlayerFromDatabase(p);
                encodedItems = ed.getData();
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            encodedItems = data.get(new NamespacedKey(EnderPlus.getInstance(), "EnderPlus"), PersistentDataType.STRING);
        }
        if (!encodedItems.isEmpty()) {
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
