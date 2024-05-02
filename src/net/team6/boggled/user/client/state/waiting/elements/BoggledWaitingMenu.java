package net.team6.boggled.user.client.state.waiting.elements;

import net.team6.boggled.user.client.game.settings.GameSettings;
import net.team6.boggled.user.client.input.Input;
import net.team6.boggled.user.client.state.menu.MenuState;
import net.team6.boggled.user.client.gui.tools.Alignment;
import net.team6.boggled.user.client.gui.tools.Spacing;
import net.team6.boggled.user.client.gui.container.BoggledContainer;
import net.team6.boggled.user.client.gui.container.VerticalContainer;
import net.team6.boggled.user.client.gui.clickable.BoggledButton;


public class BoggledWaitingMenu extends VerticalContainer {

    private final BoggledContainer headerContent;

    public BoggledWaitingMenu(Input input, GameSettings settings) {
        setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        centerChildren = true;

        setPadding(new Spacing(0,0,50,0));
        headerContent = new VerticalContainer();
        headerContent.setPadding(new Spacing(0,0,10,0));

        BoggledContainer buttonContainer = new VerticalContainer();
        buttonContainer.setPadding(new Spacing(0, 5, 5, 5));

        //buttonContainer.addUIComponent(new UIButton("RESUME", 16, (state) -> ((WaitState) state).toggleScore(false)));
        buttonContainer.addUIComponent(new BoggledButton("HOME", 16, (state) -> state.setNextState(new MenuState(state.getWindowSize(), input, settings))));
        //buttonContainer.addUIComponent(new UIButton("EXIT", 16, (state) -> System.exit(0)));

        addUIComponent(headerContent);
        addUIComponent(buttonContainer);

    }

    public void setHeaderContent(BoggledContainer content) {
        headerContent.clear();
        headerContent.addUIComponent(content);
    }
}
