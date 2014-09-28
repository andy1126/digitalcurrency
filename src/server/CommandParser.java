package server;

import java.util.HashMap;

import org.json.JSONObject;


public class CommandParser {
	JSONObject command;
	CommandExecutor executor;
	HashMap<String, String> map = new HashMap<String, String>();
	
	public CommandParser(JSONObject newCommand, User user){
		command = newCommand;
	}
	
	private HashMap<String, String> parseCommand(){
		for(String key: command.keySet()){
			String content = (String) command.get(key);
			map.put(key, content);
		}
		return map;
	}

}
