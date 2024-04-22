package temp.state.menu.elements;

import temp.core.Size;
import temp.state.game.GameState;
import temp.state.menu.MenuState;
import temp.ui.*;
import temp.ui.clickable.UIButton;

public class UIMainMenu extends VerticalContainer {
    public UIMainMenu(Size windowSize) {
        super(windowSize);
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        centerChildren = true;
        UIContainer titleContainer = new HorizontalContainer(windowSize);
        titleContainer.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.START));
        titleContainer.addUIComponent(new UIText("Boggled", 56));

        addUIComponent(titleContainer);

        addUIComponent(new UIButton("PLAY", 16,  (state) -> state.setNextState(new GameState(windowSize, state.getInput()))));
        addUIComponent(new UIButton("OPTIONS", 16, (state) -> ((MenuState)state).enterMenu(new UIOptionMenu(windowSize))));
        addUIComponent(new UIButton("EXIT", 16, (state) -> System.exit(0)));
    }
}