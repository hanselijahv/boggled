package game;

import input.Input;
import state.State;
public class Game {
    public static int TILE_SIZE = 16;

    private Input input;
    private State state;

    public Game(int width, int height) {
        input = new Input();

    }
}
