package net.team6.boggled.client.gui.component;

import Client_Java.net.team6.boggled.client.state.State;
import Client_Java.net.team6.boggled.common.core.Size;

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
    public void update(State state) {

    }
}
