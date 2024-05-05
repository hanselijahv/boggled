package net.team6.boggled.user.server.gui.clickable;

import net.team6.boggled.user.server.state.ServerState;

import java.sql.SQLException;

public interface ServerClickAction {
    void execute(ServerState state) throws SQLException;
}
