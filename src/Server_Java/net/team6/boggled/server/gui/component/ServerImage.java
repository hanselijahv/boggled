package Server_Java.net.team6.boggled.server.gui.component;

import Server_Java.net.team6.boggled.common.core.Size;
import Server_Java.net.team6.boggled.server.state.ServerState;

import java.awt.*;


public class ServerImage extends ServerComponent {
    private Image image;

    public ServerImage(Image image) {
        this.image = image;
        size = new Size(image.getWidth(null), image.getHeight(null));
    }

    @Override
    public Image getSprite() {
        return image;
    }

    @Override
    public void update(ServerState state) {

    }
}
