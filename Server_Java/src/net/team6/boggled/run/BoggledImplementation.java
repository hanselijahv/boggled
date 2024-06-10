package net.team6.boggled.run;

import BoggledApp.AlreadyLoggedInException;
import BoggledApp.BoggledPOA;
import BoggledApp.Callback;
import BoggledApp.UserNotFoundException;
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
        System.out.println("LETTERS: " + lettersList);
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

    //TODO: FOR GAME ROOM
    @Override
    public boolean submitWord(String gameID, String playerName, String word, BooleanHolder isValid, BooleanHolder canForm, StringHolder response) {
        GameRoom gameRoom = gameRooms.get(gameID);
        RoundRoom roundRoom = gameRoom.getCurrentRound();
        if (roundRoom.canFormWord(word)) {
            System.out.println(word);
            isValid.value = roundRoom.getDictionary().contains(word.toLowerCase());
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
