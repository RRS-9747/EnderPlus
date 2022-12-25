package me.rrs.enderplus.database;

public class EnderData {
    private final String playerUUID;
    private String data;

    public EnderData(final String playerUUID, final String data) {
        this.playerUUID = playerUUID;
        this.data = data;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public String getData() {
        return data;
    }

    public void setData(final String data) {
        this.data = data;
    }
}
