package Client_Java.net.team6.boggled.client.game.time;

public class Timer extends Time {

    private Runnable callBack;

    public Timer(double seconds, Runnable callBack) {
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
