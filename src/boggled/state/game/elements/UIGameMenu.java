package boggled.state.game.elements;

import boggled.game.settings.GameSettings;
import boggled.input.Input;
import boggled.state.game.GameState;
import boggled.state.menu.MenuState;
import boggled.ui.Alignment;
import boggled.ui.Spacing;
import boggled.ui.UIContainer;
import boggled.ui.VerticalContainer;
import boggled.ui.clickable.UIButton;



public class UIGameMenu extends VerticalContainer {

    private final UIContainer headerContent;

    public UIGameMenu(Input input, GameSettings settings) {
        setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        centerChildren = true;

        setPadding(new Spacing(0,0,50,0));
        headerContent = new VerticalContainer();
        headerContent.setPadding(new Spacing(0,0,50,0));

        UIContainer buttonContainer = new VerticalContainer();
        buttonContainer.setPadding(new Spacing(0, 5, 5, 5));

        buttonContainer.addUIComponent(new UIButton("MENU", 16, (state) -> state.setNextState(new MenuState(state.getWindowSize(), input, settings))));
        buttonContainer.addUIComponent(new UIButton("RESUME", 16, (state) -> ((GameState) state).togglePause(false)));
        buttonContainer.addUIComponent(new UIButton("EXIT", 16, (state) -> System.exit(0)));

        addUIComponent(headerContent);
        addUIComponent(buttonContainer);
    }

    public void setHeaderContent(UIContainer content) {
        headerContent.clear();
        headerContent.addUIComponent(content);
    }
}
