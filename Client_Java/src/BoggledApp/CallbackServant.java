package BoggledApp;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import BoggledApp.CallbackPOA;
import org.omg.CORBA.ORB;

public class CallbackServant extends CallbackPOA {
	private ORB orb;

	public CallbackServant() {
	}

	public void setORB(ORB orb_val) {
		this.orb = orb_val;
	}

	public void message(String notification) {
		System.out.println(notification);
	}
}
