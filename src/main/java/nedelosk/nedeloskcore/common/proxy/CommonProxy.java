package nedelosk.nedeloskcore.common.proxy;

import nedelosk.nedeloskcore.common.book.BookManager;
import nedelosk.nedeloskcore.common.book.PlayerData;
import net.minecraft.world.World;

public class CommonProxy {

	public void registerRenderer()
	{
		
	}
	
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
	
}
