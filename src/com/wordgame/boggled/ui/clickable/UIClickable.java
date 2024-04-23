package com.wordgame.boggled.ui.clickable;

import com.wordgame.boggled.core.Position;
import com.wordgame.boggled.input.MouseConsumer;
import com.wordgame.boggled.state.State;
import com.wordgame.boggled.ui.UIComponent;

import java.awt.*;

public abstract class UIClickable extends UIComponent implements MouseConsumer {

    protected boolean hasFocus;
    protected boolean isPressed;

    @Override
    public void update(State state) {
        Position mousePosition = state.getInput().getMousePosition();
        boolean previousFocus = hasFocus;

        hasFocus = getBounds().contains(mousePosition.intX(), mousePosition.intY());
        isPressed = hasFocus && state.getInput().isMousePressed();

        if(hasFocus && state.getInput().isMouseClicked()) {
            onClick(state);
        }

        if(hasFocus && state.getInput().isMousePressed()) {
            onDrag(state);
        }

        if(!previousFocus && hasFocus) {
            onFocus(state);
        }
    }


    protected abstract void onFocus(State state);
    public abstract void onDrag(State state);

    private Rectangle getBounds() {
        return new Rectangle(
                absolutePosition.intX(),
                absolutePosition.intY(),
                size.getWidth(),
                size.getHeight()
        );
    }
}