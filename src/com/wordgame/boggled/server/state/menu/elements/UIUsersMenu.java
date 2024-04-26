package com.wordgame.boggled.server.state.menu.elements;

import com.wordgame.boggled.server.state.menu.ServerMenuState;
import com.wordgame.boggled.ui.*;
import com.wordgame.boggled.ui.clickable.UIButton;
import com.wordgame.boggled.ui.text.UIHeader;

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
