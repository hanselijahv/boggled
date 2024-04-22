package temp;

import temp.game.Game;
import temp.game.GameLoop;

public class Launcher {
    public static void main(String[] args) {
        new Thread(new GameLoop(new Game(1280, 720))).start();
    }
}
