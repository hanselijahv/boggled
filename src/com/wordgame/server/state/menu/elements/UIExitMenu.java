package com.wordgame.server.state.menu.elements;

import com.wordgame.server.state.menu.ServerMenuState;
import com.wordgame.client.gui.VerticalContainer;
import com.wordgame.client.gui.Alignment;
import com.wordgame.client.gui.Spacing;
import com.wordgame.client.gui.clickable.UIButton;
import com.wordgame.client.gui.text.UIHeader;

public class UIExitMenu extends VerticalContainer {
    public UIExitMenu() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        centerChildren = true;

        setPadding(new Spacing(0,0,50,0));
        final UIHeader header = new UIHeader("Shut down Boggled?", 80);
        header.setPadding(new Spacing(0,0,50,0));
        addUIComponent(header);

        addUIComponent(new UIButton("YES", 16, (state) -> System.exit(0)));
        addUIComponent(new UIButton("NO", 16, (state) -> ((ServerMenuState) state).enterMenu(new ServerUIMainMenu())));

    }
}
