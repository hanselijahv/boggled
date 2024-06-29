package net.team6.boggled.client.state.leaderboards.elements;

import BoggledApp.Leaderboards;
import net.team6.boggled.client.gui.clickable.BoggledButton;
import net.team6.boggled.client.gui.component.BoggledComponent;
import net.team6.boggled.client.gui.component.BoggledHorizontalDivider;
import net.team6.boggled.client.gui.component.BoggledVerticalDivider;
import net.team6.boggled.client.gui.container.BoggledContainer;
import net.team6.boggled.client.gui.container.GridContainer;
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
        GridContainer scoreGrid = new GridContainer(3); // 3 columns for Place, Username, and Score
        Alignment centerText = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);

        scoreGrid.addUIComponent(new BoggledText("PLACE", 27));
        scoreGrid.addUIComponent(new BoggledText("NAME", 27));
        scoreGrid.addUIComponent(new BoggledText("SCORE", 27));

        // Add a divider
        scoreGrid.addUIComponent(new BoggledVerticalDivider(200));
        scoreGrid.addUIComponent(new BoggledVerticalDivider(200));
        scoreGrid.addUIComponent(new BoggledVerticalDivider(200));

        Leaderboards leaderboards = Connect.boggledImpl.getLeaderboard();
        String[] leaderboardArray = leaderboards.leaderboard;

        for (int i = 0; i < leaderboardArray.length; i++) {
            String[] playerData = leaderboardArray[i].split(",");
            String username = playerData[0];
            String score = playerData[1];

            if (!score.equals("0")) {
                scoreGrid.addUIComponent(new BoggledText((i+1) + ".", 25));
                scoreGrid.addUIComponent(new BoggledText(username, 25));
                scoreGrid.addUIComponent(new BoggledText(score, 25));
            }
        }

        return scoreGrid;
    }

}
