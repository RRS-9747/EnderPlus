package me.rrs.utils;

import me.rrs.EnderPlus;
import me.rrs.database.EnderData;
import me.rrs.database.Listeners;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
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
import java.util.List;

public class Serializers {

    public static String getEncodedItem() {
        return encodedItem;
    }

    private static String encodedItem;



    public void storeItems(Inventory inv, Player p) {

        final PersistentDataContainer data = p.getPersistentDataContainer();
            try {
                final ByteArrayOutputStream io = new ByteArrayOutputStream();
                final BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
                os.writeObject(inv.getContents());
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


    public ItemStack[] getItems(final Player p) {

        String encodedData;

        final PersistentDataContainer data = p.getPersistentDataContainer();
        ItemStack[] itemStacks = new ItemStack[0];

        if (Boolean.TRUE.equals(EnderPlus.getConfiguration().getBoolean("Database.Enable"))) {
            try {
                EnderData ed = Listeners.getPlayerFromDatabase(p);
                encodedData = ed.getData();
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            encodedData = data.get(new NamespacedKey(EnderPlus.getInstance(), "EnderPlus"), PersistentDataType.STRING);
        }
        if (encodedData!= null && !encodedData.isEmpty()) {
            final byte[] rawData = Base64.getDecoder().decode(encodedData);

            try {
                final ByteArrayInputStream io = new ByteArrayInputStream(rawData);
                final BukkitObjectInputStream in = new BukkitObjectInputStream(io);
                itemStacks = (ItemStack[]) in.readObject();
                in.close();
                io.close();
            } catch (final IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return itemStacks;
    }


    //TODO -> Remove soon
    public List<ItemStack> getItem(final Player p) {

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
            } catch (final IOException | ClassNotFoundException ignored) {

            }
        }
        return items;
    }

}
