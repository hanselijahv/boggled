package net.team6.boggled.user.server.time;

public class ServerTimer extends ServerTime {
    private final Runnable callBack;

    public ServerTimer(double seconds, Runnable callBack) {
        currentUpdates = getUpdatesFromSeconds(seconds);
        this.callBack = callBack;
    }

    @Override
    public void update() {
        if(currentUpdates > 0) {
            currentUpdates--;

            if(currentUpdates == 0) {
                callBack.run();
            }
        }
    }
}
