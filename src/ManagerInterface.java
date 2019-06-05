import java.rmi.*;
import java.util.HashMap;

public interface ManagerInterface extends Remote {
	 
	public String managerID="";
	public String managerCity="";

	public int add(int a, int b) throws java.rmi.RemoteException;
	public void addEvent(String eventID,String eventType,int bookingCapacity) throws RemoteException;
	public HashMap<String,HashMap<String,Integer> >getHashMap() throws RemoteException;
	
}

