package net.team6.boggled.user.server.gui.clickable;

import net.team6.boggled.common.core.Position;
import net.team6.boggled.user.server.input.ServerMouseConsumer;
import net.team6.boggled.user.server.gui.component.ServerComponent;
import net.team6.boggled.user.server.state.ServerState;

import java.awt.*;
import java.sql.SQLException;

public abstract class ServerClickable extends ServerComponent implements ServerMouseConsumer {

    protected boolean hasFocus;
    protected boolean isPressed;

    @Override
    public void update(ServerState state) throws SQLException {
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


    protected abstract void onFocus(ServerState state);
    public abstract void onDrag(ServerState state);

    private Rectangle getBounds() {
        return new Rectangle(
                absolutePosition.intX(),
                absolutePosition.intY(),
                size.getWidth(),
                size.getHeight()
        );
    }
}