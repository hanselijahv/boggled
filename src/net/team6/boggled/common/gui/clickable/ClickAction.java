package net.team6.boggled.common.gui.clickable;

import net.team6.boggled.client.state.State;

import java.sql.SQLException;

public interface ClickAction {
    void execute(State state) throws SQLException;
}
