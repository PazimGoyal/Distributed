import sun.rmi.runtime.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.*;
import java.text.SimpleDateFormat;
import java.util.*;

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
        /*try {
            MultiThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */while (true) {
            try {
                System.out.println("Enter 1 to enter id or 2 to exit");
                String opt = obj.nextLine();
                if (opt.equals("1") || opt == "1") {

                    System.out.println("Enter ID");
                    id = obj.nextLine().toUpperCase();
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
        try {
            switch (ans) {
                case 1:

                    type = getType();
                    uniqueid = getEventID();
                    booking = getBooking();
                    LogData("Manager trying to add event",id);
                    String reply = interFace.addEvent(uniqueid, type, booking).trim();
                    System.out.println(reply);
                    LogData("GOT REPLY",id);
                    break;

                case 2:
                    type = getType();
                    uniqueid = getEventID();
                    reply = interFace.removeEvent(uniqueid, type).trim();
                    if (reply.trim().equals("EVENT ID REMOVED SUCCESSFULLY")) {
                        for (int i = 0; i < hashMap.size(); i++) {
                            HashSet<String> tempHash = hashMap.get(i);
                            if (tempHash.contains(type + "||" + uniqueid)) {
                                tempHash.remove(type + "||" + uniqueid);
                            }
                        }

                    }
                    System.out.println(reply);
                    break;

                case 3:
                    type = getType();
                    System.out.println(interFace.listEventAvailability(type).trim());
                    break;
                case 4:
                    int temp = 0;
                    System.out.println(hashMap);
                    if (!idTaken)
                        id = getCustomerID();
                    type = getType();
                    uniqueid = getEventID();
                    LogData("ForCustomer" + id+"CUSTUMER TRYING TO BOOK EVENT", id);

                    if (id.substring(0, 3).equals(uniqueid.substring(0, 3))) {
                        temp = 0;
                    } else {

                        if (hashMap.containsKey(id)) {
                            HashSet<String> hashSet = hashMap.get(id);
                            ArrayList<String> abc = new ArrayList(hashSet);
                            for (int i = 0; i < hashSet.size(); i++) {
                                String eid = abc.get(i);
                                String sub = eid.split("\\|\\|")[1].substring(0, 3);
                                if (!id.substring(0, 3).equals(sub)) {
                                    String usub = uniqueid.substring(6, 8);
                                    String eidsub = eid.split("\\|\\|")[1].substring(6, 8);
                                    if (usub.equals(eidsub))
                                        temp++;
                                }
                            }
                        } else {
                            temp = 0;
                        }
                    }


                    if (temp < 3) {
                        reply = interFace.bookEvent(id, uniqueid, type).trim();
                        if (reply.equals("Successfully Booked")) {
                            LogData("Successfull Event BOOKED",id);
                            if (hashMap.containsKey(id)) {
                                hashMap.get(id).add(type + "||" + uniqueid);
                            } else {
                                HashSet<String> hashSet = new HashSet<>();
                                hashSet.add(type + "||" + uniqueid);
                                hashMap.put(id, hashSet);
                            }
                        }
                        System.out.println(reply);
                    } else {
                        LogData("CANNOT BOOK EVENT AS ALREADY HAVE MORE THEN # EVENTS PER MONTH",id);
                        System.out.println("CUSTOMER HAVE MORE THEN THREE BOOKINGS FOR THAT MONTH");
                    }
                    break;
                case 5:
                    if (!idTaken)
                        id = getCustomerID();
                    type = getType();
                    uniqueid = getEventID();
                    reply = interFace.cancelEvent(id, uniqueid, type).trim();
                    if (reply.equals("Event Canceled Successfully")) {
                        LogData("SUCCESS",id);


                        hashMap.get(id).remove(type + "||" + uniqueid);
                    }
                    System.out.println(reply);
                    LogData("GOT REPLY"+reply,id);


                    break;
                case 6:
                    if (!idTaken)
                        id = getCustomerID();
                    reply = interFace.getBookingSchedule(id).trim();
                    LogData(reply,id);
     reply.replaceAll("SEMINAR\\|\\|"," ").replaceAll("TRADE SHOW\\|\\|"," ").replaceAll("CONFRENCE\\|\\|"," ");
                    System.out.println(reply);
                    break;

                default:
                    break;

            }
        } catch (Exception e) {
            System.out.println("Error");
            LogData("ERROR",id);
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
        if (eventIdCheck(uniqueid))
            return uniqueid.toUpperCase();
        else {
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
        if (idCheck(uniqueid))
            return uniqueid.toUpperCase();
        else {
            System.out.println("INVALID ID TRY AGAIN");
            obj.nextLine();
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
            vals[2] = id.substring(4, 8);
        } else {
            System.out.println("Invalid Id");
        }
        return vals;

    }

    public static boolean idCheck(String id) {
        boolean ans = false;
        try {
            String city = id.substring(0, 3).toUpperCase().trim();
            String mc = id.substring(3, 4).trim().toUpperCase();
            String uid = id.substring(4).trim();
            if (city.equals("TOR") || city.equals("MTL") || city.equals("OTW"))
                ans = true;
            else {
                ans = false;
                return ans;
            }
            if (mc.equals("M") || mc.equals("C"))
                ans = true;
            else {
                return false;
            }
            if (uid.length() > 4 || uid.length() < 4)
                return false;
            else
                ans = true;
            int a = Integer.parseInt(uid);

        } catch (Exception e) {
            ans = false;
        }
        return ans;
    }

    public static boolean eventIdCheck(String id) {
        boolean ans = false;
        try {
            String city = id.substring(0, 3).toUpperCase().trim();
            String mc = id.substring(3, 4).trim().toUpperCase();
            String uid = id.substring(4).trim();
            if (city.equals("TOR") || city.equals("MTL") || city.equals("OTW"))
                ans = true;
            else {
                ans = false;
                return ans;
            }
            if (mc.equals("M") || mc.equals("E") || mc.equals("A"))
                ans = true;
            else {
                return false;
            }
            if (uid.length() > 6 || uid.length() < 6)
                return false;
            else
                ans = true;
            int a = Integer.parseInt(uid);

        } catch (Exception e) {
            ans = false;
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


    public static void LogData(String value, String name) {
        Date date = new Date(); // this object contains the current date value
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        File log = new File(name + ".txt");
        try {
            if (!log.exists()) {
                System.out.println("We had to make a new file.");
                log.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(log, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(date + " : " + value + "\n");
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("COULD NOT LOG!!");
        }

    }

    public static void MultiThread() throws Exception {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(TorInterface.bookEvent("TORC1000", "TORA060619", "SEMINAR"));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(MtlInterface.bookEvent("MTLC1000", "TORA060619", "SEMINAR"));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
            Thread thread3 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(TorInterface.bookEvent("TORC1001", "TORA060619", "SEMINAR"));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
            Thread thread4 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(OtwInterface.bookEvent("OTWC1000", "TORA060619", "SEMINAR"));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
            Thread thread5 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(MtlInterface.bookEvent("MTLC1000", "TORA060619", "SEMINAR"));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
            thread2.start();
            thread3.start();
            thread4.start();
            thread5.start();


        } catch (Exception e) {
        }
    }

}
