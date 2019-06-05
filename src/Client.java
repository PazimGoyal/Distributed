import java.io.*;
import java.net.MalformedURLException;
import java.rmi.*;
import java.util.Scanner;
import java.util.Spliterator;

public class Client {
	static ManagerInterface TorInterface;
	static ManagerInterface MtlInterface;
	static ManagerInterface OtwInterface;
	static String city = "";
	static String cm = "";
	static int uid = 0;
	static Scanner obj;
	static ManagerInterface interFace;

	public static void main(String args[]) {
		obj = new Scanner(System.in);
		start();
		while(true) {
			System.out.println("Enter 1 to enter id or 2 to exit");
			String opt = obj.nextLine();
			if(opt.equals("1")||opt=="1") {
				System.out.println("Enter ID");
				String id=obj.nextLine();
				String [] vals=split(id);

				 interFace= gettype(vals[0]);
				if(vals[1]=="M"||vals[1].equals("M")) {
					System.out.println("SELECT 1 to 6\n1. Add Event\n2. Remove Event\n3. List Event");
					int ans=obj.nextInt();
					options(ans);
				}
				else {
					System.out.println("SELECT 1 to 3\n1. Add Event\n2.Delete");
				}
				int ans=obj.nextInt();
			}else if (opt.equals("2")||opt=="2") {
				break;
			}
			else {continue;}
		}
	}
	
	public static void options(int ans) {
		String type="",uniqueid="";
		int booking;
		switch (ans) {
		case 1:
			System.out.println("Select Event type :- \n1. Seminar\n2.Trade Show\n3.Confrence");
			int typ=obj.nextInt();
			if(typ==1)
				type="SEMINAR";
			else if (typ==2)
				type="Trade Show";
			else if (typ==3)
				type="Confrence";
			else{
				System.out.println("Invalid Option...Try Again");
				options(1);
			}
			System.out.println("ENTER EVENT ID e.g MTLA100919 :- ");
			obj.nextLine();
			uniqueid=obj.nextLine();
			System.out.println("Enter Capacity");
			booking=obj.nextInt();
			try {
				interFace.addEvent(uniqueid,type,booking);
				interFace.getHashMap();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

			case 2:

				break;
		default:
			break;
		}
	}
	
	public static ManagerInterface gettype (String abc) {
		if(abc.equals("TOR"))
		return TorInterface;
		else if(abc.equals("MTL"))
			return MtlInterface;
		else
			return OtwInterface;
	}

	public static void TorServer() throws Exception {
		String registryURL = "rmi://localhost:" + 8080 + "/toronto";
		TorInterface = (ManagerInterface) Naming.lookup(registryURL);
		System.out.println("Lookup completed ");

	}

	public static void MtlServer() throws Exception {
		String registryURL = "rmi://localhost:" + 8082 + "/montreal";
		MtlInterface = (ManagerInterface) Naming.lookup(registryURL);
		System.out.println("Lookup completed ");
	}

	public static void OtwServer() throws Exception {
		String registryURL = "rmi://localhost:" + 8081 + "/ottawa";
		OtwInterface = (ManagerInterface) Naming.lookup(registryURL);
		System.out.println("Lookup completed ");
	}

	public static String[] split(String id) {
		String vals[] = new String[3];

		if (id.length() > 0) {
			vals[0] = id.substring(0, 3).toUpperCase();
			vals[1] = id.substring(3, 4).toUpperCase();
			System.out.println(vals[1]);
			vals[2] = id.substring(4, 8);
		}
		return vals;

	}
	
	
	public static  void start() {
		
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
