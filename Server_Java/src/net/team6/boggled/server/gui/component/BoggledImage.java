package net.team6.boggled.server.gui.component;

import net.team6.boggled.common.core.Size;
import net.team6.boggled.server.state.ServerState;

import java.awt.*;


public class BoggledImage extends BoggledComponent {
    private Image image;

    public BoggledImage(Image image) {
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
