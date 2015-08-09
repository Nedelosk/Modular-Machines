package nedelosk.nedeloskcore.common.core;

import java.io.File;

import nedelosk.nedeloskcore.common.book.BookManager;
import nedelosk.nedeloskcore.common.book.PlayerData;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import nedelosk.nedeloskcore.common.event.PlayerEvents;
import nedelosk.nedeloskcore.common.network.GuiHandler;
import nedelosk.nedeloskcore.common.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = "NedeloskCore", version = "0.1.1" )
public class NedeloskCore
{
	
	public static File configFolder;
	public static File configPlugins;
	public static File configNedelsokCore;
	public static GuiHandler guiHandler = new GuiHandler();
	
	@Mod.Instance("NedeloskCore")
	public static NedeloskCore instance;
    
	@SidedProxy(clientSide="nedelosk.nedeloskcore.client.proxy.ClientProxy", serverSide="nedelosk.nedeloskcore.common.proxy.CommonProxy")
	public static CommonProxy proxy;
	
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	
    	proxy.playerData = new PlayerData();
    	proxy.bookManager = new BookManager();
    	
    	NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
    	
    	NRegistry.instance = new NRegistry();
    	
    	configFolder = new File(event.getModConfigurationDirectory(), "Nedelosk_Core");
        configNedelsokCore = new File(configFolder, "NedeloskCore.cfg");
        configPlugins = new File(configFolder, "Plugins.cfg");
        
        NRegistry.preInit();
        
    	MinecraftForge.EVENT_BUS.register(new PlayerEvents());

    }
    
    @Mod.EventHandler
	public void init(FMLInitializationEvent event){
    	proxy.registerRenderer();
    	NRegistry.init();
    }
	
    @Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event){
    	NRegistry.postInit();
	}
    
    @Mod.EventHandler
    public void serverInit(FMLServerStartingEvent event )
    {
    }
	
}
