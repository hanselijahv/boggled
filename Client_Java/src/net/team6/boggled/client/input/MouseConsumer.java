package net.team6.boggled.client.input;

import net.team6.boggled.client.state.State;

import java.sql.SQLException;

public interface MouseConsumer {

    void onClick(State state) throws SQLException;
    void onDrag(State state);
    void onRelease(State state);
}
