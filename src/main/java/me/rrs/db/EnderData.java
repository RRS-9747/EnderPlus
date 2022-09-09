package me.rrs.db;

public class EnderData {
    private final String playerUUID;
    private String data;

    public EnderData(String playerUUID, String data) {
        this.playerUUID = playerUUID;
        this.data = data;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public String getdata() {
        return data;
    }

    public void setdata(String data) {
        this.data = data;
    }
}
