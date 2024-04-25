package com.wordgame.boggled.state.menu.elements;

import com.wordgame.boggled.state.wait.WaitState;
import com.wordgame.boggled.state.menu.MenuState;
import com.wordgame.boggled.ui.*;
import com.wordgame.boggled.ui.clickable.UIButton;
import com.wordgame.boggled.ui.text.UIHeader;

public class UIMainMenu extends VerticalContainer {
    public UIMainMenu() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        centerChildren = true;

        setPadding(new Spacing(0,0,50,0));
        final UIHeader header = new UIHeader("Boggled", 120);
        header.setPadding(new Spacing(0,0,50,0));
        addUIComponent(header);

        addUIComponent(new UIButton("CREATE GAME", 16, (state) -> state.setNextState(new WaitState(state.getWindowSize(), state.getInput(), state.getGameSettings()))));
        addUIComponent(new UIButton("OPTIONS", 16, (state) -> ((MenuState) state).enterMenu(new UIOptionMenu(state.getGameSettings()))));
        addUIComponent(new UIButton("EXIT", 16, (state) -> System.exit(0)));


    }
}