package com.wordgame.boggled.state.play.elements;

import com.wordgame.boggled.game.settings.GameSettings;
import com.wordgame.boggled.input.Input;
import com.wordgame.boggled.state.play.PlayState;
import com.wordgame.boggled.state.menu.MenuState;
import com.wordgame.boggled.ui.Alignment;
import com.wordgame.boggled.ui.Spacing;
import com.wordgame.boggled.ui.UIContainer;
import com.wordgame.boggled.ui.VerticalContainer;
import com.wordgame.boggled.ui.clickable.UIButton;


public class UIPlayMenu extends VerticalContainer {

    private final UIContainer headerContent;

    public UIPlayMenu(Input input, GameSettings settings) {
        setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        centerChildren = true;

        setPadding(new Spacing(0,0,50,0));
        headerContent = new VerticalContainer();
        headerContent.setPadding(new Spacing(0,0,10,0));

        UIContainer buttonContainer = new VerticalContainer();
        buttonContainer.setPadding(new Spacing(0, 5, 5, 5));

        buttonContainer.addUIComponent(new UIButton("RESUME", 16, (state) -> ((PlayState) state).toggleScore(false)));
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
