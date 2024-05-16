package net.team6.boggled.client.state.leaderboards.elements;

import net.team6.boggled.client.gui.clickable.BoggledButton;
import net.team6.boggled.client.gui.component.BoggledComponent;
import net.team6.boggled.client.gui.component.BoggledHorizontalDivider;
import net.team6.boggled.client.gui.container.BoggledContainer;
import net.team6.boggled.client.gui.container.HorizontalContainer;
import net.team6.boggled.client.gui.container.VerticalContainer;
import net.team6.boggled.client.gui.text.BoggledHeader;
import net.team6.boggled.client.gui.text.BoggledText;
import net.team6.boggled.client.gui.tools.Alignment;
import net.team6.boggled.client.state.menu.MenuState;

public class BoggledLeaderboards extends VerticalContainer{
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
