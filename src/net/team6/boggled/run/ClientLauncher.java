package net.team6.boggled.run;


import BoggledApp.Boggled;
import BoggledApp.BoggledHelper;
import net.team6.boggled.client.game.Game;
import net.team6.boggled.common.db.DatabaseConnector;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import java.applet.Applet;
import java.awt.*;
import java.io.IOException;


public class ClientLauncher extends Applet {

    public static void main(String[] args) {
        try {
            Boggled boggledImpl = Connect.createConnection(args);
            startGame(boggledImpl);
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }
    }

    private static void startGame(Boggled boggledImpl) {
        Game game;
        try {
            game = new Game(1280, 720, boggledImpl);
            game.start();
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
