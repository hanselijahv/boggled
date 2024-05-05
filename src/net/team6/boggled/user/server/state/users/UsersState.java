package net.team6.boggled.user.server.state.users;

import net.team6.boggled.common.core.Size;
import net.team6.boggled.user.server.gui.container.ServerContainer;
import net.team6.boggled.user.server.input.ServerInput;
import net.team6.boggled.user.server.state.ServerState;
import net.team6.boggled.user.server.dev.settings.ServerSettings;
import net.team6.boggled.user.server.state.users.elements.CreateUsersUI;

public class UsersState extends ServerState {
    public UsersState(Size windowSize, ServerInput serverInput, ServerSettings serverSettings) {
        super(windowSize, serverInput, serverSettings);

        serverCanvas.addUIComponent(new CreateUsersUI());
    }

    public void enterMenu(ServerContainer container) {
        serverCanvas.clear();
        serverCanvas.addUIComponent(container);
    }

    @Override
    protected void handleInput() {

    }

}
