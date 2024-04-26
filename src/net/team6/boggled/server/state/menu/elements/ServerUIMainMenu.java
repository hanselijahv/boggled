package net.team6.boggled.server.state.menu.elements;

import net.team6.boggled.server.state.menu.ServerMenuState;
import net.team6.boggled.client.gui.Alignment;
import net.team6.boggled.client.gui.Spacing;
import net.team6.boggled.client.gui.container.VerticalContainer;
import net.team6.boggled.client.gui.clickable.UIButton;
import net.team6.boggled.client.gui.text.UIHeader;

public class ServerUIMainMenu extends VerticalContainer {
    public ServerUIMainMenu() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        centerChildren = true;

        setPadding(new Spacing(0,0,50,0));
        final UIHeader header = new UIHeader("Boggled Server", 120);
        header.setPadding(new Spacing(0,0,50,0));
        addUIComponent(header);

        addUIComponent(new UIButton("EDIT USERS", 16, (state) -> ((ServerMenuState) state).enterMenu(new UIUsersMenu())));
        addUIComponent(new UIButton("EDIT SETTINGS", 16, (state) -> ((ServerMenuState) state).enterMenu(new UISettingsMenu(state.getBoggledSettings()))));
        addUIComponent(new UIButton("EXIT", 16, (state) -> ((ServerMenuState) state).enterMenu(new UIExitMenu())));

    }
}
