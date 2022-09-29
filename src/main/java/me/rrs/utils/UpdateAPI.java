package me.rrs.utils;

import me.rrs.EnderPlus;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.net.URL;

public class UpdateAPI {

    public boolean hasGithubUpdate(final String owner, final String repository) {
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

    public String getGithubVersion(final String owner, final String repository) {
        try (final InputStream inputStream = new URL(
                "https://api.github.com/repos/" + owner + "/" + repository + "/releases/latest").openStream()) {
            final JSONObject response = new JSONObject(new JSONTokener(inputStream));
            return response.getString("tag_name");
        } catch (final Exception exception) {
            exception.printStackTrace();
            return EnderPlus.getInstance().getDescription().getVersion();
        }
    }

}