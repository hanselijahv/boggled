package temp.game;

import temp.core.Size;
import temp.display.Display;
import temp.game.settings.GameSettings;
import temp.input.Input;
import temp.state.game.GameState;
import temp.state.State;
import temp.state.menu.MenuState;

public class Game {
    public static int TILE_SIZE = 16;

    private Input input;
    private State state;
    private Display display;
    private GameSettings gameSettings;

    public Game(int width, int height) {
        input = new Input();
        gameSettings = new GameSettings(false);
        state = new MenuState(new Size(width, height), input, gameSettings);
        display = new Display(width, height, input, this::resize);

    }

    public void update(){
        state.update(this);
    }

    public void render(){
        display.render(state);
    }

    public GameSettings getSettings() {
        return gameSettings;
    }

    public void enterState(State nextState) {
        state.cleanup();
        state = nextState;
    }

    public void resize(Size size) {
        state.resize(size);
    }
}
