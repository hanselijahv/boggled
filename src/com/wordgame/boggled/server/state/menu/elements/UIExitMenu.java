package com.wordgame.boggled.server.state.menu.elements;

import com.wordgame.boggled.server.state.menu.ServerMenuState;
import com.wordgame.boggled.ui.VerticalContainer;
import com.wordgame.boggled.ui.Alignment;
import com.wordgame.boggled.ui.Spacing;
import com.wordgame.boggled.ui.VerticalContainer;
import com.wordgame.boggled.ui.clickable.UIButton;
import com.wordgame.boggled.ui.text.UIHeader;

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
