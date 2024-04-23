package boggled.game;

import boggled.core.Size;
import boggled.display.Display;
import boggled.game.settings.GameSettings;
import boggled.input.Input;
import boggled.state.State;
import boggled.state.menu.MenuState;

public class Game {

    private State state;
    private final Display display;
    private final GameSettings gameSettings;

    public Game(int width, int height) {
        Input input = new Input();
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
