package net.team6.boggled.server.gui.clickable;

import net.team6.boggled.server.state.ServerState;

import java.sql.SQLException;

public interface ServerClickAction {
    void execute(ServerState state) throws SQLException;
}
