package net.team6.boggled.server.state.users;

import net.team6.boggled.common.core.Size;
import net.team6.boggled.server.gui.container.ServerContainer;
import net.team6.boggled.server.input.ServerInput;
import net.team6.boggled.server.state.ServerState;
import net.team6.boggled.server.dev.settings.ServerSettings;
import net.team6.boggled.server.state.menu.elements.ServerUsersMenu;
import net.team6.boggled.server.state.users.elements.CreateUsersUI;

public class UsersState extends ServerState {
    public UsersState(Size windowSize, ServerInput serverInput, ServerSettings serverSettings) {
        super(windowSize, serverInput, serverSettings);

        serverCanvas.addUIComponent(new ServerUsersMenu());
    }

    public void enterMenu(ServerContainer container) {
        serverCanvas.clear();
        serverCanvas.addUIComponent(container);
    }

    @Override
    protected void handleInput() {

    }

}
