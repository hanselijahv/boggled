package references;

import java.sql.Time;

public class Settings {
    private Time waitingTime;
    private Time roundTime;

    public Settings(Time waitingTime, Time roundTime) {
        this.waitingTime = waitingTime;
        this.roundTime = roundTime;
    }

    public Time getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Time waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Time getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(Time roundTime) {
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