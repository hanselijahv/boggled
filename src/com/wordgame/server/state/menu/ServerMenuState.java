package com.wordgame.server.state.menu;

import com.wordgame.client.core.Size;
import com.wordgame.client.input.Input;
import com.wordgame.server.state.menu.elements.ServerUIMainMenu;
import com.wordgame.server.state.settings.BoggledSettings;
import com.wordgame.client.state.State;
import com.wordgame.client.gui.UIContainer;

public class ServerMenuState extends State {
    public ServerMenuState(Size windowSize, Input input, BoggledSettings boggledSettings) {
        super(windowSize, input, boggledSettings);

        uiCanvas.addUIComponent(new ServerUIMainMenu());

    }

    public void enterMenu(UIContainer container) {
        uiCanvas.clear();
        uiCanvas.addUIComponent(container);
    }

    @Override
    protected void handleInput() {

    }

}
