package com.wordgame.server.display;

import com.wordgame.client.state.State;

import javax.swing.*;
import java.awt.*;

public class DevRenderer {
    private DevDisplay display;

    public DevRenderer(DevDisplay display) {
        this.display = display;
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

        if (state.getBoggledSettings().isFullScreenMode().get()){
            display.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            display.setExtendedState(JFrame.NORMAL);
        }
    }

    public DevDisplay getDisplay() {
        return display;
    }

    private void centerDisplayOnScreen(Dimension screenSize) {
        if (display != null) {
            Dimension windowSize = display.getSize();
            int x = (screenSize.width - windowSize.width) / 2;
            int y = (screenSize.height - windowSize.height) / 2;
            display.setLocation(x, y);
        }
    }

}
