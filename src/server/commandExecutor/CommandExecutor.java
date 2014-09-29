package server.commandExecutor;

import java.util.HashMap;

/**
 * base class of all commandExecutor classes. Take a request as a hashmap and
 * fulfill the request according to the class type
 */
public abstract class CommandExecutor implements Runnable {
	HashMap<String, String> map;

	public CommandExecutor(HashMap<String, String> newMap) {
		map = newMap;
	}

}
