package com.wordgame.references;

public class Player {
    private String playerId;
    private String username;
    private String password;

    public Player(String playerId, String username, String password) {
        this.playerId = playerId;
        this.username = username;
        this.password = password;
    }

    public Player() {
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
        return "Player ID: " + playerId + "\nUsername: " + username + "\nPassword: " + password;
    }
}
