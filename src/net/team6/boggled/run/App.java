package net.team6.boggled.run;


import net.team6.boggled.client.game.Game;
import net.team6.boggled.common.db.DatabaseConnector;

import java.applet.Applet;


public class App extends Applet {
    private static final Game game = new Game(1280, 720);

    public static void main(String[] args) {
        game.start();
        DatabaseConnector.getInstance();
    }
}
