package net.team6.boggled.server.state.menu.elements;

import net.team6.boggled.server.state.menu.ServerMenuState;
import net.team6.boggled.server.gui.tools.Alignment;
import net.team6.boggled.server.gui.tools.Spacing;
import net.team6.boggled.server.gui.container.ServerVerticalContainer;
import net.team6.boggled.server.gui.clickable.ServerButton;
import net.team6.boggled.server.gui.text.ServerHeader;
import net.team6.boggled.server.state.users.UsersState;

public class ServerMainMenu extends ServerVerticalContainer {
    public ServerMainMenu() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        centerChildren = true;

        setPadding(new Spacing(0,0,50,0));
        final ServerHeader header = new ServerHeader("Boggled Server", 120);
        header.setPadding(new Spacing(0,0,50,0));
        addUIComponent(header);

        addUIComponent(new ServerButton("EDIT USERS", 16, (state) -> state.setNextState(new UsersState(state.getWindowSize(), state.getInput(), state.getBoggledSettings()))));
        addUIComponent(new ServerButton("EDIT SETTINGS", 16, (state) -> ((ServerMenuState) state).enterMenu(new ServerSettingsMenu(state.getBoggledSettings()))));
        addUIComponent(new ServerButton("EXIT", 16, (state) -> ((ServerMenuState) state).enterMenu(new ServerExitMenu())));

    }
}
