package Client_Java.net.team6.boggled.client.state.leaderboards;

import Client_Java.net.team6.boggled.client.game.Game;
import Client_Java.net.team6.boggled.client.game.settings.GameSettings;
import Client_Java.net.team6.boggled.client.input.Input;
import Client_Java.net.team6.boggled.client.state.State;
import Client_Java.net.team6.boggled.client.state.leaderboards.elements.BoggledLeaderboards;
import Client_Java.net.team6.boggled.common.core.Size;

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

