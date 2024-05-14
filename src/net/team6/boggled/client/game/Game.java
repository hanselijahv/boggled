package net.team6.boggled.client.game;

import net.team6.boggled.client.state.entry.EntryState;
import net.team6.boggled.common.core.Size;
import net.team6.boggled.client.display.Display;
import net.team6.boggled.client.game.settings.GameSettings;
import net.team6.boggled.client.input.Input;
import net.team6.boggled.client.state.State;

import java.awt.FontFormatException;
import java.io.IOException;
import java.sql.SQLException;

@SuppressWarnings("Duplicates")
public class Game implements Runnable{

    private State state;
    private final Display display;
    private final GameSettings gameSettings;
    public static final int UPDATES_PER_SECOND = 60;
    private boolean running;
    private long nextStatTime;
    private int fps, ups;
    public boolean isFrameCapped = false;

    public Game(int width, int height) throws IOException, FontFormatException {
        Input input = new Input();
        gameSettings = new GameSettings(false);
        state = new EntryState(new Size(width, height), input, gameSettings);
        display = new Display(width, height, input, this::resize);
    }

    public GameSettings getSettings() {
        return gameSettings;
    }

    public void enterState(State nextState) {
        state.cleanup();
        state = nextState;
    }

    public void resize(Size size) {
        state.resize(size);
    }

    @Override
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

            double updateRate = 1.0d / UPDATES_PER_SECOND;
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

            if (shouldRender){
                render();
                //printStats();
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

    public void update() throws SQLException {
        state.update(this);
        ups++;
    }

    public void render(){
        display.render(state);
        fps++;
    }

    public synchronized void start(){
        running = true;
        Thread thread = new Thread(this, "Boggled");
        thread.start();
    }

}
