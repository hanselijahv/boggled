package net.team6.boggled.server.state.menu;

import net.team6.boggled.common.core.Size;
import net.team6.boggled.common.input.Input;
import net.team6.boggled.server.state.menu.elements.ServerUIMainMenu;
import net.team6.boggled.server.settings.BoggledSettings;
import net.team6.boggled.client.state.State;
import net.team6.boggled.common.gui.container.UIContainer;

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
