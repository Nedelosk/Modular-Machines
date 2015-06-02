package nedelosk.nedeloskcore.common.book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerData {

	public static Map<String, ArrayList<String>> entrys = new HashMap();
	public static Map<String, ArrayList<String>> knowledge  = new HashMap();
	
	public static void removeData(String playerName)
	{
		entrys.remove(playerName);
		knowledge.remove(playerName);
	}
	
	public ArrayList<String> getEntrys(String playerName) {
		return entrys.get(playerName);
	}
	
	public ArrayList<String> getKnowledges(String playerName) {
		return knowledge.get(playerName);
	}
	
	public Map<String, ArrayList<String>> getUnlockEntrys()
	{
		return entrys;
	}
	
	public static Map<String, ArrayList<String>> getUnlockKnowledges() {
		return knowledge;
	}
	
}
