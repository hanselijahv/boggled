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

public class BoggledImplementation extends BoggledPOA {
    private ConcurrentHashMap<String, WaitingRoom> waitingRooms = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, GameRoom> gameRooms = new ConcurrentHashMap<>();
    private final Map<String, String> sessionMap = new HashMap<>();     // sessionId -> username
    private final Set<String> loggedInUsers = new HashSet<>();
    private ORB orb;

    public void setOrb(ORB orb) {
        this.orb = orb;
    }

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

    @Override
    public String getWaitingRoomInfo(String playerName) {
        for (WaitingRoom room : waitingRooms.values()) {
            if (room.getPlayers().contains(playerName)) {
                return "Waiting room created by " + room.getCreator() + " has " + room.getPlayers().size() + " players: " + room.getPlayers();
            }
        }
        return "No waiting room found for player: " + playerName;
    }

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

    @Override
    public String getGameID(Callback objRef, String playerName) {
        try {
            WaitingRoom room = waitingRooms.values().iterator().next();
            return room.getGameId();
        } catch (Exception e) {
            return "GAMEID ISN'T AVAILABLE";
        }
    }

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

    @Override
    public void logout(String username) throws NotLoggedInException {
        if (loggedInUsers.contains(username)) {
            loggedInUsers.remove(username);
        } else {
            throw new NotLoggedInException("User not logged in: " + username);
        }
    }

    @Override
    public String gameScore(String gameID){
        GameRoom gameRoom = gameRooms.get(gameID);
        StringBuilder scores = new StringBuilder("Current Game scores:\n");
        for (Map.Entry<String, Integer> entry : gameRoom.getPlayerStandings().entrySet()) {
            scores.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return scores.toString();
    }

    @Override
    public int roundPoints(String gameID, String username){
        GameRoom gameRoom = gameRooms.get(gameID);
        RoundRoom roundRoom = gameRoom.getCurrentRound();
        return roundRoom.getPlayerPoint(username);
    }

    @Override
    public int playerPoints(String gameId, String username){
        GameRoom gameRoom = gameRooms.get(gameId);
        return gameRoom.getPlayerPoints(username);
    }

    @Override
    public String gameWinner(String gameId){
        GameRoom gameRoom = gameRooms.get(gameId);
        return gameRoom.getGameWinner();
    }

    @Override
    public boolean isGameFinished(String gameId){
        GameRoom gameRoom = gameRooms.get(gameId);
        return gameRoom.gameOver();
    }

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

    @Override
    public int currentRound(String gameId){
        GameRoom gameRoom = gameRooms.get(gameId);
        return gameRoom.getCurrentRoundNumber();
    }

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

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    public ConcurrentHashMap<String, WaitingRoom> getWaitingRooms() {
        return waitingRooms;
    }

    public ConcurrentHashMap<String, GameRoom> getGameRooms() {
        return gameRooms;
    }

}
