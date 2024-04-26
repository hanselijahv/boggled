package com.wordgame.server.state.menu.elements;

import com.wordgame.server.state.menu.ServerMenuState;
import com.wordgame.client.gui.Alignment;
import com.wordgame.client.gui.Spacing;
import com.wordgame.client.gui.VerticalContainer;
import com.wordgame.client.gui.clickable.UIButton;
import com.wordgame.client.gui.text.UIHeader;

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
