package Server_Java.net.team6.boggled.server.display;

import Server_Java.net.team6.boggled.server.state.ServerState;

import javax.swing.*;
import java.awt.*;

public class ServerRenderer {
    private ServerDisplay display;

    public ServerRenderer(ServerDisplay display) {
        this.display = display;
    }

    public void render(ServerState state, Graphics graphics){
        renderUI(state, graphics);
    }

    private void renderUI(ServerState state, Graphics graphics) {
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

    public ServerDisplay getDisplay() {
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
