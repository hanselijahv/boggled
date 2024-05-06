package BoggledApp;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import java.util.Properties;

public class BoggledClient {

    static Boggled boggledImpl;

    public static void main(String args[]) {
        try {
            // create and initialize the ORB
            ORB orb = ORB.init(args, null);

            // get the root naming context
            Object objRef =
                    orb.resolve_initial_references("NameService");

            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            String name = "Boggled";
            boggledImpl = BoggledHelper.narrow(ncRef.resolve_str(name));

            System.out.println
                    ("Obtained a handle on server object: "
                            + boggledImpl);


        } catch (Exception e) {
            System.out.println("ERROR : " + e);
            e.printStackTrace(System.out);
        }
    } //end main
}
