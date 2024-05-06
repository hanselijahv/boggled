package net.team6.boggled.user.server.state.users.elements;

import net.team6.boggled.common.core.Value;
import net.team6.boggled.user.server.gui.container.ServerContainer;
import net.team6.boggled.user.server.gui.container.ServerVerticalContainer;
import net.team6.boggled.user.server.gui.input.ServerTextInput;
import net.team6.boggled.user.server.gui.text.ServerHeader;
import net.team6.boggled.user.server.gui.tools.Alignment;
import net.team6.boggled.user.server.gui.tools.Spacing;
import net.team6.boggled.user.server.gui.clickable.ServerButton;
import net.team6.boggled.user.server.state.menu.ServerMenuState;
import net.team6.boggled.user.server.state.menu.elements.ServerMainMenu;
import net.team6.boggled.user.server.state.menu.elements.ServerUsersMenu;
import net.team6.boggled.user.server.state.users.UsersState;

public class UpdateUsersUI extends ServerVerticalContainer {
    private Value<String> username;
    private Value<String> password;

    public UpdateUsersUI() {
        final ServerHeader header = new ServerHeader("Update/Remove Users", 80);
        header.setMargin(new Spacing(0, 0, 50, 0));
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        this.username = new Value<>("");
        this.password = new Value<>("");
        centerChildren = true;
        addUIComponent(header);

        addUIComponent(new ServerButton("BACK", 16, (state) -> state.setNextState(new UsersState(state.getWindowSize(), state.getInput(), state.getBoggledSettings()))));



    }
}
