import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ServerImplementationOttawa extends UnicastRemoteObject implements ManagerInterface {
    public static HashMap<String, HashMap<String, Integer>> hashMap = new HashMap<>();

    public ServerImplementationOttawa() throws RemoteException {

        super();

        HashMap<String, Integer> hashMap1 = new HashMap<>();
        hashMap1.put("OTWM1234", 5);
        hashMap1.put("OTWE2134", 4);
        hashMap1.put("OTWA3234", 3);
        hashMap1.put("OTWM5934", 2);
        hashMap1.put("OTWE7254", 1);

        hashMap.put("Confrence", hashMap1);
        hashMap.put("Trade Show", hashMap1);
        hashMap.put("Seminar", hashMap1);
        hashMap.put("Confrence", hashMap1);
    }


    public String bookEvent(String customerID, String eventID, String eventType) throws RemoteException {
        return "";
    }

    public String removeEvent(String eventID, String eventType) throws RemoteException {
        return "";
    }

    public String listEventAvailability(String eventType) throws RemoteException {
        return "";

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

    @Override
    public String getBookingSchedule(String customerID) throws RemoteException {
        return null;
    }
}