package server;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * SingleSockHandler is the wrapper class of a socket. It stores the user and
 * the socket connected to the user. It also takes care of incoming request,
 * including parse and create a commandExecutor thread to process the request
 */
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
			// TODO user authentication needed
			/* perhaps to declare users at other places since
			 * other modules need to see users as well
			 */
			user = new User(Integer.parseInt((String) json.get("ID")));
			// reply ENC command
			output.writeBytes("ACK\n");
			output.flush();
		} catch (SocketTimeoutException s) {
			System.out.println("Socket timed out!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		register();
		// receive json
		while (true) {
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
	}

	/**
	 * Send a command in the form of json, the caller of the function should
	 * provide json object
	 * 
	 * @param json
	 */
	public void sendCommand(JSONObject json) {
		try {
			output.writeBytes(json.toString() + "\n");
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * take a hashmap which is returned from parseCommand.It first check the
	 * type of the command and then make a new thread to run the commandExecutor
	 * corresponding to the command.
	 * 
	 * @param map
	 */
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

	/**
	 * return a hash map contains pairs of argument name and values for command
	 * 
	 * @param json
	 * @return HashMap
	 */
	private HashMap<String, String> parseCommand(JSONObject json) {
		HashMap<String, String> map = new HashMap<String, String>();
		for (String key : json.keySet()) {
			String content = (String) json.get(key);
			map.put(key, content);
		}
		return map;
	}

	/**
	 * put the user-socket pair into the map in class ServerCommunicationModule.
	 * It should be done before any thread begin to listen to a port, so that
	 * the server can track every connections.
	 */
	private void register() {
		communicator.insertClientSockHandler(user, this);
	}

	public Socket getSock() {
		return clientSock;
	}

	/**
	 * Invoke when a client close the connection or dose not send hearbeat
	 * messages
	 */
	public void closeConnection() {
		communicator.removeSock(user);
	}
}
