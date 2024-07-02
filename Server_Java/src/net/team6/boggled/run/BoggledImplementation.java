package net.team6.boggled.run;

import BoggledApp.*;
import net.team6.boggled.common.model.GameRoom;
import net.team6.boggled.common.model.Player;
import net.team6.boggled.common.model.RoundRoom;
import net.team6.boggled.common.model.WaitingRoom;
import org.omg.CORBA.BooleanHolder;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static net.team6.boggled.common.db.PlayerDAO.playerDAOImpl;

/**
 * This class is the implementation of the Boggled game.
 * It extends the BoggledPOA class and implements all the methods defined in the Boggled interface.
 * It manages the game rooms, waiting rooms, sessions, and logged-in users.
 */
public class BoggledImplementation extends BoggledPOA {

    // concurrent hash map to store waiting rooms, with room ID as the key and the WaitingRoom object as the value
    private ConcurrentHashMap<String, WaitingRoom> waitingRooms = new ConcurrentHashMap<>();

    // concurrent hash map to store game rooms, with room ID as the key and the GameRoom object as the value
    private ConcurrentHashMap<String, GameRoom> gameRooms = new ConcurrentHashMap<>();

    // map to store session data, with session ID as the key and the username as the value
    private final Map<String, String> sessionMap = new HashMap<>();

    // set to store the usernames of all currently logged-in users
    private final Set<String> loggedInUsers = new HashSet<>();

    private ORB orb;    // ORB

    /**
     * Sets the ORB for this Boggled implementation.
     *
     * @param orb the ORB to set
     */
    public void setOrb(ORB orb) {
        this.orb = orb;
    }

