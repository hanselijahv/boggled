package com.wordgame.common.model;

public class Game {
    public String gameId;
    private String playerId;
    private int highestScore;

    public Game(String gameId, String player, int highestScore) {
        this.gameId = gameId;
        this.playerId = player;
        this.highestScore = highestScore;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayer(String playerId) {
        this.playerId = playerId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    @Override
    public String toString() {
        return "Game Details: \n" +
               "\tGame ID: " + gameId + "\n" +
               "\tPlayer ID: " + playerId + "\n" +
               "\tHighest Score: " + highestScore;
    }
}
