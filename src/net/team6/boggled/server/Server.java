package net.team6.boggled.server;

import net.team6.boggled.common.core.Size;
import net.team6.boggled.common.input.Input;
import net.team6.boggled.server.display.DevDisplay;
import net.team6.boggled.server.state.menu.ServerMenuState;
import net.team6.boggled.server.settings.BoggledSettings;
import net.team6.boggled.client.state.State;

import java.sql.SQLException;

public class Server implements Runnable {
    private State state;
    private final DevDisplay display;
    private final BoggledSettings boggledSettings;
    public static final int UPDATES_PER_SECOND = 60;
    private boolean running;
    private final double updateRate = 1.0d / UPDATES_PER_SECOND;
    private long nextStatTime;
    private int fps, ups;
    public boolean isFrameCapped = false;
    private Thread thread;

    public Server(int width, int height) throws SQLException {
        Input input = new Input();
        boggledSettings = new BoggledSettings(false);
        state = new ServerMenuState(new Size(width, height), input, boggledSettings);
        display = new DevDisplay(width, height, input, this::resize);
    }

    public void update() throws SQLException {
        state.serverUpdate(this);
        ups++;
    }

    public void render(){
        display.render(state);
        fps++;
    }

    public BoggledSettings getSettings() {
        return boggledSettings;
    }

    public void enterState(State nextState) {
        state.cleanup();
        state = nextState;
    }

    public void resize(Size size) {
        state.resize(size);
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
            accumulator += lastRenderTimeInSeconds * getSettings().getGameSpeedMultiplier();
            lastUpdate = currentTime;

            if (accumulator >= updateRate) {
                while (accumulator >= updateRate) {
                    try {
                        update();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
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

    public synchronized void start(){
        running = true;
        thread = new Thread(this, "Boggled");
        thread.start();
    }

    private void printStats() {
        if (System.currentTimeMillis() > nextStatTime) {
            System.out.printf("FPS: %d, UPS: %d%n", fps, ups);
            fps = 0;
            ups = 0;
            nextStatTime = System.currentTimeMillis() + 1000;
        }
    }

}
