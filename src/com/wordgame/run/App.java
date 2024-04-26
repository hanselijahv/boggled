package com.wordgame.run;


import com.wordgame.client.game.Game;
import com.wordgame.client.game.GameLoop;


public class App {
    public static void main(String[] args) {
        new Thread(new GameLoop(new Game(1280, 720))).start();
    }
}
