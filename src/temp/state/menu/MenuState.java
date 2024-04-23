package temp.state.menu;


import temp.core.Size;
import temp.game.settings.GameSettings;
import temp.input.Input;
import temp.state.State;
import temp.state.menu.elements.UIMainMenu;
import temp.ui.UIContainer;

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
