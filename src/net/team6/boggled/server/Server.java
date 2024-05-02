package net.team6.boggled.server;

import net.team6.boggled.common.core.Size;
import net.team6.boggled.common.input.Input;
import net.team6.boggled.server.display.DevDisplay;
import net.team6.boggled.server.state.menu.ServerMenuState;
import net.team6.boggled.server.settings.BoggledSettings;
import net.team6.boggled.client.state.State;

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
