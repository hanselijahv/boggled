package net.team6.boggled.common.model;


import net.team6.boggled.run.BoggledImplementation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import static net.team6.boggled.common.db.SettingsDAO.settingsDAOImpl;

public class WaitingRoom {
	private final String creator;
	private final ConcurrentHashMap<String, Boolean> players = new ConcurrentHashMap<>();
	public long startTime;
	private Timer timer;
	private long timerDuration = getWaitingTime();
	private GameRoom gameRoom = new GameRoom();

	private String gameId;

	private BoggledImplementation boggledImplementation;  // test

	/**
	 * Constructor for the WaitingRoom class.
	 *
	 * @param boggledImplementation the BoggledImplementation instance
	 * @param creator the creator of the waiting room
	 */
	public WaitingRoom(BoggledImplementation boggledImplementation, String creator) {
		this.boggledImplementation = boggledImplementation;
		gameId = gameRoom.createGameId();
		this.creator = creator;
		startTime = System.currentTimeMillis();
		players.put(creator, true);
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (isNotEnough()) {
					System.out.println("Not enough players to start the game.");
				} else {
					if (isReadyToStart()) {
						List<String> playersInWaitingRoom = new ArrayList<>();
						for (WaitingRoom room : getWaitingRooms().values()) {
							playersInWaitingRoom.addAll(room.getPlayers());
						}
						gameRoom.setPlayers(playersInWaitingRoom);
						gameRoom.start();
						getGameRooms().put(gameId, gameRoom);
						System.out.println("GAME STARTED FOR GAME: " + gameId + "\nPLAYERS: " + gameRoom.players);
					}
				}
				System.out.println("Waiting room dissolved: " + creator);
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						getWaitingRooms().clear();
					}
				}, 500);
				timer.cancel();
			}
		}, (timerDuration) * 1000);
	}

	/**
	 * Adds a player to the waiting room.
	 *
	 * @param playerName the name of the player
	 */
	public void joinPlayer(String playerName) {
		players.put(playerName, true);
	}

	/**
	 * Checks if the waiting room is ready to start a game.
	 *
	 * @return true if the waiting room is ready to start a game, false otherwise
	 */
	public boolean isReadyToStart() {
		return players.size() >= 2;
	}

	/**
	 * Checks if there are not enough players in the waiting room.
	 *
	 * @return true if there are not enough players, false otherwise
	 */
	public boolean isNotEnough() {
		return players.size() <= 1;
	}

	/**
	 * Returns the remaining time for the waiting room.
	 *
	 * @return the remaining time in seconds
	 */
	public String getRemainingTime() {
		long elapsedTime = System.currentTimeMillis() - startTime;
		long remainingTime = Math.max(0, timerDuration - (elapsedTime / 1000));
		long seconds = (remainingTime % 60);
		return String.format("%02d", seconds);
	}


	public List<String> getPlayers() {
		return new ArrayList<>(players.keySet());
	}

	/**
	 * Returns the waiting time for the waiting room.
	 *
	 * @return the waiting time in seconds
	 */
	public int getWaitingTime() {
		int duration;
		try {
			List<Settings> waitingTimeSetting = settingsDAOImpl.getAll();
			Settings setting = waitingTimeSetting.get(0);
			duration = setting.getWaitingTime();
			System.out.println(duration);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return duration;
	}

	public String getGameId() {
		return gameId;
	}

	public ConcurrentHashMap<String, WaitingRoom> getWaitingRooms() {
		return boggledImplementation.getWaitingRooms();
	}

	public ConcurrentHashMap<String, GameRoom> getGameRooms() {
		return boggledImplementation.getGameRooms();
	}

	public String getCreator() {
		return creator;
	}

	public long getStartTime() {
		return startTime;
	}

	public Timer getTimer() {
		return timer;
	}

	public long getTimerDuration() {
		return timerDuration;
	}

	public GameRoom getGameRoom() {
		return gameRoom;
	}
}