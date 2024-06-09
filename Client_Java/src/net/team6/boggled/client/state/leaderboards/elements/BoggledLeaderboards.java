package net.team6.boggled.client.state.leaderboards.elements;

import Client_Java.net.team6.boggled.client.gui.clickable.BoggledButton;
import Client_Java.net.team6.boggled.client.gui.component.BoggledComponent;
import Client_Java.net.team6.boggled.client.gui.component.BoggledHorizontalDivider;
import Client_Java.net.team6.boggled.client.gui.container.BoggledContainer;
import Client_Java.net.team6.boggled.client.gui.container.HorizontalContainer;
import Client_Java.net.team6.boggled.client.gui.container.VerticalContainer;
import Client_Java.net.team6.boggled.client.gui.text.BoggledHeader;
import Client_Java.net.team6.boggled.client.gui.text.BoggledText;
import Client_Java.net.team6.boggled.client.gui.tools.Alignment;
import Client_Java.net.team6.boggled.client.state.menu.MenuState;

public class BoggledLeaderboards extends VerticalContainer {
    public BoggledLeaderboards() {
        setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        setCenterChildren(true);
        addUIComponent(new BoggledHeader("LEADERBOARDS", 80));

        BoggledContainer leaderboardsContainer = new VerticalContainer();
        leaderboardsContainer.addUIComponent(createScoreRows());

        addUIComponent(leaderboardsContainer);
        addUIComponent(new BoggledButton("BACK", 16, state -> state.setNextState(new MenuState(state.getWindowSize(), state.getInput(), state.getGameSettings()))));
    }

    private BoggledComponent createScoreRows(){
        BoggledContainer scoreRow = new HorizontalContainer();
        scoreRow.addUIComponent(new BoggledText("1. User1", 16));
        scoreRow.addUIComponent(new BoggledHorizontalDivider());
        scoreRow.addUIComponent(new BoggledText("100", 16));
        return scoreRow;
    }


}
