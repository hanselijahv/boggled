package temp.state.game.elements;

import temp.core.Size;
import temp.game.settings.GameSettings;
import temp.input.Input;
import temp.state.game.GameState;
import temp.state.menu.MenuState;
import temp.ui.Alignment;
import temp.ui.Spacing;
import temp.ui.UIContainer;
import temp.ui.VerticalContainer;
import temp.ui.clickable.UIButton;

import java.awt.*;

public class UIGameMenu extends VerticalContainer {

    private UIContainer headerContent;

    public UIGameMenu(Input input, GameSettings settings) {
        setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        setCenterChildren(true);

        headerContent = new VerticalContainer();

        UIContainer buttonContainer = new VerticalContainer();
        buttonContainer.setPadding(new Spacing(0, 5, 5, 5));
        buttonContainer.setBackgroundColor(Color.DARK_GRAY);
        buttonContainer.addUIComponent(new UIButton("Menu", 16, (state) -> state.setNextState(new MenuState(state.getWindowSize(), input, settings))));
        buttonContainer.addUIComponent(new UIButton("Resume", 16, (state) -> ((GameState) state).togglePause(false)));
        buttonContainer.addUIComponent(new UIButton("Exit", 16, (state) -> System.exit(0)));

        addUIComponent(headerContent);
        addUIComponent(buttonContainer);
    }

    public void setHeaderContent(UIContainer content) {
        headerContent.clear();
        headerContent.addUIComponent(content);
    }
}
