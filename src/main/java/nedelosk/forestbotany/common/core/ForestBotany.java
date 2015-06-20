package nedelosk.forestbotany.common.core;

import java.io.File;

import nedelosk.forestbotany.common.core.config.Config;
import nedelosk.forestbotany.common.core.proxy.CommonProxy;
import nedelosk.forestbotany.common.modules.Module;
import nedelosk.forestbotany.common.modules.ModuleManager;
import nedelosk.forestbotany.common.network.GuiHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = "ForestBotany", version = "0.0.1a", dependencies = "after:Forestry;after:NotEnoughItems;after:NedeloskCore")
public class ForestBotany {
	
	  public static File config= new File(Module.configFolder, "Forest-Botany");
	  public static GuiHandler guiHandler = new GuiHandler();
	
	@Mod.Instance("ForestBotany")
	public static ForestBotany instance;

	@SidedProxy(clientSide = "nedelosk.forestbotany.client.core.proxy.ClientProxy", serverSide = "nedelosk.forestbotany.common.core.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		/*NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
		ForestBotanyTab.instance = new ForestBotanyTab();
		ModuleManager.preInit();*/
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		/*ModuleManager.init();
		proxy.registerRenderers();
		Config.loadConfig(config);*/
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		//ModuleManager.postInit();
	}
	
}
