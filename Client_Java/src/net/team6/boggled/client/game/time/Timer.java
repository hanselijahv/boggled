package net.team6.boggled.client.game.time;

public class Timer extends Time {

    private Runnable callBack;
    private boolean isRunning;

    public Timer(double seconds, Runnable callBack) {
        currentUpdates = getUpdatesFromSeconds(seconds);
        this.callBack = callBack;
        this.isRunning = true;
    }

    @Override
    public void update() {
        if(isRunning && currentUpdates > 0) {
            currentUpdates--;

            if(currentUpdates == 0) {
                callBack.run();
                isRunning = false;
            }
        }
    }

    public void start() {
        isRunning = true;
    }

    public void stop() {
        isRunning = false;
    }
}