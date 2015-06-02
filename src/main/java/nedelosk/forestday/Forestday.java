package nedelosk.forestday;

import java.io.File;

import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.common.config.ModuleConfig;
import nedelosk.forestday.common.core.Defaults;
import nedelosk.forestday.common.network.GuiHandler;
import nedelosk.forestday.common.proxy.CommonProxy;
import nedelosk.forestday.common.registrys.ForestdayRegistry;
import nedelosk.nedeloskcore.common.NedelsokCore;
import nedelosk.nedeloskcore.common.book.BookManager;
import nedelosk.nedeloskcore.common.book.PlayerData;
import nedelosk.nedeloskcore.common.event.PlayerEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
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
	
	ForestdayRegistry registry = new ForestdayRegistry();
    
	@SidedProxy(clientSide="nedelosk.forestday.client.proxy.ClientProxy", serverSide="nedelosk.forestday.common.proxy.CommonProxy")
	public static CommonProxy proxy;
	
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	
    	proxy.playerData = new PlayerData();
    	proxy.bookManager = new BookManager();
    	
  	  NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
  	  
    	
        File configFileForestdayFolder = new File(NedelsokCore.instance.configFolder, "forest-day");
        File configFileForestday = new File(configFileForestdayFolder, "Forest-Day.cfg");
        File configFileModule = new File(configFileForestdayFolder, "Module.cfg");
        config_forestday = new Configuration(configFileForestday);
        config_module = new Configuration(configFileModule);
    	ForestdayConfig.loadConfig(configFileForestday);
    	ModuleConfig.loadConfig(configFileModule);
    	registry.preInit();
    	
    	MinecraftForge.EVENT_BUS.register(new PlayerEvents());

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
