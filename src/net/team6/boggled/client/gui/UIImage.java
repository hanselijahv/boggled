package net.team6.boggled.client.gui;

import net.team6.boggled.client.core.Size;
import net.team6.boggled.client.state.State;

import java.awt.Image;


public class UIImage extends UIComponent{
    private Image image;

    public UIImage(Image image) {
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
