package BoggledApp;

import BoggledApp.CallbackPOA;
import org.omg.CORBA.ORB;

public class CallbackServant extends CallbackPOA {

    private ORB orb;

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    public void callback(String notification) {
        System.out.println(notification);
    }
}
