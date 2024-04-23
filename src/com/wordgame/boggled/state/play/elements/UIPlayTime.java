package com.wordgame.boggled.state.play.elements;

import com.wordgame.boggled.game.time.Time;
import com.wordgame.boggled.state.State;
import com.wordgame.boggled.state.play.PlayState;
import com.wordgame.boggled.ui.Alignment;
import com.wordgame.boggled.ui.HorizontalContainer;
import com.wordgame.boggled.ui.text.UIText;

public class UIPlayTime extends HorizontalContainer {

    private final UIText gameTime;

    public UIPlayTime() {
        super();
        this.alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.START);
        this.gameTime = new UIText("", 32);
        addUIComponent(gameTime);
    }

    @Override
    public void update(State state) {
        super.update(state);
        Time gameTime = ((PlayState) state).getGameTimer();
        this.gameTime.setText(gameTime.getFormattedTime());
    }
}
