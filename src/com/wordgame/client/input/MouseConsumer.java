package com.wordgame.client.input;

import com.wordgame.client.state.State;

public interface MouseConsumer {

    void onClick(State state);
    void onDrag(State state);
    void onRelease(State state);
}
