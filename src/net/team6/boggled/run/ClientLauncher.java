package net.team6.boggled.run;


import BoggledApp.Boggled;
import BoggledApp.BoggledHelper;
import net.team6.boggled.user.client.game.Game;
import net.team6.boggled.common.db.DatabaseConnector;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

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
        game.start();
        DatabaseConnector.getInstance();

        try {
            ORB orb = ORB.init(args, null);

            Object objRef =
                    orb.resolve_initial_references("NameService");

            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            String name = "Boggled";
            Boggled boggledImpl = BoggledHelper.narrow(ncRef.resolve_str(name));

            System.out.println
                    ("Obtained a handle on server object: "
                            + boggledImpl);

        } catch (Exception e) {
            System.out.println("ERROR : " + e);
            e.printStackTrace(System.out);
        }


    }
}
