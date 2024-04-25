package com.wordgame.references;

import java.sql.Timestamp;

public class Settings {
    private int waitingTime;
    private int roundTime;
    private int numberOfRounds;

    public Settings(int waitingTime, int roundTime, int numberOfRounds) {
        this.waitingTime = waitingTime;
        this.roundTime = roundTime;
        this.numberOfRounds = numberOfRounds;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public void setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
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
        return "Settings{" +
                "waitingTime=" + waitingTime +
                ", roundTime=" + roundTime +
                ", numberOfRounds=" + numberOfRounds +
                '}';
    }
}