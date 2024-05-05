package net.team6.boggled.user.server.state.menu.elements;

import net.team6.boggled.user.server.state.menu.ServerMenuState;
import net.team6.boggled.user.server.gui.tools.Alignment;
import net.team6.boggled.user.server.gui.tools.Spacing;
import net.team6.boggled.user.server.gui.container.ServerVerticalContainer;
import net.team6.boggled.user.server.gui.clickable.ServerButton;
import net.team6.boggled.user.server.gui.text.ServerHeader;
import net.team6.boggled.user.server.state.users.UsersState;
import net.team6.boggled.user.server.state.users.elements.CreateUsersUI;
import net.team6.boggled.user.server.state.users.elements.UpdateUsersUI;

public class ServerUsersMenu extends ServerVerticalContainer {
    public ServerUsersMenu() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        centerChildren = true;

        setPadding(new Spacing(0,0,50,0));
        final ServerHeader header = new ServerHeader("Users", 120);
        header.setPadding(new Spacing(0,0,50,0));
        addUIComponent(header);


        addUIComponent(new ServerButton("CREATE A USER", 16, (state) -> state.setNextState(new UsersState(state.getWindowSize(), state.getInput(), state.getBoggledSettings()))));
        addUIComponent(new ServerButton("UPDATE USERS", 16, (state) -> ((ServerMenuState) state).enterMenu(new UpdateUsersUI())));
        addUIComponent(new ServerButton("BACK", 16, (state) -> ((ServerMenuState) state).enterMenu(new ServerMainMenu())));
    }
}
