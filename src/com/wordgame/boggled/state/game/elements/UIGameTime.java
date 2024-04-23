package com.wordgame.boggled.state.game.elements;

import com.wordgame.boggled.game.time.Time;
import com.wordgame.boggled.state.State;
import com.wordgame.boggled.state.game.GameState;
import com.wordgame.boggled.ui.Alignment;
import com.wordgame.boggled.ui.HorizontalContainer;
import com.wordgame.boggled.ui.text.UIText;

public class UIGameTime extends HorizontalContainer {

    private UIText gameTime;

    public UIGameTime() {
        super();
        this.alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.START);
        this.gameTime = new UIText("", 16);
        addUIComponent(gameTime);
    }

    @Override
    public void update(State state) {
        super.update(state);
        Time gameTime = ((GameState) state).getGameTimer();
        this.gameTime.setText(gameTime.getFormattedTime());
    }
}
