package net.team6.boggled.run;

import BoggledApp.Boggled;
import BoggledApp.BoggledHelper;
import BoggledApp.BoggledImpl;
import net.team6.boggled.user.server.dev.Server;
import net.team6.boggled.common.db.DatabaseConnector;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.applet.Applet;
import java.sql.SQLException;

public class ServerLauncher extends Applet {
    private static final Server server;

    static {
        try {
            server = new Server(1280, 720);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException {
        DatabaseConnector.getInstance();
        server.start();
        try {
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();


            BoggledImpl my = new BoggledImpl();
            my.setORB(orb);

            // get object reference from the servant
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(my);
            Boggled href = BoggledHelper.narrow(ref);

            Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            NameComponent[] path = ncRef.to_name("Boggled");
            ncRef.rebind(path, href);

            System.out.println("Boggled Server Running ...");


            orb.run();


        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }


    }
}
