import java.rmi.*;
import java.rmi.server.*;
import java.util.HashMap;

public class ServerImplementationMontreal extends UnicastRemoteObject implements ManagerInterface {
    public static HashMap<String, HashMap<String, Integer>> hashMap = new HashMap<>();

    public ServerImplementationMontreal() throws RemoteException {

        super();

        HashMap<String, Integer> hashMap1 = new HashMap<>();
        hashMap1.put("MTLM1234", 5);
        hashMap1.put("MTLE2134", 3);
        hashMap1.put("MTLA3234", 5);
        hashMap1.put("MTLM5934", 8);
        hashMap1.put("MTLE7254", 1);
        hashMap.put("Confrence", hashMap1);
        hashMap.put("Trade Show", hashMap1);
        hashMap.put("Seminar", hashMap1);
        hashMap.put("Confrence", hashMap1);
    }


    public String bookEvent(String customerID, String eventID, String eventType) throws RemoteException {
        return "working well";
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
        System.out.println(hashMap.values());
        return hashMap;
    }

    @Override
    public String getBookingSchedule(String customerID) throws RemoteException {
        return "";
    }
}