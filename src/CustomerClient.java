import java.io.*;
import java.net.MalformedURLException;
import java.rmi.*;
import java.util.Scanner;
import java.util.Spliterator;

public class CustomerClient {
	static ManagerInterface TorInterface;
	static ManagerInterface MtlInterface;
	static ManagerInterface OtwInterface;
	static String city = "";
	static String cm = "";
	static int uid = 0;

	public static void main(String args[]) {
		Scanner obj = new Scanner(System.in);
		start();
		while(true) {
			System.out.println("Enter 1 to enter id or 2 to exit");
			String opt = obj.nextLine();
			if(opt.equals("1")||opt=="1") {
				System.out.println("Enter ID");
				String id=obj.nextLine();
				String [] vals=split(id);

				ManagerInterface interFace= gettype(vals[0]);
				if(vals[1]=="M") {
					System.out.println("SELECT 1 to 6\n1. Add Event\n2.Delete");
				}
				else {
					System.out.println("SELECT 1 to 3\n1. Add Event\n2.Delete");
				}
				int ans=obj.nextInt();
				try {
					interFace.addEvent("TOR1234", "Confrence", 4);
					interFace.addEvent("MTL1234", "Trade Show", 8);
					interFace.addEvent("OTW1234", "Trade Show", 2);
					interFace.addEvent("TOR1234", "Seminar", 8);
					interFace.getHashMap();

				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				
				
				
				
			}else {
				break;
			}
			
			


		}
	}
	
	public static void options(int ans) {
		switch (ans) {
		case 1:
			
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
