package net.team6.boggled.client.state.wait.elements;

import net.team6.boggled.client.game.settings.GameSettings;
import net.team6.boggled.client.input.Input;
import net.team6.boggled.client.state.menu.MenuState;
import net.team6.boggled.client.gui.Alignment;
import net.team6.boggled.client.gui.Spacing;
import net.team6.boggled.client.gui.container.UIContainer;
import net.team6.boggled.client.gui.container.VerticalContainer;
import net.team6.boggled.client.gui.clickable.UIButton;


public class UIWaitMenu extends VerticalContainer {

    private final UIContainer headerContent;

    public UIWaitMenu(Input input, GameSettings settings) {
        setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        centerChildren = true;

        setPadding(new Spacing(0,0,50,0));
        headerContent = new VerticalContainer();
        headerContent.setPadding(new Spacing(0,0,10,0));

        UIContainer buttonContainer = new VerticalContainer();
        buttonContainer.setPadding(new Spacing(0, 5, 5, 5));

        //buttonContainer.addUIComponent(new UIButton("RESUME", 16, (state) -> ((WaitState) state).toggleScore(false)));
        buttonContainer.addUIComponent(new UIButton("HOME", 16, (state) -> state.setNextState(new MenuState(state.getWindowSize(), input, settings))));
        //buttonContainer.addUIComponent(new UIButton("EXIT", 16, (state) -> System.exit(0)));

        addUIComponent(headerContent);
        addUIComponent(buttonContainer);

    }

    public void setHeaderContent(UIContainer content) {
        headerContent.clear();
        headerContent.addUIComponent(content);
    }
}
