package nedelosk.modularmachines.common;

import java.io.File;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.common.command.CommandModularMachines;
import nedelosk.modularmachines.common.config.Config;
import nedelosk.modularmachines.common.core.InternalMethodHandler;
import nedelosk.modularmachines.common.core.MMCore;
import nedelosk.modularmachines.common.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = "ModularMachines", version = "${version}", dependencies = "after:NotEnoughItems;after:EnderIO;required-after:ForestDay;after:ThermalExpansion")
public class ModularMachines {

	public static Configuration config;
	public static File configFolder;
	@Mod.Instance("ModularMachines")
	public static ModularMachines instance;
	@SidedProxy(clientSide = "nedelosk.modularmachines.client.proxy.ClientProxy", serverSide = "nedelosk.modularmachines.common.proxy.CommonProxy")
	public static CommonProxy proxy;
	MMCore registry = new MMCore();

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModularMachinesApi.handler = new InternalMethodHandler();
		File configFolderModularMachines = new File(event.getModConfigurationDirectory(), "Modular-Machines");
		File configFileModularMachines = new File(configFolderModularMachines, "Modular-Machines.cfg");
		config = new Configuration(configFileModularMachines);
		configFolder = configFolderModularMachines;
		registry.preInit(instance, event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
		registry.init(instance, event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		registry.postInit(instance, event);
		Config.postInit();
	}

	@Mod.EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandModularMachines());
	}
}
