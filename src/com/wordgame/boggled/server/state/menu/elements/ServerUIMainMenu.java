package com.wordgame.boggled.server.state.menu.elements;

import com.wordgame.boggled.server.state.menu.ServerMenuState;
import com.wordgame.boggled.ui.Alignment;
import com.wordgame.boggled.ui.Spacing;
import com.wordgame.boggled.ui.VerticalContainer;
import com.wordgame.boggled.ui.clickable.UIButton;
import com.wordgame.boggled.ui.text.UIHeader;

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
