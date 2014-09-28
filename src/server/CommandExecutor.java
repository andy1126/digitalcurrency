/**
 * base class of all commandExecutor classes.
 * Take a request as a hashmap and fulfill the 
 * request according to the class type
 */
package server;

import java.util.HashMap;

public abstract class CommandExecutor implements Runnable{
	HashMap<String, String> map;
	
	public CommandExecutor(HashMap<String, String> newMap){
		map = newMap;
	}

}
