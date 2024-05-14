package net.team6.boggled.run;


import BoggledApp.Boggled;
import BoggledApp.BoggledHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public class Connect {

    public static Boggled boggledImpl;

    public static String sessionID;

    public static void createConnection(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name = "Boggled";
            boggledImpl = BoggledHelper.narrow(ncRef.resolve_str(name));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
