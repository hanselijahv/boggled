package net.team6.boggled.user.server.state.users.elements;

import net.team6.boggled.common.core.Value;
import net.team6.boggled.user.server.gui.container.ServerContainer;
import net.team6.boggled.user.server.gui.container.ServerVerticalContainer;
import net.team6.boggled.user.server.gui.input.ServerTextInput;
import net.team6.boggled.user.server.gui.tools.Alignment;
import net.team6.boggled.user.server.gui.tools.Spacing;
import net.team6.boggled.user.server.gui.clickable.ServerButton;
import net.team6.boggled.user.server.state.menu.ServerMenuState;

public class UpdateUsersUI extends ServerVerticalContainer {
    private Value<String> username;
    private Value<String> password;

    public UpdateUsersUI() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        this.username = new Value<>("");
        this.password = new Value<>("");
        centerChildren = true;



    }
}
