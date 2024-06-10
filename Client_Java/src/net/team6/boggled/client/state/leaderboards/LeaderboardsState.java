package net.team6.boggled.client.state.leaderboards;

import net.team6.boggled.client.game.Game;
import net.team6.boggled.client.game.settings.GameSettings;
import net.team6.boggled.client.input.Input;
import net.team6.boggled.client.state.State;
import net.team6.boggled.client.state.leaderboards.elements.BoggledLeaderboards;
import net.team6.boggled.common.core.Size;

public class LeaderboardsState extends State {
    public LeaderboardsState(Size windowSize, Input input, GameSettings gameSettings) {
        super(windowSize, input, gameSettings);

        boggledCanvas.addUIComponent(new BoggledLeaderboards());

    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(Game game) {
    }

}

