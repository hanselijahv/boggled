package com.wordgame.boggled.ui;

import com.wordgame.boggled.core.Size;
import com.wordgame.boggled.state.State;

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
