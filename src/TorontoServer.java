import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;

public class TorontoServer {
	public static void main(String args[]) {
		try {

			startRegistry(8080);
			ServerImplimentation exportedObj = new ServerImplimentation();
			Naming.rebind("rmi://localhost:" + 8080 + "/toronto", exportedObj);
			System.out.println("Hello Server ready.");
		} catch (Exception re) {
			System.out.println("Exception in HelloServer.main: " + re);
		}
	}

	private static void startRegistry(int RMIPortNum) throws RemoteException {
		try {
			Registry registry = LocateRegistry.getRegistry(RMIPortNum);
			registry.list();
		} catch (RemoteException e) {
			System.out.println("RMI registry cannot be located at port " + RMIPortNum);
			Registry registry = LocateRegistry.createRegistry(RMIPortNum);
			System.out.println("RMI registry created at port " + RMIPortNum);
		}
	}
}