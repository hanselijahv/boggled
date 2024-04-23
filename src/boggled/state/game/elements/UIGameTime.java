package boggled.state.game.elements;

import boggled.game.time.Time;
import boggled.state.State;
import boggled.state.game.GameState;
import boggled.ui.Alignment;
import boggled.ui.HorizontalContainer;
import boggled.ui.text.UIText;

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
