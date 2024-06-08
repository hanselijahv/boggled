package Server_Java.net.team6.boggled.server.state.users;

import Server_Java.net.team6.boggled.common.core.Size;
import Server_Java.net.team6.boggled.server.dev.settings.ServerSettings;
import Server_Java.net.team6.boggled.server.gui.container.ServerContainer;
import Server_Java.net.team6.boggled.server.input.ServerInput;
import Server_Java.net.team6.boggled.server.state.ServerState;
import Server_Java.net.team6.boggled.server.state.menu.elements.ServerUsersMenu;

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
