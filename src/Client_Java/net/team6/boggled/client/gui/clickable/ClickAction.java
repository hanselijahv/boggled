package Client_Java.net.team6.boggled.client.gui.clickable;

import Client_Java.net.team6.boggled.client.state.State;

import java.sql.SQLException;

public interface ClickAction {
    void execute(State state) throws SQLException;
}
