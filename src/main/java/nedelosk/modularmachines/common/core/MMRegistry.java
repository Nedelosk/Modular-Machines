package nedelosk.modularmachines.common.core;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import nedelosk.modularmachines.client.techtree.gui.TechTree;
import nedelosk.modularmachines.common.config.ModularConfig;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.plugins.PluginManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.ForgeHooks;

public class MMRegistry {

	PluginManager pluginManager = new PluginManager();
	
	public void preInit()
	{
    	ModularRegistry.preInit();
    	pluginManager.registerPlugins();
		pluginManager.preInit();
    	ModularConfig.preInit();
    	BlockRegistry.preInit();
    	ItemRegistry.preInit();
    	PacketHandler.preInit();
    	ModularRecipeManager.preInit();
    	//ClientRegistry.registerKeyBinding(TechTree.techTree);
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
