package net.team6.boggled.client.state.home;


import net.team6.boggled.common.core.Size;
import net.team6.boggled.client.game.settings.GameSettings;
import net.team6.boggled.common.input.Input;
import net.team6.boggled.client.state.State;
import net.team6.boggled.client.state.home.elements.UIHomeMenu;
import net.team6.boggled.common.gui.container.UIContainer;

public class HomeState extends State {
    public HomeState(Size windowSize, Input input, GameSettings gameSettings) {
        super(windowSize, input, gameSettings);

        uiCanvas.addUIComponent(new UIHomeMenu());
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
