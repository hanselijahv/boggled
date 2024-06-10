package net.team6.boggled.client.display;


import net.team6.boggled.client.state.State;

import javax.swing.*;
import java.awt.*;

public class
Renderer {

    private Display display;

    public Renderer(Display display) {
        this.display = display; // Initialize the display object in the constructor
    }


    public void render(State state, Graphics graphics){
        renderUI(state, graphics);
    }

    private void renderUI(State state, Graphics graphics) {

        state.getMouseHandler().getPrimaryButtonUI().ifPresent(uiImage -> graphics.drawImage(
                uiImage.getSprite(),
                uiImage.getAbsolutePosition().intX(),
                uiImage.getAbsolutePosition().intY(),
                null
        ));

        state.getUiCanvas().getComponents().forEach(uiContainer -> graphics.drawImage(
                uiContainer.getSprite(),
                uiContainer.getRelativePosition().intX(),
                uiContainer.getRelativePosition().intY(),
                null
        ));

        if (state.getGameSettings().isFullScreenMode().get()){
            display.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            display.setExtendedState(JFrame.NORMAL);
        }
    }



}
