package temp.state.game;

import temp.core.Size;
import temp.game.settings.GameSettings;
import temp.state.game.elements.UIGameTime;
import temp.input.Input;
import temp.state.State;
import temp.state.menu.MenuState;
import temp.ui.*;
import temp.ui.clickable.UIButton;

import java.awt.*;

public class GameState extends State {
    public GameState(Size windowSize, Input input, GameSettings gameSettings) {
        super(windowSize, input, gameSettings);
        initializeUI(windowSize);

    }

    private void initializeUI(Size windowSize) {
        uiContainers.add(new UIGameTime(windowSize));

        VerticalContainer verticalContainer = new VerticalContainer(windowSize);
        verticalContainer.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        verticalContainer.setBackgroundColor(Color.DARK_GRAY);
        verticalContainer.addUIComponent(new UIButton("Menu", 16, (state) -> state.setNextState(new MenuState(windowSize, input, gameSettings))));
        verticalContainer.addUIComponent(new UIButton("Options", 16, (state) -> System.out.println("Button 2 pressed!")));
        verticalContainer.addUIComponent(new UIButton("Exit", 16, (state) -> System.exit(0)));
        uiContainers.add(verticalContainer);


    }


}
