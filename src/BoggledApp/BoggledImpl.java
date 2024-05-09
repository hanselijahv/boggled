package BoggledApp;

import org.omg.CORBA.ORB;

public class BoggledImpl extends BoggledPOA{
    private ORB orb;

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    @Override
    public void login(Account player) throws UserNotFoundException {

    }

    @Override
    public void signup(String username, String password) throws UserAlreadyExistsException {

    }

    @Override
    public void logout(Account player) throws NotLoggedInException {

    }

    @Override
    public String playGame(String username) throws InsufficientPlayersException {
        return null;
    }

    @Override
    public void sendWord(String gameID, String word) throws InvalidWordException {

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
    public Settings getSettings() {
        return null;
    }

    @Override
    public RoundWinner getRoundWinner() {
        return null;
    }

    @Override
    public GameWinner getGameWinner() {
        return null;
    }

    @Override
    public GameWinner[] getLeaderboards() {
        return new GameWinner[0];
    }

    @Override
    public GamePlayer[] getGamePlayers() {
        return new GamePlayer[0];
    }
}
