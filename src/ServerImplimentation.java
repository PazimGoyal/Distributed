import java.rmi.*;
import java.rmi.server.*;
import java.util.HashMap;

public class ServerImplimentation extends UnicastRemoteObject implements ManagerInterface {
	public static HashMap<String, HashMap<String, String>> hashMap = new HashMap<>();

	public ServerImplimentation() throws RemoteException {

		super();

		hashMap.put("Confrence",new HashMap<>());
		hashMap.put("TradeShow",  new HashMap<>());
		hashMap.put("Confrencg", new HashMap<>());
		hashMap.put("Confrench", new HashMap<>());
	}

	public int add(int a, int b) throws RemoteException {
		return a + b;
	}

	public void addEvent(String eventID, String eventType, int bookingCapacity) throws RemoteException {
		boolean exists = hashMap.containsKey(eventType);
		HashMap<String, String> temp;
		if (exists) {
			System.out.println("Exists");
			temp = hashMap.get(eventType);
			if (temp.containsKey(eventID)) {
				temp.remove(eventID);
				temp.put(eventID, "" + bookingCapacity);
			} else {
				temp.put(eventID, "" + bookingCapacity);

			}

		} else {

			temp = new HashMap<>();
			temp.put(eventID, ""+bookingCapacity);
			hashMap.put(eventType, temp);
		}

				System.out.println("Added" + eventID);

	}

	@Override
	public HashMap<String, HashMap<String, String>> getHashMap() throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("------->" + hashMap.keySet());
		return hashMap;
	}
}