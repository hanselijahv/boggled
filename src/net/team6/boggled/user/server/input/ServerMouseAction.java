package net.team6.boggled.user.server.input;

import net.team6.boggled.user.client.gui.component.BoggledImage;
import net.team6.boggled.user.server.state.ServerState;

public abstract class ServerMouseAction implements ServerMouseConsumer {

    public abstract void update(ServerState state);
    public abstract BoggledImage getSprite();
    public abstract void cleanUp();
}
