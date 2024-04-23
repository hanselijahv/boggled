package temp.state.menu.elements;

import temp.state.game.GameState;
import temp.state.menu.MenuState;
import temp.ui.*;
import temp.ui.clickable.UIButton;
import temp.ui.text.UIHeader;

public class UIMainMenu extends VerticalContainer {
    public UIMainMenu() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        centerChildren = true;

        setPadding(new Spacing(0,0,50,0));
        final UIHeader header = new UIHeader("Boggled", 72);
        header.setPadding(new Spacing(0,0,50,0));
        addUIComponent(header);

        addUIComponent(new UIButton("PLAY", 16, (state) -> state.setNextState(new GameState(state.getWindowSize(), state.getInput(), state.getGameSettings()))));
        addUIComponent(new UIButton("OPTIONS", 16, (state) -> ((MenuState) state).enterMenu(new UIOptionMenu(state.getGameSettings()))));
        addUIComponent(new UIButton("EXIT", 16, (state) -> System.exit(0)));


    }
}