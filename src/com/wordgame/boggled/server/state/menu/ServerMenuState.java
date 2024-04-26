package com.wordgame.boggled.server.state.menu;

import com.wordgame.boggled.core.Size;
import com.wordgame.boggled.input.Input;
import com.wordgame.boggled.server.state.menu.elements.ServerUIMainMenu;
import com.wordgame.boggled.server.state.settings.BoggledSettings;
import com.wordgame.boggled.state.State;
import com.wordgame.boggled.ui.UIContainer;

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
