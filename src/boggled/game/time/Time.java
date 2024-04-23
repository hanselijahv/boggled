package boggled.game.time;

import boggled.game.GameLoop;

public class Time {

    protected int currentUpdates;

    public Time() {
        this.currentUpdates = 0;
    }

    public int getUpdatesFromSeconds(double seconds) {
        return (int) Math.round(seconds * GameLoop.UPDATES_PER_SECOND);
    }

    public void update() {
        currentUpdates++;
    }

    public String getFormattedTime() {
        StringBuilder stringBuilder = new StringBuilder();
        int minutes = currentUpdates / GameLoop.UPDATES_PER_SECOND / 60;
        int seconds = currentUpdates / GameLoop.UPDATES_PER_SECOND % 60;

        if(minutes < 10) {
            stringBuilder.append(0);
        }
        stringBuilder.append(minutes);
        stringBuilder.append(":");

        if(seconds < 10) {
            stringBuilder.append(0);
        }
        stringBuilder.append(seconds);
        return stringBuilder.toString();
    }

    public boolean secondsDividableBy(double seconds) {
        return currentUpdates % getUpdatesFromSeconds(seconds) == 0;
    }

    public int asSeconds() {
        return currentUpdates / GameLoop.UPDATES_PER_SECOND;
    }
}
