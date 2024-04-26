package com.wordgame.client.gui;

import com.wordgame.client.core.Size;
import com.wordgame.client.state.State;

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
