package com.wordgame.boggled.state.menu;


import com.wordgame.boggled.core.Size;
import com.wordgame.boggled.game.settings.GameSettings;
import com.wordgame.boggled.input.Input;
import com.wordgame.boggled.state.State;
import com.wordgame.boggled.state.menu.elements.UIMainMenu;
import com.wordgame.boggled.ui.UIContainer;

public class MenuState extends State {
    public MenuState(Size windowSize, Input input, GameSettings gameSettings) {
        super(windowSize, input, gameSettings);

        uiCanvas.addUIComponent(new UIMainMenu());
        //audioPlayer.playMusic("main.wav");

    }

    public void enterMenu(UIContainer container) {
        uiCanvas.clear();
        uiCanvas.addUIComponent(container);
    }

    @Override
    protected void handleInput() {

    }
}
