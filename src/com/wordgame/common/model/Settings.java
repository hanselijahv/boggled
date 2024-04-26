package com.wordgame.common.model;

public class Settings {
    private int waitingTime;
    private int roundTime;
    private int roundsToWin;

    public Settings(int waitingTime, int roundTime, int roundsToWin) {
        this.waitingTime = waitingTime;
        this.roundTime = roundTime;
        this.roundsToWin = roundsToWin;
    }

    public int getRoundsToWin() {
        return roundsToWin;
    }

    public void setRoundsToWin(int roundsToWin) {
        this.roundsToWin = roundsToWin;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(int roundTime) {
        this.roundTime = roundTime;
    }

    @Override
    public String toString() {
        return "Settings: \n" +
               "\tWaiting Time: " + waitingTime + " seconds\n" +
               "\tRound Time: " + roundTime + " seconds";
    }
}