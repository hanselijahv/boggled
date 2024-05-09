package net.team6.boggled.common.model;

public class RoundWinner {
    private String gameID;
    private String playerID;
    private int roundScore;

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public int getRoundScore() {
        return roundScore;
    }

    public void setRoundScore(int roundScore) {
        this.roundScore = roundScore;
    }
}
