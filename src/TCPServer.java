import java.io.*;
import java.net.*;

import org.json.JSONObject;

class TCPServer
{
   public static void main(String argv[]) throws Exception
      {
         String clientSentence;
         String capitalizedSentence;
         ServerSocket welcomeSocket = new ServerSocket(6789);

         while(true)
         {
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("connection made!");
            
            BufferedReader inFromClient =
               new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientSentence = inFromClient.readLine();
            JSONObject json = new JSONObject(clientSentence);
            
            System.out.println("Received: " + clientSentence);
            for(String key : json.keySet()) {
    			System.out.println(key+": "+json.get(key));
    		}
            capitalizedSentence = clientSentence.toUpperCase();
            System.out.println(capitalizedSentence);
            outToClient.writeBytes(capitalizedSentence+"\n");
         }
      }
}