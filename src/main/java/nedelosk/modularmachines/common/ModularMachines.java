package nedelosk.modularmachines.common;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import nedelosk.modularmachines.api.RendererSides;
import nedelosk.modularmachines.api.modular.module.ModuleEntry;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.events.ModularEvents;
import nedelosk.modularmachines.common.modular.config.ModularConfig;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.proxy.CommonProxy;
import nedelosk.modularmachines.plugins.PluginManager;
import nedelosk.nedeloskcore.common.core.NedelsokCore;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = "ModularMachines", version = "0.1.1")
public class ModularMachines
{
	public static Configuration config;
	
	@Mod.Instance("ModularMachines")
	public static ModularMachines instance;
    
	@SidedProxy(clientSide="nedelosk.modularmachines.client.proxy.ClientProxy", serverSide="nedelosk.modularmachines.common.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	MMRegistry registry = new MMRegistry();
	
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        File configFileForestdayFolder = new File(NedelsokCore.instance.configFolder, "modular-machines");
        File configFileForestday = new File(configFileForestdayFolder, "Modular-Machines.cfg");
        config = new Configuration(configFileForestday);
        registry.preInit();
    	MinecraftForge.EVENT_BUS.register(new ModularEvents());
    	NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
    }
    
    @Mod.EventHandler
	public void init(FMLInitializationEvent event){
    	proxy.registerRenderer();
        registry.init();
    }
	
    @Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event){
        registry.postInit();
	}
	
}
