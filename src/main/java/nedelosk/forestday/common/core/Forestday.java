package nedelosk.forestday.common.core;

import java.io.File;

import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.common.config.ModuleConfig;
import nedelosk.forestday.common.network.GuiHandler;
import nedelosk.forestday.common.proxy.CommonProxy;
import nedelosk.forestday.common.registrys.FRegistry;
import nedelosk.nedeloskcore.common.NedelsokCore;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Defaults.MOD_ID,  version = Defaults.VERSION, dependencies = "after:Forestry;after:NotEnoughItems;after:NedeloskCore" )
public class Forestday
{
	  public static Configuration config_forestday;
	  public static Configuration config_module;
	  public static GuiHandler guiHandler = new GuiHandler();
	  
	@Mod.Instance(Defaults.MOD_ID)
	public static Forestday instance;
	
	FRegistry registry = new FRegistry();
    
	@SidedProxy(clientSide="nedelosk.forestday.client.proxy.ClientProxy", serverSide="nedelosk.forestday.common.proxy.CommonProxy")
	public static CommonProxy proxy;
	
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {	
    	
  	  NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
  	  
    	
        File configFileForestdayFolder = new File(NedelsokCore.instance.configFolder, "forest-day");
        File configFileForestday = new File(configFileForestdayFolder, "Forest-Day.cfg");
        File configFileModule = new File(configFileForestdayFolder, "Module.cfg");
        config_forestday = new Configuration(configFileForestday);
        config_module = new Configuration(configFileModule);
    	ForestdayConfig.loadConfig(configFileForestday);
    	ModuleConfig.loadConfig(configFileModule);
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
