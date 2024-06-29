package net.team6.boggled.run;

import BoggledApp.*;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import terminalclient.CallbackServant;

public class Connect {

    public static Boggled boggledImpl;
    public static Callback cref;
    public static String username;

    public static void createConnection(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name = "Boggled";
            boggledImpl = BoggledHelper.narrow(ncRef.resolve_str(name));
            CallbackServant ciaoCallbackImpl = new CallbackServant();
            ciaoCallbackImpl.setORB(orb);
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(ciaoCallbackImpl);
            cref = CallbackHelper.narrow(ref);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
