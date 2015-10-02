package nedelosk.modularmachines.common;

import java.io.File;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.common.command.CommandModularMachines;
import nedelosk.modularmachines.common.config.ModularConfig;
import nedelosk.modularmachines.common.core.MMCore;
import nedelosk.modularmachines.common.proxy.CommonProxy;
import nedelosk.nedeloskcore.common.core.NedeloskCore;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = "ModularMachines", version = "0.2.0", dependencies = "after:NotEnoughItems;after:EnderIO;after:NedeloskCore;after:Thaumcraft;after:ForestDay;after:ThermalExpansion;after:TConstruct")
public class ModularMachines
{
	public static Configuration config;
	public static Configuration configTechTree;
	public static File configFolder;
	
	@Mod.Instance("ModularMachines")
	public static ModularMachines instance;
    
	@SidedProxy(clientSide="nedelosk.modularmachines.client.proxy.ClientProxy", serverSide="nedelosk.modularmachines.common.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	MMCore registry = new MMCore();
	
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        File configFolderModularMachines = new File(NedeloskCore.configFolder, "modular-machines");
        File configFileModularMachines = new File(configFolderModularMachines, "Modular-Machines.cfg");
        File configFileTechTree = new File(configFolderModularMachines, "Tech-Tree.cfg");
        config = new Configuration(configFileModularMachines);
        configTechTree = new Configuration(configFileTechTree);
        configFolder = configFolderModularMachines;
        registry.preInit();
    	NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
    }
    
    @Mod.EventHandler
	public void init(FMLInitializationEvent event){
    	proxy.registerRenderer();
        proxy.init();
        registry.init();
    }
	
    @Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event){
        registry.postInit();
        ModularConfig.postInit();
	}
    
    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
    	event.registerServerCommand(new CommandModularMachines());
    }
	
}
