package net.team6.boggled.run;


import net.team6.boggled.user.client.game.Game;
import net.team6.boggled.common.db.DatabaseConnector;

import java.applet.Applet;
import java.awt.*;
import java.io.IOException;


public class ClientLauncher extends Applet {
    private static final Game game;

    static {
        try {
            game = new Game(1280, 720);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        game.start();
        DatabaseConnector.getInstance();
    }
}
