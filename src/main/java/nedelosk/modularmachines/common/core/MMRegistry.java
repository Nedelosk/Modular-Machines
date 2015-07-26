package nedelosk.modularmachines.common.core;

import nedelosk.modularmachines.common.modular.config.ModularConfig;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.plugins.PluginManager;

public class MMRegistry {

	PluginManager pluginManager = new PluginManager();
	
	public void preInit()
	{
		pluginManager.preInit();
    	ModularRegistry.preInit();
    	ModularConfig.preInit();
    	BlockRegistry.preInit();
    	ItemRegistry.preInit();
    	PacketHandler.preInit();
    	PluginManager.registerPlugins();
	}
	
	public void init()
	{
		pluginManager.init();
	}
	
	public void postInit()
	{
		pluginManager.postInit();
	}
	
}
