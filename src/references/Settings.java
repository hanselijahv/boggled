package references;

import java.sql.Time;
import java.sql.Timestamp;

public class Settings {
    private Timestamp waitingTime;
    private Timestamp roundTime;

    public Settings(Timestamp waitingTime, Timestamp roundTime) {
        this.waitingTime = waitingTime;
        this.roundTime = roundTime;
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
                '}';
    }
}