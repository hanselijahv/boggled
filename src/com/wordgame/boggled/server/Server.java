package com.wordgame.boggled.server;

import com.wordgame.boggled.core.Size;
import com.wordgame.boggled.input.Input;
import com.wordgame.boggled.server.display.DevDisplay;
import com.wordgame.boggled.server.state.menu.ServerMenuState;
import com.wordgame.boggled.server.state.settings.BoggledSettings;
import com.wordgame.boggled.state.State;

public class Server {
    private State state;
    private final DevDisplay display;
    private final BoggledSettings boggledSettings;

    public Server(int width, int height) {
        Input input = new Input();
        boggledSettings = new BoggledSettings(false);
        state = new ServerMenuState(new Size(width, height), input, boggledSettings);
        display = new DevDisplay(width, height, input, this::resize);
    }

    public void update(){
        state.serverUpdate(this);
    }

    public void render(){
        display.render(state);
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

}
