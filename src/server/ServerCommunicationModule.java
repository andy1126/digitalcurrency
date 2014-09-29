package server;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * The serverCommunicationModule receive ENC request from a client and then
 * create a socket in a separate thread(SingleSockHandler), The module itself
 * keep a map all currently working connections in sockList, which is a map
 * between user and sockets.
 */
public class ServerCommunicationModule extends Thread {
	ServerSocket welcomeSock;
	Map<User, SingleSockHandler> sockList;

	public ServerCommunicationModule(int port) throws IOException {
		welcomeSock = new ServerSocket(port);
		welcomeSock.setSoTimeout(10000);
		sockList = new HashMap<User, SingleSockHandler>();
	}

	public void run() {
		while (true) {
			try {
				Socket server = welcomeSock.accept();
				SingleSockHandler sockHandler = new SingleSockHandler(server,
						this);
				sockHandler.start();
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			} finally {
				// close all existing connections
				for (User tmp : sockList.keySet()) {
					try {
						sockList.get(tmp).getSock().close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void sendCommandByUser(User user, JSONObject json){
		getSockHandlerByUser(user).sendCommand(json);
	}

	public SingleSockHandler getSockHandlerByUser(User user) {
		return sockList.get(user);
	}

	public void insertClientSockHandler(User user, SingleSockHandler handler) {
		sockList.put(user, handler);
	}

	public void removeSock(User user) {
		sockList.remove(user);
	}

}
