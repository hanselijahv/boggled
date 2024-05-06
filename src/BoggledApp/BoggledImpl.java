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
    public void logout(Account player) throws NotLoggedInException {

    }

    @Override
    public void reconnect(Account player) {

    }

    @Override
    public void playGame() throws InsufficientPlayersException {
        System.out.println("Play game!");
    }

    @Override
    public Settings getSettings() {
        return null;
    }

    @Override
    public GameResult getGameWinner() {
        return null;
    }

    @Override
    public GameResult getLeaderboards() {
        return null;
    }

    @Override
    public void sendWord(String word) throws InvalidWordException {
    }

}
