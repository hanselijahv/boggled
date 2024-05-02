package net.team6.boggled.common.model;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameRoom {
	private String roomId;
	private List<Account> accounts;
	private boolean gameStarted;
	private Timer timer;

	public GameRoom(String roomId, List<Account> accounts, boolean gameStarted) {
		this.roomId = roomId;
		this.accounts = accounts;
		this.gameStarted = gameStarted;
	}

	public synchronized void joinRoom(Account account) {
		if (gameStarted) {
			// TODO: Delete print statements, transfer to controller
			System.out.println( "[" + account.getUsername() + "] Game has already started. Cannot join the room.");

		} else {
			accounts.add(account);
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

	public List<Account> getPlayers() {
		return accounts;
	}

	public void setPlayers(List<Account> accounts) {
		this.accounts = accounts;
	}

	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	@Override
	public String toString() {
		return "Game Room Details: \n" +
			 "\tRoom ID: " + roomId + "\n" +
			 "\tPlayers: " + accounts + "\n" +
			 "\tGame Started: " + gameStarted;
	}
}
