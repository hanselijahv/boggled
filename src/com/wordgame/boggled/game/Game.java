package com.wordgame.boggled.game;

import com.wordgame.boggled.core.Size;
import com.wordgame.boggled.display.Display;
import com.wordgame.boggled.game.settings.GameSettings;
import com.wordgame.boggled.input.Input;
import com.wordgame.boggled.state.State;
import com.wordgame.boggled.state.menu.MenuState;

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

    public void update(){
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
