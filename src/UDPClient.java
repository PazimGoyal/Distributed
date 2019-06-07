import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient {
    public static void main(String[] args) {
        DatagramSocket aSocket = null;
        try{
            System.out.println("CLIENT.Client Started........");
            aSocket = new DatagramSocket(); //reference of the original socket
            byte [] message = "Hello".getBytes(); //message to be passed is stored in byte array
//			System.out.println("InetAddress.getLocalHost() = " + InetAddress.getLocalHost());//know the name and IP address of your host

            InetAddress aHost = InetAddress.getByName("localhost"); //Host name is specified and the IP address of server host is calculated using DNS.
//			InetAddress aHost = InetAddress.getByName("192.168.2.18");

            int serverPort = 8080;//agreed upon port
            DatagramPacket request = new DatagramPacket(message, "Hello".length(), aHost, serverPort);//request packet ready
            aSocket.send(request);//request sent out
            System.out.println("Request message sent from the client is : "+ new String(request.getData()));

            byte [] buffer = new byte[1000];//to store the received data, it will be populated by what receive method returns
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);//reply packet ready but not populated.

            //CLIENT.Client waits until the reply is received-----------------------------------------------------------------------
            aSocket.receive(reply);//reply received and will populate reply packet now.
            System.out.println("Reply received from the server is: "+ new String(reply.getData()));//print reply message after converting it to a string from bytes
        }
        catch(SocketException e){
            System.out.println("Socket: "+e.getMessage());
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("IO: "+e.getMessage());
        }
        finally{
            if(aSocket != null) aSocket.close();//now all resources used by the socket are returned to the OS, so that there is no
            //resource leakage, therefore, close the socket after it's use is completed to release resources.
        }
    }
}
