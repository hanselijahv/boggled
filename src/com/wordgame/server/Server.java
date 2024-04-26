package com.wordgame.server;

import com.wordgame.client.core.Size;
import com.wordgame.client.input.Input;
import com.wordgame.common.model.Settings;
import com.wordgame.server.display.DevDisplay;
import com.wordgame.server.state.menu.ServerMenuState;
import com.wordgame.server.state.settings.BoggledSettings;
import com.wordgame.client.state.State;

import java.sql.SQLException;

public class Server {
    private State state;
    private final DevDisplay display;
    private final BoggledSettings boggledSettings;

    public Server(int width, int height) throws SQLException {
        Input input = new Input();
        boggledSettings = new BoggledSettings(false);
        state = new ServerMenuState(new Size(width, height), input, boggledSettings);
        display = new DevDisplay(width, height, input, this::resize);
    }

    public void update() throws SQLException {
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
