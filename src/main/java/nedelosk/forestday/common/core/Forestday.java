package nedelosk.forestday.common.core;

import java.io.File;

import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.common.network.GuiHandler;
import nedelosk.forestday.common.proxy.CommonProxy;
import nedelosk.forestday.common.registrys.FRegistry;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.events.ModularEvents;
import nedelosk.modularmachines.common.modular.module.manager.ModuleEnergyManager;
import nedelosk.nedeloskcore.common.core.NedeloskCore;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Defaults.MOD_ID,  version = Defaults.VERSION, dependencies = "after:Forestry;after:NotEnoughItems;after:NedeloskCore" )
public class ForestDay
{
	  public static Configuration config_forestday;
	  public static GuiHandler guiHandler = new GuiHandler();
	  
	@Mod.Instance(Defaults.MOD_ID)
	public static ForestDay instance;
	
	FRegistry registry = new FRegistry();
    
	@SidedProxy(clientSide="nedelosk.forestday.client.proxy.ClientProxy", serverSide="nedelosk.forestday.common.proxy.CommonProxy")
	public static CommonProxy proxy;
	
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {	
    	
  	  	NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
  	  
    	
        File configFileForestdayFolder = new File(NedeloskCore.instance.configFolder, "forest-day");
        File configFileForestday = new File(configFileForestdayFolder, "Forest-Day.cfg");
        config_forestday = new Configuration(configFileForestday);
    	ForestdayConfig.loadConfig(configFileForestday);
    	registry.preInit();
    }
    
    @Mod.EventHandler
	public void init(FMLInitializationEvent event){
    	
    	registry.init();
    	
		proxy.registerRenderers();
    }
	
    @Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event){
    	registry.postInit();
	}
	
}
