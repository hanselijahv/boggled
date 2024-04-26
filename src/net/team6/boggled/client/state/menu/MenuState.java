package net.team6.boggled.client.state.menu;


import net.team6.boggled.client.core.Size;
import net.team6.boggled.client.game.settings.GameSettings;
import net.team6.boggled.client.input.Input;
import net.team6.boggled.client.state.State;
import net.team6.boggled.client.state.menu.elements.UIMainMenu;
import net.team6.boggled.client.gui.container.UIContainer;

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
