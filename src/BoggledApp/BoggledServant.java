package BoggledApp;

import org.omg.CORBA.ORB;

import static net.team6.boggled.user.server.ServerTest.accountDAOImpl;

public class BoggledServant extends BoggledPOA{
    private ORB orb;

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    @Override
    public void login(String username, String password) throws UserNotFoundException {
        boolean isAuthenticated = accountDAOImpl.authenticatePlayer(username, password);
        if (!isAuthenticated) {
            throw new UserNotFoundException("User '" + username + "' not found or invalid credentials.");
        }
        System.out.println("User '" + username + " logged in successfully.");
    }

    @Override
    public void logout(String username) throws NotLoggedInException {

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
}
