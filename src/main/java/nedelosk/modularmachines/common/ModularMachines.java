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
import nedelosk.modularmachines.client.techtree.utils.TechEntryData;
import nedelosk.modularmachines.common.command.CommandModularMachines;
import nedelosk.modularmachines.common.config.ModularConfig;
import nedelosk.modularmachines.common.config.TechTreeConfigs;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.proxy.CommonProxy;
import nedelosk.nedeloskcore.common.core.NedeloskCore;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = "ModularMachines", version = "0.1.1", dependencies = "after:NotEnoughItems;after:EnderIO;after:NedeloskCore;after:Thaumcraft;after:ForestDay;after:ThermalExpansion")
public class ModularMachines
{
	public static Configuration config;
	public static Configuration configTechTree;
	public static File configFolder;
	
	@Mod.Instance("ModularMachines")
	public static ModularMachines instance;
    
	@SidedProxy(clientSide="nedelosk.modularmachines.client.proxy.ClientProxy", serverSide="nedelosk.modularmachines.common.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	MMRegistry registry = new MMRegistry();
	
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
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
        {
        ModularMachinesApi.currentLanguage = Minecraft.getMinecraft().gameSettings.language;
        }
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
    	TechEntryData.checkJsonData();
        ModularConfig.postInit();
        TechTreeConfigs.postInit();
        proxy.postInit();
	}
    
    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
    	event.registerServerCommand(new CommandModularMachines());
    }
	
}
