package net.team6.boggled.user.server.input;

import net.team6.boggled.user.server.state.ServerState;

import java.sql.SQLException;

public interface ServerMouseConsumer {

    void onClick(ServerState state) throws SQLException;
    void onDrag(ServerState state);
    void onRelease(ServerState state);
}
