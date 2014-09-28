package server;

import java.io.*;
import java.net.*;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class SingleSockHandler extends Thread {
	Socket clientSock;
	User user;
	BufferedReader input;
	DataOutputStream output;
	ServerCommunicationModule communicator;

	public SingleSockHandler(Socket sock,
			ServerCommunicationModule theCommunicator) {
		clientSock = sock;
		communicator = theCommunicator;
		try {
			input = new BufferedReader(new InputStreamReader(
					clientSock.getInputStream()));
			output = new DataOutputStream(clientSock.getOutputStream());
			JSONObject json = new JSONObject(input.readLine());
			user = new User(Integer.parseInt((String) json.get("ID")));
			output.writeBytes("ACK\n");
		} catch (SocketTimeoutException s) {
			System.out.println("Socket timed out!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		register();
		// receive json
		try {
			JSONObject json = new JSONObject(input.readLine());
			HashMap<String, String> map = parseCommand(json);
			executeCommand(map);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void executeCommand(HashMap<String, String> map) {
		String type = map.get("type");
		switch (type) {
		case "JNQ":
			break;
		case "VNT":
			break;
		case "NCX":
			break;
		case "HSI":
			break;
		case "CNU":
			break;
		case "NPM":
			break;
		case "SVN":
			break;
		case "NCD":
			break;
		}

	}

	private HashMap<String, String> parseCommand(JSONObject json) {
		HashMap<String, String> map = new HashMap<String, String>();
		for (String key : json.keySet()) {
			String content = (String) json.get(key);
			map.put(key, content);
		}
		return map;
	}

	private void register() {
		communicator.insertClientSocketHandler(user, this);
	}

	public Socket getSock() {
		return clientSock;
	}
}
