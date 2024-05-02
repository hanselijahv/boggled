package net.team6.boggled.server.state.menu.elements;

import net.team6.boggled.server.state.menu.ServerMenuState;
import net.team6.boggled.common.gui.tools.Alignment;
import net.team6.boggled.common.gui.tools.Spacing;
import net.team6.boggled.common.gui.container.VerticalContainer;
import net.team6.boggled.common.gui.clickable.UIButton;
import net.team6.boggled.common.gui.text.UIHeader;

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
