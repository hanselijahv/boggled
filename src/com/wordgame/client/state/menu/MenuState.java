package com.wordgame.client.state.menu;


import com.wordgame.client.core.Size;
import com.wordgame.client.game.settings.GameSettings;
import com.wordgame.client.input.Input;
import com.wordgame.client.state.State;
import com.wordgame.client.state.menu.elements.UIMainMenu;
import com.wordgame.client.gui.UIContainer;

public class MenuState extends State {
    public MenuState(Size windowSize, Input input, GameSettings gameSettings) {
        super(windowSize, input, gameSettings);

        uiCanvas.addUIComponent(new UIMainMenu());
        audioPlayer.playMusic("main.wav");

    }

    public void enterMenu(UIContainer container) {
        uiCanvas.clear();
        uiCanvas.addUIComponent(container);
    }

    @Override
    protected void handleInput() {

    }
}
