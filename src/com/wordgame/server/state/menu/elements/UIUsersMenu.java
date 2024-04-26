package com.wordgame.server.state.menu.elements;

import com.wordgame.server.state.menu.ServerMenuState;
import com.wordgame.client.gui.*;
import com.wordgame.client.gui.clickable.UIButton;
import com.wordgame.client.gui.text.UIHeader;

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
