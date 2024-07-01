package net.team6.boggled.common.model;

import net.team6.boggled.common.db.PlayerDAO;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static net.team6.boggled.common.db.SettingsDAO.settingsDAOImpl;


public class GameRoom {
	private final ConcurrentHashMap<String, Integer> playerStandings = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, Integer> playerScores = new ConcurrentHashMap<>();

	private RoundRoom currentRound;
	public List<String> players;
	private String gameWinner;
	private int winningScore;
	private int currentRoundNumber = 0;
	public long startTime;
	private Timer timer;

	private final int numOfRoundsNeedToWin = getNumRounds();
	private final int roundTime = getRoundTime()+1;

	public RoundRoom getCurrentRound() {
		return currentRound;
	}

	public void start(){
		currentRound = new RoundRoom(this, players);
		currentRoundNumber++;
		startTime = System.currentTimeMillis();
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Map<String, Set<String>> playerWordsSetMap = currentRound.getPlayerWords();
				Map<String, List<String>> playerWordsListMap = new HashMap<>();

				for (Map.Entry<String, Set<String>> entry : playerWordsSetMap.entrySet()) {
					playerWordsListMap.put(entry.getKey(), new ArrayList<>(entry.getValue()));
				}

				System.out.println("ROUND ENDED");
				System.out.println(playerWordsListMap);
				getCurrentRound().endRound();
				if(gameOver()){
					return;
				}
				for (int i = 5; i != 0; i--) {
					System.out.println("Round starting in " + i + " seconds...");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
				start();
			}
		}, roundTime * 1000L);
		printGameScores();

	}

	public String getRemainingTime() {
		long elapsedTime = System.currentTimeMillis() - startTime;
		long remainingTime = Math.max(0, roundTime - (elapsedTime / 1000));
		return String.valueOf(remainingTime);
	}

	public boolean gameOver() {
		for (Map.Entry<String, Integer> entry : playerStandings.entrySet()) {
			if (entry.getValue() >= numOfRoundsNeedToWin) {
				gameWinner = entry.getKey();
				Integer highScore = playerScores.get(gameWinner);
				if (highScore != null) {
					winningScore = highScore;
					System.out.println("High score of the game winner " + gameWinner + ": " + highScore);
					PlayerDAO playerDAOImpl = new PlayerDAO();
					try {
						playerDAOImpl.updateHighestScore(gameWinner, highScore);
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
				}
				return true;
			}
		}
		return false;
	}

	public int getPlayerPoints(String username) {
		return playerScores.getOrDefault(username, 0);
	}


	public int getRoundTime() {
		int roundTime;
		try {
			List<Settings> roundTimeSetting = settingsDAOImpl.getAll();
			Settings setting = roundTimeSetting.get(0);
			roundTime = setting.getRoundTime();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return roundTime;
	}

	public int getNumRounds() {
		int numOfRounds;
		try {
			List<Settings> numOfRoundsSetting = settingsDAOImpl.getAll();
			Settings setting = numOfRoundsSetting.get(0);
			numOfRounds = setting.getRoundsToWin();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return numOfRounds;
	}

	public String createGameId() {
		return UUID.randomUUID().toString();
	}

	public void setPlayers(List<String> players) {
		this.players = players;
		for (String player : players) {
			playerStandings.putIfAbsent(player, 0);
		}
	}

	public void printGameScores() {
		System.out.println("Current Game scores:");
		for (Map.Entry<String, Integer> entry : playerStandings.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}

	public int getNumOfRoundsNeedToWin() {
		return numOfRoundsNeedToWin;
	}

	public long getStartTime() {
		return startTime;
	}

	public int getCurrentRoundNumber() {
		return currentRoundNumber;
	}

	public String getGameWinner() {
		return gameWinner;
	}

	public List<String> getPlayers() {
		return players;
	}

	public ConcurrentHashMap<String, Integer> getPlayerScores() {
		return playerScores;
	}

	public ConcurrentHashMap<String, Integer> getPlayerStandings() {
		return playerStandings;
	}

	public Timer getTimer() {
		return timer;
	}

	public int getWinningScore() {
		return winningScore;
	}
}