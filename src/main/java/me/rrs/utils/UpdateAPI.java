package me.rrs.utils;

import me.rrs.EnderPlus;
import org.bukkit.util.Consumer;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public enum UpdateAPI {
    ;

    public static boolean hasGithubUpdate(final String owner, final String repository) {
        try (final InputStream inputStream = new URL(
                "https://api.github.com/repos/" + owner + "/" + repository + "/releases/latest").openStream()) {
            final JSONObject response = new JSONObject(new JSONTokener(inputStream));
            final String currentVersion = EnderPlus.getInstance().getDescription().getVersion();
            final String latestVersion = response.getString("tag_name");
            return !currentVersion.equals(latestVersion);
        } catch (final Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static String getGithubVersion(final String owner, final String repository) {
        try (final InputStream inputStream = new URL(
                "https://api.github.com/repos/" + owner + "/" + repository + "/releases/latest").openStream()) {
            final JSONObject response = new JSONObject(new JSONTokener(inputStream));
            return response.getString("tag_name");
        } catch (final Exception exception) {
            exception.printStackTrace();
            return EnderPlus.getInstance().getDescription().getVersion();
        }
    }

    public static void getPolyMartUpdate(final Consumer<? super String> consumer) {
        try (final InputStream inputStream = new URL(
                "https://api.polymart.org/v1/getResourceInfoSimple?resource_id=2865&key=version").openStream()) {
            try (final Scanner sc = new Scanner(inputStream)) {
                if (sc.hasNext()) {
                    consumer.accept(sc.next());
                }
            }
        } catch (final Exception ignored) {

        }

    }
}