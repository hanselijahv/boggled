package net.team6.boggled.user.client.state.menu;


import net.team6.boggled.common.core.Size;
import net.team6.boggled.user.client.game.settings.GameSettings;
import net.team6.boggled.user.client.input.Input;
import net.team6.boggled.user.client.state.State;
import net.team6.boggled.user.client.state.menu.elements.BoggledMainMenu;
import net.team6.boggled.user.client.gui.container.BoggledContainer;

public class MenuState extends State {
    public MenuState(Size windowSize, Input input, GameSettings gameSettings) {
        super(windowSize, input, gameSettings);

        boggledCanvas.addUIComponent(new BoggledMainMenu());
        //audioPlayer.playMusic("main.wav");

    }

    public void enterMenu(BoggledContainer container) {
        boggledCanvas.clear();
        boggledCanvas.addUIComponent(container);
    }

    @Override
    protected void handleInput() {

    }

}
