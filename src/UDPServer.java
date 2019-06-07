import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {

    public static void main(String[] args) {
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(8080);
            byte[] buffer = new byte[1000];// to stored the received data from
            // the client.
            System.out.println("Server Started............");
            while (true) {// non-terminating loop as the server is always in listening mode.
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);

                // Server waits for the request to come
                aSocket.receive(request);// request received

                System.out.println("Request received from client: " + new String(request.getData()));

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
}
