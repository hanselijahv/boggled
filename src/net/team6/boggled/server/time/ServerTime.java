package net.team6.boggled.server.time;

import net.team6.boggled.server.dev.Server;

@SuppressWarnings("Duplicates")
public class ServerTime {
    protected int currentUpdates;

    public ServerTime() {
        this.currentUpdates = 0;
    }

    public int getUpdatesFromSeconds(double seconds) {
        return (int) Math.round(seconds * Server.UPDATES_PER_SECOND);
    }

    public void update() {
        currentUpdates++;
    }

    public String getFormattedTime() {
        StringBuilder stringBuilder = new StringBuilder();
        int minutes = currentUpdates / Server.UPDATES_PER_SECOND / 60;
        int seconds = currentUpdates / Server.UPDATES_PER_SECOND % 60;

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
        return currentUpdates / Server.UPDATES_PER_SECOND;
    }
}
