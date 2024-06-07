package net.team6.boggled.common.model;

public class Player {

    private String playerId;
    private String username;
    private String password;
    private int highestScore;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Player(String playerId, String username, String password, int highestScore) {
        this.playerId = playerId;
        this.username = username;
        this.password = password;
        this.highestScore = highestScore;
    }

    public Player(String playerId, String username, String password) {
        this.playerId = playerId;
        this.username = username;
        this.password = password;
    }

    public Player() {
        this.playerId = null;
        this.username = null;
        this.password = null;
        this.highestScore = 0;
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

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public String toString() {
        return "\nPlayer ID: " + playerId + "\nUsername: " + username;
    }

}
