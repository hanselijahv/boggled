package Server_Java.net.team6.boggled.server.input;

import Server_Java.net.team6.boggled.server.gui.component.BoggledImage;
import Server_Java.net.team6.boggled.server.state.ServerState;

public abstract class ServerMouseAction implements ServerMouseConsumer {

    public abstract void update(ServerState state);
    public abstract BoggledImage getSprite();
    public abstract void cleanUp();
}
