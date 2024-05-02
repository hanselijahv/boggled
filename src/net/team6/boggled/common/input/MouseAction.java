package net.team6.boggled.common.input;

import net.team6.boggled.client.state.State;
import net.team6.boggled.common.gui.component.UIImage;

public abstract class MouseAction implements MouseConsumer{

    public abstract void update(State state);
    public abstract UIImage getSprite();
    public abstract void cleanUp();
}
