package net.team6.boggled.client.state.leaderboards.elements;

import BoggledApp.Leaderboards;
import net.team6.boggled.client.gui.clickable.BoggledButton;
import net.team6.boggled.client.gui.component.BoggledComponent;
import net.team6.boggled.client.gui.container.BoggledContainer;
import net.team6.boggled.client.gui.container.HorizontalContainer;
import net.team6.boggled.client.gui.container.VerticalContainer;
import net.team6.boggled.client.gui.text.BoggledHeader;
import net.team6.boggled.client.gui.text.BoggledText;
import net.team6.boggled.client.gui.tools.Alignment;
import net.team6.boggled.client.state.menu.MenuState;
import net.team6.boggled.run.Connect;

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
        BoggledContainer scoreRow = new VerticalContainer();

        BoggledContainer headerRow = new HorizontalContainer();
        headerRow.addUIComponent(new BoggledText("PLACE", 16));
        headerRow.addUIComponent(new BoggledText("USERNAME", 16));
        headerRow.addUIComponent(new BoggledText("SCORE", 16));
        scoreRow.addUIComponent(headerRow);

        Leaderboards leaderboards = Connect.boggledImpl.getLeaderboard();
        String[] leaderboardArray = leaderboards.leaderboard;

        for (int i = 0; i < leaderboardArray.length; i++) {
            String[] playerData = leaderboardArray[i].split(",");
            String username = playerData[0];
            String score = playerData[1];

            BoggledContainer row = new HorizontalContainer();
            row.addUIComponent(new BoggledText(String.format("%-5s", (i+1) + "."), 16)); // increased width to 5
            row.addUIComponent(new BoggledText(String.format("%-15s", username), 16)); // increased width to 15
            row.addUIComponent(new BoggledText(String.format("%-7s", score), 16)); // increased width to 7

            scoreRow.addUIComponent(row);
        }

        return scoreRow;
    }


}
