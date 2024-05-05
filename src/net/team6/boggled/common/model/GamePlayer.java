package net.team6.boggled.common.model;

public class GamePlayer {
	private String gameID;
	private String playerID;
	private boolean playerConnected;

	public GamePlayer(String gameID, String playerID, boolean player_connected) {
		this.gameID = gameID;
		this.playerID = playerID;
		this.playerConnected = player_connected;
	}

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

	public boolean isPlayerConnected() {
		return playerConnected;
	}

	public void setPlayerConnected(boolean playerConnected) {
		this.playerConnected = playerConnected;
	}

	@Override
	public String toString() {
		return "Game ID: " + gameID + "\nPlayer ID: " + playerID + "\nConnected: " + playerConnected;
	}
}
