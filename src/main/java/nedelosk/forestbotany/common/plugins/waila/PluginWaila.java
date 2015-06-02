package nedelosk.forestbotany.common.plugins.waila;

import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLInterModComms;

public class PluginWaila {

	@Optional.Method(modid = "Waila")
	public static void register(IWailaRegistrar register){

	}
	
	public static void init()
	{
		//if(Loader.isModLoaded("Waila"))
			//FMLInterModComms.sendMessage( "Waila", "register", PluginWaila.class.getName() + ".register" );
		
	}
	
}
