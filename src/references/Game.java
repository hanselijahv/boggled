package references;

import java.util.List;

public class Game {
    public String gameId;
    private List<Player> playerList;
    private Leaderboard winner;

    public Game(String gameId, List<Player> playerList, Leaderboard winner) {
        this.gameId = gameId;
        this.playerList = playerList;
        this.winner = winner;
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

    public Leaderboard getWinner() {
        return winner;
    }

    public void setWinner(Leaderboard winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId='" + gameId + '\'' +
                ", playerList=" + playerList +
                ", winner=" + winner +
                '}';
    }
}
