package temp.state.menu.elements;

import temp.core.Size;
import temp.state.game.GameState;
import temp.state.menu.MenuState;
import temp.ui.*;
import temp.ui.clickable.UIButton;
import temp.ui.text.UIText;

import java.awt.*;

public class UIMainMenu extends VerticalContainer {
    public UIMainMenu() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        setBackgroundColor(Color.DARK_GRAY);
        centerChildren = true;

        addUIComponent(new UIText("BOGGLED", 24));

        setPadding(new Spacing(10));
        addUIComponent(new UIButton("PLAY", 16, (state) -> state.setNextState(new GameState(state.getWindowSize(), state.getInput(), state.getGameSettings()))));
        addUIComponent(new UIButton("OPTIONS", 16, (state) -> ((MenuState) state).enterMenu(new UIOptionMenu(state.getWindowSize(), state.getGameSettings()))));
        addUIComponent(new UIButton("EXIT", 16, (state) -> System.exit(0)));


    }
}