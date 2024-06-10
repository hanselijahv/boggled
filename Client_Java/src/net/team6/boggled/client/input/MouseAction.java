package net.team6.boggled.client.input;

import net.team6.boggled.client.gui.component.BoggledImage;
import net.team6.boggled.client.state.State;

public abstract class MouseAction implements MouseConsumer {

    public abstract void update(State state);
    public abstract BoggledImage getSprite();
    public abstract void cleanUp();
}
