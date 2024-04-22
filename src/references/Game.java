package references;

import java.util.List;

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
        return "Game{" +
                "gameId='" + gameId + '\'' +
                ", playerId=" + playerId +
                '}';
    }
}
