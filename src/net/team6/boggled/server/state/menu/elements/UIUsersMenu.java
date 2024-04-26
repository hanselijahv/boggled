package net.team6.boggled.server.state.menu.elements;

import net.team6.boggled.server.state.menu.ServerMenuState;
import net.team6.boggled.client.gui.Alignment;
import net.team6.boggled.client.gui.Spacing;
import net.team6.boggled.client.gui.VerticalContainer;
import net.team6.boggled.client.gui.clickable.UIButton;
import net.team6.boggled.client.gui.text.UIHeader;

public class UIUsersMenu extends VerticalContainer {
    public UIUsersMenu() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        centerChildren = true;

        setPadding(new Spacing(0,0,50,0));
        final UIHeader header = new UIHeader("Users", 120);
        header.setPadding(new Spacing(0,0,50,0));
        addUIComponent(header);


        addUIComponent(new UIButton("BACK", 16, (state) -> ((ServerMenuState) state).enterMenu(new ServerUIMainMenu())));
    }
}
