import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ServerImplimentationToronto extends UnicastRemoteObject implements ManagerInterface {
	public static HashMap<String, HashMap<String, Integer>> hashMap = new HashMap<>();

	public ServerImplimentationToronto() throws RemoteException {

		super();
		HashMap<String,Integer> hashMap1=new HashMap<>();
		hashMap1.put("TORM1234",5);
		hashMap1.put("TORE2134",3);
		hashMap1.put("TORA3234",5);
		hashMap1.put("TORM5934",8);
		hashMap1.put("TORE7254",1);

		hashMap.put("Confrence",hashMap1);
		hashMap.put("Trade Show", hashMap1);
		hashMap.put("Seminar", hashMap1);
		hashMap.put("Confrence", hashMap1);
	}

	public int add(int a, int b) throws RemoteException {
		return a + b;
	}

	public void addEvent(String eventID, String eventType, int bookingCapacity) throws RemoteException {
		boolean exists = hashMap.containsKey(eventType);
		HashMap<String, Integer> temp;
		if (exists) {
			temp = hashMap.get(eventType);
			if (temp.containsKey(eventID)) {
				temp.remove(eventID);
				temp.put(eventID,  bookingCapacity);
			} else {
				temp.put(eventID,  bookingCapacity);

			}

		} else {

			temp = new HashMap<>();
			temp.put(eventID, bookingCapacity);
			hashMap.put(eventType, temp);
		}


	}

	@Override
	public HashMap<String, HashMap<String, Integer>> getHashMap() throws RemoteException {
		System.out.println( hashMap.keySet());
		return hashMap;
	}
}