package Server_Java.net.team6.boggled.server.state.menu.elements;

import Server_Java.net.team6.boggled.server.gui.clickable.ServerButton;
import Server_Java.net.team6.boggled.server.gui.container.ServerVerticalContainer;
import Server_Java.net.team6.boggled.server.gui.text.ServerHeader;
import Server_Java.net.team6.boggled.server.gui.tools.Alignment;
import Server_Java.net.team6.boggled.server.gui.tools.Spacing;
import Server_Java.net.team6.boggled.server.state.menu.ServerMenuState;
import Server_Java.net.team6.boggled.server.state.users.UsersState;
import Server_Java.net.team6.boggled.server.state.users.elements.CreateUsersUI;
import Server_Java.net.team6.boggled.server.state.users.elements.UpdateUsersUI;

public class ServerUsersMenu extends ServerVerticalContainer {
    public ServerUsersMenu() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        centerChildren = true;

        setPadding(new Spacing(0,0,50,0));
        final ServerHeader header = new ServerHeader("Users", 120);
        header.setPadding(new Spacing(0,0,50,0));
        addUIComponent(header);


        addUIComponent(new ServerButton("CREATE A USER", 16, (state) -> ((UsersState) state).enterMenu(new CreateUsersUI())));
        addUIComponent(new ServerButton("UPDATE USERS", 16, (state) -> ((UsersState) state).enterMenu(new UpdateUsersUI())));
        addUIComponent(new ServerButton("BACK", 16, (state) -> state.setNextState(new ServerMenuState(state.getWindowSize(), state.getInput(), state.getBoggledSettings()))));
    }
}
