package net.team6.boggled.common.model;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameRoom {
	private String roomId;
	private List<Player> players;
	private boolean gameStarted;
	private Timer timer;

	public GameRoom(String roomId, List<Player> players, boolean gameStarted) {
		this.roomId = roomId;
		this.players = players;
		this.gameStarted = gameStarted;
	}

	public synchronized void joinRoom(Player player) {
		if (gameStarted) {
			// TODO: Delete print statements, transfer to controller
			System.out.println( "[" + player.getUsername() + "] Game has already started. Cannot join the room.");

		} else {
			players.add(player);
		}
	}

	public synchronized boolean isGameStarted() {
		return gameStarted;
	}

	public synchronized void startGame() {
		if (!gameStarted) {
			this.gameStarted = true;
			// TODO: Delete print statements, transfer to controller
			System.out.println("Game started...");
		}
	}

	public void startTimer() {
		if (timer == null) {
			timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					startGame();
				}
			}, 10 * 1000); // This will start the game after 10 seconds
		}
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	@Override
	public String toString() {
		return "Game Room Details: \n" +
			 "\tRoom ID: " + roomId + "\n" +
			 "\tPlayers: " + players + "\n" +
			 "\tGame Started: " + gameStarted;
	}
}
