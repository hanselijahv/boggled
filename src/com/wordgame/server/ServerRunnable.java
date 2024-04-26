package com.wordgame.server;

@SuppressWarnings("Duplicates")
public class ServerRunnable implements Runnable {
    public static final int UPDATES_PER_SECOND = 60;
    private Server server;
    private boolean running;
    private final double updateRate = 1.0d / UPDATES_PER_SECOND;
    private long nextStatTime;
    private int fps, ups;
    public boolean isFrameCapped = false;

    public ServerRunnable(Server server) {
        this.server = server;
    }

    public void run() {
        running = true;
        boolean shouldRender;
        double accumulator = 0;
        long currentTime, lastUpdate = System.currentTimeMillis();
        nextStatTime = System.currentTimeMillis() + 1000;

        while (running) {
            shouldRender = !isFrameCapped;
            currentTime = System.currentTimeMillis();
            double lastRenderTimeInSeconds = (currentTime - lastUpdate) / 1000d;
            accumulator += lastRenderTimeInSeconds * server.getSettings().getGameSpeedMultiplier();
            lastUpdate = currentTime;

            if (accumulator >= updateRate) {
                while (accumulator >= updateRate) {
                    update();
                    accumulator -= updateRate;
                    shouldRender = true;

                }
            }

            if (shouldRender) {
                render();
                printStats();
            }

        }

    }

    private void printStats() {
        if (System.currentTimeMillis() > nextStatTime) {
            System.out.printf("FPS: %d, UPS: %d%n", fps, ups);
            fps = 0;
            ups = 0;
            nextStatTime = System.currentTimeMillis() + 1000;
        }
    }

    private void update() {
        server.update();
        ups++;
    }

    private void render() {
        server.render();
        fps++;
    }

}
