package server;

import java.util.HashMap;

public abstract class CommandExecutor implements Runnable{
	HashMap<String, String> map;
	
	public CommandExecutor(HashMap<String, String> newMap){
		map = newMap;
	}

}
