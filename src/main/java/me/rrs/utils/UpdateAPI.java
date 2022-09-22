package me.rrs.utils;

import me.rrs.EnderPlus;
import org.bukkit.util.Consumer;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;


public class UpdateAPI {

    public boolean hasGithubUpdate(String owner, String repository) {
        try (InputStream inputStream = new URL(
                "https://api.github.com/repos/" + owner + "/" + repository + "/releases/latest").openStream()) {
            JSONObject response = new JSONObject(new JSONTokener(inputStream));
            String currentVersion = EnderPlus.getInstance().getDescription().getVersion();
            String latestVersion = response.getString("tag_name");
            return !currentVersion.equals(latestVersion);
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public String getGithubVersion(String owner, String repository) {
        try (InputStream inputStream = new URL(
                "https://api.github.com/repos/" + owner + "/" + repository + "/releases/latest").openStream()) {
            JSONObject response = new JSONObject(new JSONTokener(inputStream));
            return response.getString("tag_name");
        } catch (Exception exception) {
            exception.printStackTrace();
            return EnderPlus.getInstance().getDescription().getVersion();
        }
    }

    public void getPolyMartUpdate(final Consumer<String> consumer){
        try (InputStream inputStream = new URL(
                "https://api.polymart.org/v1/getResourceInfoSimple?resource_id=2865&key=version").openStream()) {
            Scanner sc = new Scanner(inputStream);
            if (sc.hasNext()){
                consumer.accept(sc.next());
            }

        } catch (Exception exception) {
        }
    }
}