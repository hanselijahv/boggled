package net.team6.boggled.user.client.state.menu.elements;

import net.team6.boggled.user.client.gui.container.VerticalContainer;
import net.team6.boggled.user.client.state.waiting.WaitingState;
import net.team6.boggled.user.client.state.menu.MenuState;
import net.team6.boggled.user.client.gui.clickable.BoggledButton;
import net.team6.boggled.user.client.gui.text.BoggledHeader;
import net.team6.boggled.user.client.gui.tools.Alignment;
import net.team6.boggled.user.client.gui.tools.Spacing;

public class BoggledMainMenu extends VerticalContainer {
    public BoggledMainMenu() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        centerChildren = true;

        setPadding(new Spacing(0,0,50,0));
        final BoggledHeader header = new BoggledHeader("Boggled", 120);
        header.setPadding(new Spacing(0,0,50,0));
        addUIComponent(header);


        addUIComponent(new BoggledButton("CREATE GAME", 16, (state) -> state.setNextState(new WaitingState(state.getWindowSize(), state.getInput(), state.getGameSettings()))));
        addUIComponent(new BoggledButton("OPTIONS", 16, (state) -> ((MenuState) state).enterMenu(new BoggledOptionMenu(state.getGameSettings()))));
        addUIComponent(new BoggledButton("EXIT", 16, (state) -> ((MenuState) state).enterMenu(new BoggledExitMenu())));
    }
}