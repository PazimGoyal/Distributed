import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class TorontoServer {
    public static void main(String args[]) {
        try {

            startRegistry(8080);
            ServerImplementationToronto exportedObj = new ServerImplementationToronto();
            Naming.rebind("rmi://localhost:" + 8080 + "/toronto", exportedObj);
            System.out.println("Hello Server ready.");
            startUDPServer(8086);
        } catch (Exception re) {
            System.out.println("Exception in HelloServer.main: " + re);
        }
    }

    private static void startUDPServer(int portNumber) {
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(portNumber);
            byte[] buffer = new byte[10000];// to stored the received data from
            // the client.
            System.out.println("Toronto UDP Server Started on 8086............");
            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);

                aSocket.receive(request);

                System.out.println("Request received from client: " + new String(request.getData()));
                /*TODO : Write appropriate message upon receive*/
                String val = "Message received sending response";
                DatagramPacket reply = new DatagramPacket(val.getBytes(), val.length(), request.getAddress(),
                        request.getPort());// reply packet ready

                aSocket.send(reply);// reply sent
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null)
                aSocket.close();
        }
    }


    private static void startRegistry(int RMIPortNum) throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(RMIPortNum);
            registry.list();
        } catch (RemoteException e) {
            System.out.println("RMI registry cannot be located at port " + RMIPortNum);
            Registry registry = LocateRegistry.createRegistry(RMIPortNum);
            System.out.println("RMI registry created at port " + RMIPortNum);
        }
    }
}