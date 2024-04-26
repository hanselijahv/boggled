package net.team6.boggled.client.state.menu.elements;

import net.team6.boggled.client.gui.container.VerticalContainer;
import net.team6.boggled.client.state.wait.WaitState;
import net.team6.boggled.client.state.menu.MenuState;
import net.team6.boggled.client.gui.clickable.UIButton;
import net.team6.boggled.client.gui.text.UIHeader;
import net.team6.boggled.client.gui.Alignment;
import net.team6.boggled.client.gui.Spacing;

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