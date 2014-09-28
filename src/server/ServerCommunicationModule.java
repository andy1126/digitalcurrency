package server;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class ServerCommunicationModule extends Thread{
	ServerSocket welcomeSock;
	Map<User, SingleSockHandler> sockList;
	
	public ServerCommunicationModule (int port) throws IOException{
		welcomeSock = new ServerSocket(port);
		welcomeSock.setSoTimeout(10000);
	    sockList = new HashMap<User, SingleSockHandler>();
	}
	
	public void run(){
		while (true) {
			try {
				Socket server = welcomeSock.accept();
				SingleSockHandler sockHandler = new SingleSockHandler(server, this);
				sockHandler.start(); 
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}finally{
				for(User tmp: sockList.keySet()){
					try {
						sockList.get(tmp).getSock().close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public SingleSockHandler getSockHandlerByUser(User user){
		return sockList.get(user);
	}
	public void insertClientSocketHandler(User user, SingleSockHandler handler){
		sockList.put(user, handler);
	}
	
	
}
