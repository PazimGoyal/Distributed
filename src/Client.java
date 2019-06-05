import java.io.*;
import java.net.MalformedURLException;
import java.rmi.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Spliterator;

public class Client {
    static HashMap<String, String> hashMap = new HashMap<>();
    static ManagerInterface TorInterface;
    static ManagerInterface MtlInterface;
    static ManagerInterface OtwInterface;
    static String id = "";
    static boolean idtaken = false;
    static Scanner obj;
    static ManagerInterface interFace;

    public static void main(String args[]) {
        obj = new Scanner(System.in);
        start();
        while (true) {
            try {
                System.out.println("Enter 1 to enter id or 2 to exit");
                String opt = obj.nextLine();
                if (opt.equals("1") || opt == "1") {
                    System.out.println("Enter ID");
                    id = obj.nextLine();
                    String[] vals = split(id);
                    interFace = gettype(vals[0]);
                    if (vals[1] == "M" || vals[1].equals("M")) {
                        idtaken = false;
                        System.out.println("SELECT 1 to 6\n1. Add Event\n2. Remove Event\n3. List Event\n4. Book Event\n5.EventList Bookings\n6.Cancel ");
                        int ans = obj.nextInt();
                        options(ans);
                    } else {
                        idtaken = true;
                        System.out.println("SELECT 1 to 3\n1. Book Event\n2.Cancel Event\n3.List Bookings");
                        int ans = obj.nextInt();
                        options(ans + 3);
                    }
                } else if (opt.equals("2") || opt == "2") {
                    break;
                } else {
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void options(int ans) throws Exception {
        String type = "", uniqueid = "";
        int booking;
        switch (ans) {
            case 1:
                type = getType();
                uniqueid = getEventID();
                booking = getBooking();
                String reply = interFace.addEvent(uniqueid, type, booking);
                System.out.println(reply);
                System.out.println(interFace.getHashMap());
                break;

            case 2:
                type = getType();
                uniqueid = getEventID();
                reply = interFace.removeEvent(uniqueid, type);
                System.out.println(interFace.getHashMap());
                System.out.println(reply);
                break;

            case 3:
                type = getType();
                System.out.println(interFace.listEventAvailability(type));
                break;
            case 4:
                if (!idtaken)
                    id = getCustomerID();
                type = getType();
                uniqueid = getEventID();
                reply = interFace.bookEvent(id, uniqueid, type);
                if (reply.equals("Successfully Booked")) {
                    hashMap.put(id, uniqueid);
                }
                System.out.println(reply);
                break;
            case 5:

                if (!idtaken)
                    id = getCustomerID();
                reply = interFace.getBookingSchedule(id);
                System.out.println(reply);
                break;
            case 6:

                break;

            default:
                break;
        }
    }


    public static String getType() {
        String type = "";
        System.out.println("Select Event type :- \n1. Seminar\n2.Trade Show\n3.Confrence");
        int typ = obj.nextInt();
        if (typ == 1)
            type = "SEMINAR";
        else if (typ == 2)
            type = "Trade Show";
        else if (typ == 3)
            type = "Confrence";
        else {
            System.out.println("Invalid Option...Try Again");
            getType();
        }
        return type.toUpperCase();
    }

    public static String getEventID() {
        String uniqueid = "";
        System.out.println("ENTER EVENT ID e.g MTLA100919 :- ");
        obj.nextLine();
        uniqueid = obj.nextLine();
        return uniqueid.toUpperCase();
    }

    public static String getCustomerID() {
        String uniqueid = "";
        System.out.println("ENTER CUSTOMER ID e.g MTLC1234 :- ");
        obj.nextLine();
        uniqueid = obj.nextLine();
        return uniqueid;
    }

    public static Integer getBooking() {
        int booking = 0;
        System.out.println("Enter Capacity");
        booking = obj.nextInt();

        return booking;
    }


    public static ManagerInterface gettype(String abc) {
        if (abc.equals("TOR"))
            return TorInterface;
        else if (abc.equals("MTL"))
            return MtlInterface;
        else
            return OtwInterface;
    }

    public static void TorServer() throws Exception {
        String registryURL = "rmi://localhost:" + 8080 + "/toronto";
        TorInterface = (ManagerInterface) Naming.lookup(registryURL);
        System.out.println("Toronto Server Started");

    }

    public static void MtlServer() throws Exception {
        String registryURL = "rmi://localhost:" + 8082 + "/montreal";
        MtlInterface = (ManagerInterface) Naming.lookup(registryURL);
        System.out.println("Montreal Server Started");
    }

    public static void OtwServer() throws Exception {
        String registryURL = "rmi://localhost:" + 8081 + "/ottawa";
        OtwInterface = (ManagerInterface) Naming.lookup(registryURL);
        System.out.println("Ottawa Server Started");
    }

    public static String[] split(String id) {
        String vals[] = new String[3];

        if (0 < id.length() && id.length() < 9) {
            vals[0] = id.substring(0, 3).toUpperCase();
            vals[1] = id.substring(3, 4).toUpperCase();
            System.out.println(vals[1]);
            vals[2] = id.substring(4, 8);
        } else {
            System.out.println("Invalid Id");
        }
        return vals;

    }


    public static void start() {

        try {
            TorServer();
            //          MtlServer();
//            OtwServer();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}
