package net.team6.boggled.user.server.state.menu;

import net.team6.boggled.common.core.Size;
import net.team6.boggled.user.server.input.ServerInput;
import net.team6.boggled.user.server.state.ServerState;
import net.team6.boggled.user.server.state.menu.elements.ServerMainMenu;
import net.team6.boggled.user.server.dev.settings.ServerSettings;
import net.team6.boggled.user.server.gui.container.ServerContainer;

public class ServerMenuState extends ServerState {
    public ServerMenuState(Size windowSize, ServerInput serverInput, ServerSettings serverSettings) {
        super(windowSize, serverInput, serverSettings);

        serverCanvas.addUIComponent(new ServerMainMenu());

    }

    public void enterMenu(ServerContainer container) {
        serverCanvas.clear();
        serverCanvas.addUIComponent(container);
    }

    @Override
    protected void handleInput() {

    }

}
