package net.team6.boggled.server.state.menu;

import net.team6.boggled.common.core.Size;
import net.team6.boggled.server.dev.settings.ServerSettings;
import net.team6.boggled.server.gui.container.ServerContainer;
import net.team6.boggled.server.input.ServerInput;
import net.team6.boggled.server.state.ServerState;
import net.team6.boggled.server.state.menu.elements.ServerMainMenu;

public class ServerMenuState extends ServerState {
    public ServerMenuState(Size windowSize, ServerInput serverInput, ServerSettings serverSettings) {
        super(windowSize, serverInput, serverSettings);

        serverCsanvas.addUIComponent(new ServerMainMenu());

    }

    public void enterMenu(ServerContainer container) {
        serverCanvas.clear();
        serverCanvas.addUIComponent(container);
    }

    @Override
    protected void handleInput() {

    }

}
