package temp.display;


import temp.state.State;

import java.awt.*;

public class Renderer {


    public void render(State state, Graphics graphics){
        renderUI(state, graphics);
    }

    private void renderUI(State state, Graphics graphics) {
        state.getUiContainers().forEach(uiContainer -> graphics.drawImage(
                uiContainer.getSprite(),
                uiContainer.getRelativePosition().intX(),
                uiContainer.getRelativePosition().intY(),
                null
        ));
    }
}
