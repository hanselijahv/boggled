package net.team6.boggled.client.state.waiting.elements;

import net.team6.boggled.client.game.settings.GameSettings;
import net.team6.boggled.common.input.Input;
import net.team6.boggled.client.state.home.HomeState;
import net.team6.boggled.common.gui.tools.Alignment;
import net.team6.boggled.common.gui.tools.Spacing;
import net.team6.boggled.common.gui.container.UIContainer;
import net.team6.boggled.common.gui.container.VerticalContainer;
import net.team6.boggled.common.gui.clickable.UIButton;


public class UIWaitingMenu extends VerticalContainer {

    private final UIContainer headerContent;

    public UIWaitingMenu(Input input, GameSettings settings) {
        setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        centerChildren = true;

        setPadding(new Spacing(0,0,50,0));
        headerContent = new VerticalContainer();
        headerContent.setPadding(new Spacing(0,0,10,0));

        UIContainer buttonContainer = new VerticalContainer();
        buttonContainer.setPadding(new Spacing(0, 5, 5, 5));

        //buttonContainer.addUIComponent(new UIButton("RESUME", 16, (state) -> ((WaitState) state).toggleScore(false)));
        buttonContainer.addUIComponent(new UIButton("HOME", 16, (state) -> state.setNextState(new HomeState(state.getWindowSize(), input, settings))));
        //buttonContainer.addUIComponent(new UIButton("EXIT", 16, (state) -> System.exit(0)));

        addUIComponent(headerContent);
        addUIComponent(buttonContainer);

    }

    public void setHeaderContent(UIContainer content) {
        headerContent.clear();
        headerContent.addUIComponent(content);
    }
}
