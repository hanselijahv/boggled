package temp.state.game.elements;

import temp.core.Size;
import temp.state.State;
import temp.ui.Alignment;
import temp.ui.HorizontalContainer;
import temp.ui.UIText;

public class UIGameTime extends HorizontalContainer {

    private UIText gameTime;

    public UIGameTime(Size windowSize) {
        super(windowSize);
        this.alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.START);
        this.gameTime = new UIText("", 28);
        addUIComponent(gameTime);
    }


    public void update(State state){
        super.update(state);
        gameTime.setText(state.getTime().getFormattedTime());
    }
}
