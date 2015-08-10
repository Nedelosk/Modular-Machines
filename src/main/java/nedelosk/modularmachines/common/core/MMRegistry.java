package nedelosk.modularmachines.common.core;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import nedelosk.modularmachines.common.config.ModularConfig;
import nedelosk.modularmachines.common.core.manager.ModularRecipeManager;
import nedelosk.modularmachines.common.core.manager.OreDictManager;
import nedelosk.modularmachines.common.core.registry.BlockRegistry;
import nedelosk.modularmachines.common.core.registry.ItemRegistry;
import nedelosk.modularmachines.common.core.registry.ModularRegistry;
import nedelosk.modularmachines.common.core.registry.TechTreeRegistry;
import nedelosk.modularmachines.common.events.EventHandler;
import nedelosk.modularmachines.common.events.EventHandlerNetwork;
import nedelosk.modularmachines.common.events.KeyHandler;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.plugins.PluginManager;
import net.minecraftforge.common.MinecraftForge;

public class MMRegistry {

	PluginManager pluginManager = new PluginManager();
	
	public void preInit()
	{
    	pluginManager.registerPlugins();
		pluginManager.preInit();
    	ModularConfig.preInit();
    	BlockRegistry.preInit();
    	ItemRegistry.preInit();
    	ModularRegistry.preInit();
    	TechTreeRegistry.preInit();
    	PacketHandler.preInit();
    	ModularRecipeManager.preInit();
    	MinecraftForge.EVENT_BUS.register(new EventHandler());
    	if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
    		FMLCommonHandler.instance().bus().register(new KeyHandler());
		FMLCommonHandler.instance().bus().register(new EventHandlerNetwork());
	}
	
	public void init()
	{
		OreDictManager.init();
		pluginManager.init();
	}
	
	public void postInit()
	{
		pluginManager.postInit();
	}
	
}
