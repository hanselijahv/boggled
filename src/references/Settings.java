package references;

import java.sql.Time;
import java.sql.Timestamp;

public class Settings {
    private Timestamp waitingTime;
    private Timestamp roundTime;
    private int numberOfRounds;

    public Settings(Timestamp waitingTime, Timestamp roundTime, int numberOfRounds) {
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

    public Timestamp getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Timestamp waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Timestamp getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(Timestamp roundTime) {
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