package com.wordgame.client.input;

import com.wordgame.client.state.State;
import com.wordgame.client.gui.UIImage;

public abstract class MouseAction implements MouseConsumer{

    public abstract void update(State state);
    public abstract UIImage getSprite();
    public abstract void cleanUp();
}
