package net.team6.boggled.client.state.waiting.elements;

import net.team6.boggled.client.game.time.Time;
import net.team6.boggled.client.state.State;
import net.team6.boggled.client.state.waiting.WaitingState;
import net.team6.boggled.common.gui.tools.Alignment;
import net.team6.boggled.common.gui.container.HorizontalContainer;
import net.team6.boggled.common.gui.text.UIText;

import java.sql.SQLException;

public class UIWaitingTimer extends HorizontalContainer {

    private final UIText gameTime;

    public UIWaitingTimer() {
        super();
        this.alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.START);
        this.gameTime = new UIText("", 32);
        addUIComponent(gameTime);
    }

    @Override
    public void update(State state) throws SQLException {
        super.update(state);
        Time gameTime = ((WaitingState) state).getGameTimer();
        this.gameTime.setText(gameTime.getFormattedTime());
    }
}
