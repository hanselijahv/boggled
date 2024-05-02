package net.team6.boggled.user.client.gui.clickable;

import net.team6.boggled.user.client.state.State;

import java.sql.SQLException;

public interface ClickAction {
    void execute(State state) throws SQLException;
}
