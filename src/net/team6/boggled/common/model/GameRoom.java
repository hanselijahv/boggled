package net.team6.boggled.common.model;

public class GameRoom {
	private String roomId;
	private boolean gameStarted;

	public GameRoom(String roomId, boolean gameStarted) {
		this.roomId = roomId;
		this.gameStarted = gameStarted;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public boolean isGameStarted() {
		return gameStarted;
	}

	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	@Override
	public String toString() {
		return "Game Room Details: \n" +
			 "\tRoom ID: " + roomId + "\n" +
			 "\tGame Started: " + gameStarted;
	}
}
