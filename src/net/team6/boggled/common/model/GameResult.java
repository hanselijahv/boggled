package net.team6.boggled.common.model;

public class GameResult {
    public String gameId;
    private String playerId;
    private int winningScore;

    public GameResult(String gameId, String player, int winningScore) {
        this.gameId = gameId;
        this.playerId = player;
        this.winningScore = winningScore;
    }

    public int getWinningScore() {
        return winningScore;
    }

    public void setWinningScore(int winningScore) {
        this.winningScore = winningScore;
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
               "\tHighest Score: " + winningScore;
    }
}
