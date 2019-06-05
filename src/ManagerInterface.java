import java.rmi.*;
import java.util.HashMap;

public interface ManagerInterface extends Remote {

    public String managerID = "";
    public String managerCity = "";


    public boolean bookEvent(String customerID, String eventID, String eventType) throws RemoteException;

    public String removeEvent(String eventID, String eventType) throws RemoteException;

    public void listEventAvailability(String eventType) throws RemoteException;

    public void addEvent(String eventID, String eventType, int bookingCapacity) throws RemoteException;

    public HashMap<String, HashMap<String, Integer>> getHashMap() throws RemoteException;

}

