package boggled.state.menu;


import boggled.core.Size;
import boggled.game.settings.GameSettings;
import boggled.input.Input;
import boggled.state.State;
import boggled.state.menu.elements.UIMainMenu;
import boggled.ui.UIContainer;

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
