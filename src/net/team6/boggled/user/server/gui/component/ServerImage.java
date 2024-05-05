package net.team6.boggled.user.server.gui.component;

import net.team6.boggled.common.core.Size;
import net.team6.boggled.user.server.state.ServerState;

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
