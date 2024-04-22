package temp.input;

import temp.state.State;

public interface MouseAction {

    void onClick(State state);
    void onDrag(State state);
    void onRelease(State state);
}