    /**
     * Logs in a user with the given username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @throws UserNotFoundException if the user is not found
     * @throws AlreadyLoggedInException if the user is already logged in
     */
    @Override
    public void login(String username, String password) throws UserNotFoundException, AlreadyLoggedInException {
        Player account = new Player(null, username, password);
        boolean isAuthenticated = false;

        if (loggedInUsers.contains(username)) {
            throw new AlreadyLoggedInException("User already logged in: " + username);
        }

        try {
            isAuthenticated = playerDAOImpl.authenticatePlayer(account);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (!isAuthenticated) {
            throw new UserNotFoundException("User not found!");
        }
        String sessionId = generateSessionId();
        sessionMap.put(sessionId, username);
        loggedInUsers.add(username);
    }

    /**
     * Adds a player to the waiting room.
     *
     * @param playerName the name of the player
     */
    @Override
    public void joinWaitingRoom(String playerName) {
        WaitingRoom room = null;
        if (!waitingRooms.isEmpty()) {
            room = waitingRooms.values().iterator().next();
        }
        if (room == null) {
            room = new WaitingRoom(this, playerName);
            waitingRooms.put(playerName, room);
        } else {
            room.joinPlayer(playerName);
            System.out.println(playerName + " joined the waiting room created by " + room.getCreator());
        }
        System.out.println(getWaitingRoomInfo(playerName));
    }

    /**
     * Gets the waiting time for a player.
     *
     * @param objRef the callback object reference
     * @return the remaining waiting time
     */
    @Override
    public String getWaitingTime(Callback objRef) {
        String remainingTime;
        try {
            WaitingRoom room = waitingRooms.values().iterator().next();
            remainingTime = room.getRemainingTime();
        } catch (Exception e) {
            return "Room has ended!";
        }
        return remainingTime;
    }

    /**
     * Gets the waiting room information for a player.
     *
     * @param playerName the name of the player
     * @return the waiting room information
     */
    @Override
    public String getWaitingRoomInfo(String playerName) {
        for (WaitingRoom room : waitingRooms.values()) {
            if (room.getPlayers().contains(playerName)) {
                return "Waiting room created by " + room.getCreator() + " has " + room.getPlayers().size() + " players: " + room.getPlayers();
            }
        }
        return "No waiting room found for player: " + playerName;
    }

    /**
     * Gets the round time for a game.
     *
     * @param objRef the callback object reference
     * @param gameID the game ID
     * @return the remaining round time
     */
    @Override
    public String getRoundTime(Callback objRef, String gameID) {
        String remainingTime;
        try {
            GameRoom gameRoom = gameRooms.get(gameID);
            remainingTime = gameRoom.getRemainingTime();
        } catch (Exception e) {
            return "Round has ended!";
        }
        return remainingTime;
    }

    /**
     * Gets the game ID for a player.
     *
     * @param objRef the callback object reference
     * @param playerName the name of the player
     * @return the game ID
     */
    @Override
    public String getGameID(Callback objRef, String playerName) {
        try {
            WaitingRoom room = waitingRooms.values().iterator().next();
            return room.getGameId();
        } catch (Exception e) {
            return "GAMEID ISN'T AVAILABLE";
        }
    }

    /**
     * Gets the letters for a game.
     *
     * @param gameID the game ID
     * @return the letters
     */
    @Override
    public String getLetters(String gameID) {
        GameRoom gameRoom = gameRooms.get(gameID);
        List<Character> lettersList = gameRoom.getCurrentRound().getRandomLetters();
        StringBuilder stringBuilder = new StringBuilder(lettersList.size());
        for (Character character : lettersList) {
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }

    /**
     * Checks if the game is ready to start.
     *
     * @return true if the game is ready to start, false otherwise
     */
    @Override
    public boolean isGameReadyToStart() {
        WaitingRoom room;
        try {
            room = waitingRooms.values().iterator().next();
        } catch (Exception e) {
            return false;
        }
        return room.isReadyToStart();
    }

    /**
     * Submits a word for a player in a game.
     *
     * @param gameID the game ID
     * @param playerName the name of the player
     * @param word the word to submit
     * @param isValid a holder for the validity of the word
     * @param canForm a holder for the ability to form the word
     * @param response a holder for the response message
     * @return true if the word is submitted successfully, false otherwise
     */
    @Override
    public boolean submitWord(String gameID, String playerName, String word, BooleanHolder isValid, BooleanHolder canForm, StringHolder response) {
        GameRoom gameRoom = gameRooms.get(gameID);
        RoundRoom roundRoom = gameRoom.getCurrentRound();
        if (roundRoom.getDictionary().contains(word.toLowerCase())) {
            isValid.value = true;
            canForm.value = true;
            response.value = "Word is valid!";
            roundRoom.submitWord(playerName,word);
            return true;
        } else {
            isValid.value = false;
            response.value = "Word is invalid!";
            return false;
        }
    }

    /**
     * Logs out a user with the given username.
     *
     * @param username the username of the user
     * @throws NotLoggedInException if the user is not logged in
     */
    @Override
    public void logout(String username) throws NotLoggedInException {
        if (loggedInUsers.contains(username)) {
            loggedInUsers.remove(username);
        } else {
            throw new NotLoggedInException("User not logged in: " + username);
        }
    }

    /**
     * Gets the game score for a game.
     *
     * @param gameID the game ID
     * @return the game score
     */
    @Override
    public String gameScore(String gameID){
        GameRoom gameRoom = gameRooms.get(gameID);
        StringBuilder scores = new StringBuilder();
        for (Map.Entry<String, Integer> entry : gameRoom.getPlayerStandings().entrySet()) {
            scores.append(entry.getKey()).append(" - ").append(entry.getValue()).append("/").append("\n");
        }
        return scores.toString();
    }

    /**
     * Gets the round points for a player in a game.
     *
     * @param gameID the game ID
     * @param username the username of the player
     * @return the round points
     */
    @Override
    public int roundPoints(String gameID, String username){
        GameRoom gameRoom = gameRooms.get(gameID);
        RoundRoom roundRoom = gameRoom.getCurrentRound();
        return roundRoom.getPlayerPoint(username);
    }

    /**
     * Gets the total points for a player in a game.
     *
     * @param gameId the game ID
     * @param username the username of the player
     * @return the total points
     */
    @Override
    public int playerPoints(String gameId, String username){
        GameRoom gameRoom = gameRooms.get(gameId);
        return gameRoom.getPlayerPoints(username);
    }

    /**
     * Gets the game winner for a game.
     *
     * @param gameId the game ID
     * @return the game winner
     */
    @Override
    public String gameWinner(String gameId){
        GameRoom gameRoom = gameRooms.get(gameId);
        return gameRoom.getGameWinner();
    }

    /**
     * Gets the winner's score for a game.
     *
     * @param gameID the game ID
     * @return the winner's score
     */
    @Override
    public int getWinnerScore(String gameID) {
        GameRoom gameRoom = gameRooms.get(gameID);
        return gameRoom.getWinningScore();
    }

    /**
     * Checks if the game is finished.
     *
     * @param gameId the game ID
     * @return true if the game is finished, false otherwise
     */
    @Override
    public boolean isGameFinished(String gameId){
        GameRoom gameRoom = gameRooms.get(gameId);
        return gameRoom.gameOver();
    }

    /**
     * Gets the round winner for a game.
     *
     * @param gameId the game ID
     * @throws NoWinnerException if there is no winner for the round
     * @return the round winner
     */
    @Override
    public String roundWinner(String gameId) throws NoWinnerException {
        GameRoom gameRoom = gameRooms.get(gameId);
        gameRoom.getCurrentRound().getRoundWinner();
        if(gameRoom.getCurrentRound().getRoundWinner() == null){
            throw new NoWinnerException("No winner for this round");
        } else {
            return gameRoom.getCurrentRound().getRoundWinner();
        }
    }

    /**
     * Gets the current round number for a game.
     *
     * @param gameId the game ID
     * @return the current round number
     */
    @Override
    public int currentRound(String gameId){
        GameRoom gameRoom = gameRooms.get(gameId);
        return gameRoom.getCurrentRoundNumber();
    }

    /**
     * Gets the total number of rounds needed to win for a game.
     *
     * @param gameID the game ID
     * @return the total number of rounds needed to win
     */
    @Override
    public int totalRounds(String gameID) {
        GameRoom gameRoom = gameRooms.get(gameID);
        return gameRoom.getNumOfRoundsNeedToWin();
    }

    /**
     * Gets the leaderboard of the game.
     *
     * @return the leaderboard
     */
    @Override
    public Leaderboards getLeaderboard() {
        List<Player> players;
        try {
            players = playerDAOImpl.getTop5PlayersWithHighestScores();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String[] leaderboardArray = new String[players.size()];
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            leaderboardArray[i] = player.getUsername() + "," + player.getHighestScore();
        }
        Leaderboards leaderboards = new Leaderboards();
        leaderboards.leaderboard = leaderboardArray;
        return leaderboards;
    }

    /**
     * Generates a session ID.
     *
     * @return the generated session ID
     */
    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Gets the waiting rooms.
     *
     * @return the waiting rooms
     */
    public ConcurrentHashMap<String, WaitingRoom> getWaitingRooms() {
        return waitingRooms;
    }

    /**
     * Gets the game rooms.
     *
     * @return the game rooms
     */
    public ConcurrentHashMap<String, GameRoom> getGameRooms() {
        return gameRooms;
    }

}
