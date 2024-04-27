package net.team6.boggled.run;


import net.team6.boggled.client.game.Game;
import net.team6.boggled.client.game.GameLoop;


public class App {
    public static void main(String[] args) {
        new Thread(new GameLoop(new Game(1280, 720))).start();
    }
}
