package BoggledApp;

import org.omg.CORBA.ORB;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static net.team6.boggled.user.server.ServerTest.accountDAOImpl;

public class BoggledServant extends BoggledPOA{
    private ORB orb;
    private final Map<String, String> sessionMap = new HashMap<>();
    private static BoggledServant instance = null;

    public static BoggledServant getInstance() {
        if (instance == null) {
            instance = new BoggledServant();
        }
        return instance;
    }

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    @Override
    public synchronized void login(String username, String password) throws UserNotFoundException {
        boolean isAuthenticated = accountDAOImpl.authenticatePlayer(username, password);
        if (!isAuthenticated) {
            throw new UserNotFoundException("User '" + username + "' not found or invalid credentials.");
        }
        String sessionId = generateSessionId();
        sessionMap.put(sessionId, username);
        System.out.println("User '" + username + "' logged in successfully. Session ID: " + sessionId);
    }

    public synchronized String getSessionId(String username) {
        for (Map.Entry<String, String> entry : sessionMap.entrySet()) {
            if (entry.getValue().equals(username)) {
                return entry.getKey();
            }
        }
        return null;
    }


    @Override
    public synchronized void logout(String sessionId) throws NotLoggedInException {
        String username = sessionMap.get(sessionId);
        if (username == null) {
            throw new NotLoggedInException("Session ID '" + sessionId + "' is not valid.");
        }
        sessionMap.remove(sessionId);
        System.out.println("User '" + username + "' logged out successfully.");
    }

    @Override
    public String playGame(String username) throws InsufficientPlayersException {
        return null;
    }

    @Override
    public void sendWord(String gameID, String word) throws InvalidWordException {

    }

    @Override
    public char[] getLetters(String gameID) {
        return new char[0];
    }

    @Override
    public boolean isGameOver(String gameID) {
        return false;
    }

    @Override
    public boolean isRoundOver(String gameID) {
        return false;
    }

    @Override
    public RoundWinner getRoundWinner(String gameID) {
        return null;
    }

    @Override
    public GameWinner getGameWinner(String gameID) {
        return null;
    }

    @Override
    public int getRoundTime(String gameID) {
        return 0;
    }

    @Override
    public int getWaitingTime(String gameID) {
        return 0;
    }

    @Override
    public int getNumRounds(String gameID) {
        return 0;
    }

    @Override
    public GameWinner[] getLeaderboards() {
        return new GameWinner[0];
    }

    @Override
    public GamePlayer[] getGamePlayers(String gameID) {
        return new GamePlayer[0];
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString(); // Generate unique session ID
    }
}
