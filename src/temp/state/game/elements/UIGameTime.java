package temp.state.game.elements;

import temp.core.Size;
import temp.game.Time;
import temp.state.State;
import temp.state.game.GameState;
import temp.ui.Alignment;
import temp.ui.HorizontalContainer;
import temp.ui.text.UIText;

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
