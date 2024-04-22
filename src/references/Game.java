package references;

import java.util.List;

public class Game {
    public String gameId;
    private List<Player> playerList;
    private int highestScore;

    public Game(String gameId, List<Player> playerList, int highestScore) {
        this.gameId = gameId;
        this.playerList = playerList;
        this.highestScore = highestScore;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public String getGameId() {
        return gameId;
    }
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId='" + gameId + '\'' +
                ", playerList=" + playerList +
                '}';
    }
}
