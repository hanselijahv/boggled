package net.team6.boggled.run;


import net.team6.boggled.client.game.Game;

import java.applet.Applet;
import java.awt.*;
import java.io.IOException;


public class ClientLauncher extends Applet {
    private static final Game game;

    static {
        try {
            game = new Game(1280, 720);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        try{
            Connect.createConnection(args);
            game.start();
        } catch (Exception e) {
            System.out.println("ERROR : " + e);
            e.printStackTrace(System.out);
        }
    }
}
