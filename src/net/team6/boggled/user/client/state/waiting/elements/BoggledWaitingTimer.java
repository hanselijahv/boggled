package net.team6.boggled.user.client.state.waiting.elements;

import net.team6.boggled.user.client.game.time.Time;
import net.team6.boggled.user.client.state.State;
import net.team6.boggled.user.client.state.waiting.WaitingState;
import net.team6.boggled.user.client.gui.tools.Alignment;
import net.team6.boggled.user.client.gui.container.HorizontalContainer;
import net.team6.boggled.user.client.gui.text.BoggledText;

import java.sql.SQLException;

public class BoggledWaitingTimer extends HorizontalContainer {

    private final BoggledText gameTime;

    public BoggledWaitingTimer() {
        super();
        this.alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.START);
        this.gameTime = new BoggledText("", 32);
        addUIComponent(gameTime);
    }

    @Override
    public void update(State state) throws SQLException {
        super.update(state);
        Time gameTime = ((WaitingState) state).getGameTimer();
        this.gameTime.setText(gameTime.getFormattedTime());
    }
}
