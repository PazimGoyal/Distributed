import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

public class ServerImplimentationToronto extends UnicastRemoteObject implements ManagerInterface {
    public static HashMap<String, HashMap<String, Integer>> hashMap = new HashMap<>();
    public static HashMap<String, HashSet<String>> customerBooking = new HashMap<>();

    public ServerImplimentationToronto() throws RemoteException {

        super();

        hashMap.put("CONFRENCE", new HashMap<>());
        hashMap.put("TRADE SHOW", new HashMap<>());
        hashMap.put("SEMINAR", new HashMap<>());
        hashMap.put("CONFRENCE", new HashMap<>());
        hashMap.get("CONFRENCE").put("TORA123412", 5);
        hashMap.get("CONFRENCE").put("TORA123412", 5);
        hashMap.get("SEMINAR").put("TORE999999", 5);
        hashMap.get("TRADE SHOW").put("TORE989898", 5);
        hashMap.get("TRADE SHOW").put("TORM121219", 5);
        customerBooking.put("TORC1234", new HashSet<>());
        customerBooking.get("TORC1234").add("TORA123412");


    }


    public String bookEvent(String customerID, String eventID, String eventType) throws RemoteException {
        String reply = "";
        if (customerBooking.containsKey(customerID) && (customerBooking.get(customerID).contains(eventID))) {
            reply = "Event Already Booked for Customer";
        } else {
            HashMap<String, Integer> temp;
            boolean exists = hashMap.containsKey(eventType);
            if (exists) {
                temp = hashMap.get(eventType);
                if (temp.containsKey(eventID)) {
                    if (temp.get(eventID) > 0) {
                        temp.put(eventID, temp.get(eventID) - 1);
                        if (customerBooking.containsKey(customerID))
                            customerBooking.get(customerID).add(eventID);
                        else {
                            customerBooking.put(customerID, new HashSet<>());
                            customerBooking.get(customerID).add(eventID);
                        }
                        reply = "Successfully Booked";
                    } else {
                        reply = "CAPACITY FULL";
                    }

                } else {

                    reply = "No SUCH EVENT ID FOUND";
                }

            }
        }
        System.out.println(customerBooking);
        return reply;

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

    public String listEventAvailability(String eventType) throws RemoteException {

        HashMap<String, Integer> temp;
        String reply = eventType;
        boolean exists = hashMap.containsKey(eventType);
        if (exists) {
            temp = hashMap.get(eventType);
            reply = reply + " :- " + temp.toString().substring(1, temp.toString().length() - 1);

        } else {
            reply = "NO SUCH EVENT TYPE FOUND";
        }
        return reply;


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

    public String getBookingSchedule(String customerId) throws RemoteException {
        String reply = "";
        if (customerBooking.containsKey(customerId)) {
            reply = customerBooking.get(customerId).toString();
        } else {
            reply = "NO SUCH CUSTOMER FOUND";
        }


        return reply;
    }
}