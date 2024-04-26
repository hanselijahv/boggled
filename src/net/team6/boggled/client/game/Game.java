package net.team6.boggled.client.game;

import net.team6.boggled.client.core.Size;
import net.team6.boggled.client.display.Display;
import net.team6.boggled.client.game.settings.GameSettings;
import net.team6.boggled.client.input.Input;
import net.team6.boggled.client.state.State;
import net.team6.boggled.client.state.menu.MenuState;

import java.sql.SQLException;

public class Game {

    private State state;

    private final Display display;

    private final GameSettings gameSettings;

    public Game(int width, int height) {
        Input input = new Input();
        gameSettings = new GameSettings(false);
        state = new MenuState(new Size(width, height), input, gameSettings);
        display = new Display(width, height, input, this::resize);
    }

    public void update() throws SQLException {
        state.update(this);
    }

    public void render(){
        display.render(state);
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
}
