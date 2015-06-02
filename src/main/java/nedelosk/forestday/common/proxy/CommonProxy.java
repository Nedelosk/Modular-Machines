package nedelosk.forestday.common.proxy;

import nedelosk.nedeloskcore.common.book.BookManager;
import nedelosk.nedeloskcore.common.book.PlayerData;
import net.minecraft.world.World;

public class CommonProxy {

	public PlayerData playerData;
	public BookManager bookManager;
	
	public PlayerData getPlayerData()
	{
		return this.playerData;
	}
	
	public BookManager getBookManager() {
		return bookManager;
	}
	
	public World getClientWorld()
	{
		return null;
	}
	
    public void registerRenderers() {}
    
    public void openGUICharconia(){}
	
}
