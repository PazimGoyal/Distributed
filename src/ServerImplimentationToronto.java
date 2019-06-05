import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Hashtable;

public class ServerImplimentationToronto extends UnicastRemoteObject implements ManagerInterface {
    public static HashMap<String, HashMap<String, Integer>> hashMap = new HashMap<>();

    public ServerImplimentationToronto() throws RemoteException {

        super();

        hashMap.put("CONFRENCE", new HashMap<>());
        hashMap.put("TRADE SHOW", new HashMap<>());
        hashMap.put("SEMINAR", new HashMap<>());
        hashMap.put("CONFRENCE", new HashMap<>());
    }


    public boolean bookEvent(String customerID, String eventID, String eventType) throws RemoteException {
        return false;
    }

    public String removeEvent(String eventID, String eventType) throws RemoteException {
        HashMap<String, Integer> temp;
        String reply = "";

        boolean exists = hashMap.containsKey(eventType);
        if (exists) {
            temp = hashMap.get(eventType);
            if (temp.containsKey(eventID)) {
                temp.remove(eventID);
                reply = "EVENT ID REMOVED SUCCESSFULLY";
            } else {
                reply = "No SUCH EVENT ID FOUND";
            }

        } else {
            reply = "NO SUCH EVENT TYPE FOUND";
        }
        return reply;


    }

    public void listEventAvailability(String eventType) throws RemoteException {

    }

    public void addEvent(String eventID, String eventType, int bookingCapacity) throws RemoteException {
        boolean exists = hashMap.containsKey(eventType);
        HashMap<String, Integer> temp;
        if (exists) {
            temp = hashMap.get(eventType);
            if (temp.containsKey(eventID)) {
                temp.remove(eventID);
                temp.put(eventID, bookingCapacity);
            } else {
                temp.put(eventID, bookingCapacity);

            }

        } else {

            temp = new HashMap<>();
            temp.put(eventID, bookingCapacity);
            hashMap.put(eventType, temp);
        }


    }

    @Override
    public HashMap<String, HashMap<String, Integer>> getHashMap() throws RemoteException {
        System.out.println(hashMap);
        return hashMap;
    }
}