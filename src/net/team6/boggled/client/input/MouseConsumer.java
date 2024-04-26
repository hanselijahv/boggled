package net.team6.boggled.client.input;

import net.team6.boggled.client.state.State;

public interface MouseConsumer {

    void onClick(State state);
    void onDrag(State state);
    void onRelease(State state);
}
