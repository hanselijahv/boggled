package net.team6.boggled.client.state.wait.elements;

import net.team6.boggled.client.game.time.Time;
import net.team6.boggled.client.state.State;
import net.team6.boggled.client.state.wait.WaitState;
import net.team6.boggled.client.gui.Alignment;
import net.team6.boggled.client.gui.HorizontalContainer;
import net.team6.boggled.client.gui.text.UIText;

import java.sql.SQLException;

public class UIWaitTime extends HorizontalContainer {

    private final UIText gameTime;

    public UIWaitTime() {
        super();
        this.alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.START);
        this.gameTime = new UIText("", 32);
        addUIComponent(gameTime);
    }

    @Override
    public void update(State state) throws SQLException {
        super.update(state);
        Time gameTime = ((WaitState) state).getGameTimer();
        this.gameTime.setText(gameTime.getFormattedTime());
    }
}
