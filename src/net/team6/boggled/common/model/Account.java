package net.team6.boggled.common.model;

public class Account {
    private String playerId;
    private String username;
    private String password;

    public Account(String playerId, String username, String password) {
        this.playerId = playerId;
        this.username = username;
        this.password = password;
    }

    public Account() {
        this.playerId = null;
        this.username = null;
        this.password = null;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "\nPlayer ID: " + playerId + "\nUsername: " + username;
    }
}
