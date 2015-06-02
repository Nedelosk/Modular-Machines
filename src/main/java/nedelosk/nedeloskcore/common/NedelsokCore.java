package nedelosk.nedeloskcore.common;

import java.io.File;

import mcp.mobius.waila.network.NetworkHandler;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import nedelosk.nedeloskcore.common.network.GuiHandler;
import nedelosk.nedeloskcore.common.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = "NedeloskCore", version = "1.0.1" )
public class NedelsokCore
{
	
	public static File configFolder;
	public static File configPlugins;
	public static File configNedelsokCore;
	public static GuiHandler guiHandler = new GuiHandler();
	
	@Mod.Instance("NedeloskCore")
	public static NedelsokCore instance;
    
	@SidedProxy(clientSide="nedelosk.nedeloskcore.client.proxy.ClientProxy", serverSide="nedelosk.nedeloskcore.common.proxy.CommonProxy")
	public static CommonProxy proxy;
	
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	
    	NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
    	
    	NRegistry.instance = new NRegistry();
    	
    	configFolder = new File(event.getModConfigurationDirectory(), "Nedelosk_Core");
        configNedelsokCore= new File(configFolder, "NedeloskCore.cfg");
        configPlugins = new File(configFolder, "Plugins.cfg");
        
        NRegistry.instance.preInit();

    }
    
    @Mod.EventHandler
	public void init(FMLInitializationEvent event){
    	proxy.registerRenderer();
    	NRegistry.instance.init();
    }
	
    @Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event){
    	NRegistry.instance.postInit();
	}
	
}
