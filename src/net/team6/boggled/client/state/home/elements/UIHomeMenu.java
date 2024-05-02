package net.team6.boggled.client.state.home.elements;

import net.team6.boggled.client.state.entry.EntryState;
import net.team6.boggled.common.gui.container.VerticalContainer;
import net.team6.boggled.client.state.waiting.WaitingState;
import net.team6.boggled.client.state.home.HomeState;
import net.team6.boggled.common.gui.clickable.UIButton;
import net.team6.boggled.common.gui.text.UIHeader;
import net.team6.boggled.common.gui.tools.Alignment;
import net.team6.boggled.common.gui.tools.Spacing;

public class UIHomeMenu extends VerticalContainer {
    public UIHomeMenu() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        centerChildren = true;

        setPadding(new Spacing(0,0,50,0));
        final UIHeader header = new UIHeader("Boggled", 120);
        header.setPadding(new Spacing(0,0,50,0));
        addUIComponent(header);


        addUIComponent(new UIButton("LOGIN" ,16, (state) -> state.setNextState(new EntryState(state.getWindowSize(), state.getInput(), state.getGameSettings()))));
        addUIComponent(new UIButton("CREATE GAME", 16, (state) -> state.setNextState(new WaitingState(state.getWindowSize(), state.getInput(), state.getGameSettings()))));
        addUIComponent(new UIButton("OPTIONS", 16, (state) -> ((HomeState) state).enterMenu(new UIHomeOptionMenu(state.getGameSettings()))));
        addUIComponent(new UIButton("EXIT", 16, (state) -> System.exit(0)));


    }
}