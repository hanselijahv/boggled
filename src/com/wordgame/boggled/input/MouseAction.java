package com.wordgame.boggled.input;

import com.wordgame.boggled.state.State;
import com.wordgame.boggled.ui.UIImage;

public abstract class MouseAction implements MouseConsumer{

    public abstract void update(State state);
    public abstract UIImage getSprite();
    public abstract void cleanUp();
}
