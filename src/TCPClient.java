import java.io.*;
import java.net.*;

import org.json.JSONObject;

class TCPClient {
	public static void main(String argv[]) throws Exception {
		JSONObject json = new JSONObject();
		json.put("COMP523", "Software Engineering Laboratory");
		json.put("Diane", "The teacher");
		
		for(String key : json.keySet()) {
			System.out.println(key+": "+json.get(key));
		}
		
		String sentence = json.toString();
		String modifiedSentence;
		//BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
		//		System.in));
		Socket clientSocket = new Socket("localhost", 6789);
		DataOutputStream outToServer = new DataOutputStream(
				clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		//sentence = inFromUser.readLine();
		outToServer.writeBytes(sentence+"\n");
		modifiedSentence = inFromServer.readLine();
		JSONObject another = new JSONObject(modifiedSentence);
		for(String key : another.keySet()) {
			System.out.println(key+": "+another.get(key));
		}
		clientSocket.close();
	}
}