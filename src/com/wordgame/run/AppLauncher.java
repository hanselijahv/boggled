package com.wordgame.run;


import com.wordgame.boggled.game.Game;
import com.wordgame.boggled.game.GameLoop;


public class AppLauncher {
    public static void main(String[] args) {

        new Thread(new GameLoop(new Game(1280, 720))).start();
    }
}
