import java.io.*;
import java.rmi.*;

public class ManagerClient {

   public static void main(String args[]) {
      try {

         String registryURL = "rmi://localhost:" + 8080 + "/hello";  
         ManagerInterface h =(ManagerInterface)Naming.lookup(registryURL);
         System.out.println("Lookup completed " );
         int message = h.add(6,8);
         System.out.println("HelloClient: " + message);
      } catch (Exception e) {
         System.out.println("Exception in HelloClient: " + e);
      } 
   } }

//"rmi://localhost:" + 8080 + 
//"rmi://localhost" + ":" + 8080 + 