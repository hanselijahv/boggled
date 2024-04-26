package com.wordgame.client.gui.clickable;

import com.wordgame.client.core.Position;
import com.wordgame.client.input.MouseConsumer;
import com.wordgame.client.state.State;
import com.wordgame.client.gui.UIComponent;

import java.awt.*;
import java.sql.SQLException;

public abstract class UIClickable extends UIComponent implements MouseConsumer {

    protected boolean hasFocus;
    protected boolean isPressed;

    @Override
    public void update(State state) throws SQLException {
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