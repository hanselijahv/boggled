package Client_Java.net.team6.boggled.client.state.menu;


import Client_Java.net.team6.boggled.client.game.settings.GameSettings;
import Client_Java.net.team6.boggled.client.gui.container.BoggledContainer;
import Client_Java.net.team6.boggled.client.input.Input;
import Client_Java.net.team6.boggled.client.state.State;
import Client_Java.net.team6.boggled.client.state.menu.elements.BoggledMainMenu;
import Client_Java.net.team6.boggled.common.core.Size;

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
