import java.rmi.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Client {
    static HashMap<String, HashSet<String>> hashMap = new HashMap<>();
    static ManagerInterface TorInterface;
    static ManagerInterface MtlInterface;
    static ManagerInterface OtwInterface;
    static String id = "";
    static boolean idTaken = false;
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
                        idTaken = false;
                        System.out.println("SELECT 1 to 6\n1. Add Event\n2. Remove Event\n3. List Event Availability \n4. Book Event\n5.Cancel Event \n6.Get Booking Schedule");
                        int ans = obj.nextInt();
                        options(ans);
                    } else {
                        idTaken = true;
                        System.out.println("SELECT 1 to 3\n1. Book Event\n2.Cancel Event\n3.Get Booking Schedule");
                        int ans = obj.nextInt();
                        options(ans + 3);
                    }
                } else if (opt.equals("2") || opt == "2") {
                    System.exit(0);
                } else {
                    continue;
                }
            } catch (Exception e) {
                System.out.println("Error While Entering ... Try Again");
            }
        }
    }

    public static void options(int ans) throws Exception {
        String type = "", uniqueid = "";
        int booking;
        try{
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
                if (!idTaken)
                    id = getCustomerID();
                type = getType();
                uniqueid = getEventID();
                reply = interFace.bookEvent(id, uniqueid, type);
                if (reply.equals("Successfully Booked")) {
                    if (hashMap.containsKey(id)) {
                        hashMap.get(id).add(type + "||" + uniqueid);
                    } else {
                        HashSet<String> hashSet = new HashSet<>();
                        hashSet.add(type + "||" + uniqueid);
                        hashMap.put(id, hashSet);
                    }
                }
                System.out.println(reply);
                break;
            case 5:
                if (!idTaken)
                    id = getCustomerID();
                type = getType();
                uniqueid = getEventID();
                reply = interFace.cancelEvent(id, uniqueid, type);
                if (reply.equals("Event Canceled Successfully")) {
                    hashMap.get(id).remove(type + "||" + uniqueid);
                }
                System.out.println(reply);


                break;
            case 6:
                if (!idTaken)
                    id = getCustomerID();
                reply = interFace.getBookingSchedule(id);
                System.out.println(reply);
                break;

            default:
                break;

        }
        }catch (Exception e){
            System.out.println("Error");
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
            type = "Conference";
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
        if(eventIdCheck(uniqueid))
           return uniqueid.toUpperCase();
else
        {
            System.out.println("INVALID EVENT ID TRY AGAIN");
            getEventID();
        }
        return "";

    }

    public static String getCustomerID() {
        String uniqueid = "";
        System.out.println("ENTER CUSTOMER ID e.g MTLC1234 :- ");
        obj.nextLine();
        uniqueid = obj.nextLine();
        if(idCheck(uniqueid))
            return uniqueid;
        else
        {
            System.out.println("INVALID ID TRY AGAIN");
            getCustomerID();
        }
        return "";
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

        if (idCheck(id)) {
            vals[0] = id.substring(0, 3).toUpperCase();
            vals[1] = id.substring(3, 4).toUpperCase();
            System.out.println(vals[1]);
            vals[2] = id.substring(4, 8);
        } else {
            System.out.println("Invalid Id");
        }
        return vals;

    }

    public static boolean idCheck(String id){
        boolean ans=false;
        try {
            String city = id.substring(0, 3).toUpperCase().trim();
            String mc = id.substring(3, 4).trim().toUpperCase();
            String uid = id.substring(4, 8).trim();
            if(city.equals("TOR")||city.equals("MTL")||city.equals("OTW"))
                ans=true;
            else {ans=false;
            return ans;}
            if (mc.equals("M")||mc.equals("C"))
                ans=true;
            else{
                return false;
            }
            if(uid.length()>4||uid.length()<4)
                return false;
            else
                ans=true;
            int a=Integer.parseInt(uid);

        }catch (Exception e){
            ans=false;
        }
        return ans;
    }
    public static boolean eventIdCheck(String id){
        boolean ans=false;
        try {
            String city = id.substring(0, 3).toUpperCase().trim();
            String mc = id.substring(3, 4).trim().toUpperCase();
            String uid = id.substring(4, 8).trim();
            if(city.equals("TOR")||city.equals("MTL")||city.equals("OTW"))
                ans=true;
            else {ans=false;
                return ans;}
            if (mc.equals("M")||mc.equals("E")||mc.equals("A"))
                ans=true;
            else{
                return false;
            }
            if(uid.length()>6||uid.length()<6)
                return false;
            else
                ans=true;
            int a=Integer.parseInt(uid);

        }catch (Exception e){
            ans=false;
        }
        return ans;
    }



    public static void start() {

        try {
            TorServer();
            MtlServer();
            OtwServer();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }





}
