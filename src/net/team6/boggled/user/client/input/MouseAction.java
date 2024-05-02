package net.team6.boggled.user.client.input;

import net.team6.boggled.user.client.state.State;
import net.team6.boggled.user.client.gui.component.BoggledImage;

public abstract class MouseAction implements MouseConsumer{

    public abstract void update(State state);
    public abstract BoggledImage getSprite();
    public abstract void cleanUp();
}
