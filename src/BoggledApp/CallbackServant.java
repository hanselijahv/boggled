//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package BoggledApp;

import org.omg.CORBA.ORB;

public class CallbackServant extends CallbackPOA {
	private ORB orb;

	public CallbackServant() {
	}

	public void setORB(ORB orb_val) {
		this.orb = orb_val;
	}

	public void callback(String notification) {
		System.out.println(notification);
	}

	@Override
	public void message(String message) {
		System.out.println(message);
	}
}
