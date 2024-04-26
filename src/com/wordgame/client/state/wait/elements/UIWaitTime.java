package com.wordgame.client.state.wait.elements;

import com.wordgame.client.game.time.Time;
import com.wordgame.client.state.State;
import com.wordgame.client.state.wait.WaitState;
import com.wordgame.client.gui.Alignment;
import com.wordgame.client.gui.HorizontalContainer;
import com.wordgame.client.gui.text.UIText;

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
