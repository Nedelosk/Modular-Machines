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
import nedelosk.modularmachines.common.config.Constants;
import nedelosk.modularmachines.common.core.InternalMethodHandler;
import nedelosk.modularmachines.common.core.MMCore;
import nedelosk.modularmachines.common.proxy.CommonProxy;

@Mod(modid = Constants.MODID, name = Constants.NAME, version = Constants.VERSION, dependencies = Constants.DEPENDENCIES, guiFactory = "nedelosk.modularmachines.common.config.ConfigFactory")
public class ModularMachines {

	public static File configFolder;
	public static File configFile;
	@Mod.Instance("ModularMachines")
	public static ModularMachines instance;
	@SidedProxy(clientSide = "nedelosk.modularmachines.client.proxy.ClientProxy", serverSide = "nedelosk.modularmachines.common.proxy.CommonProxy")
	public static CommonProxy proxy;
	MMCore registry = new MMCore();

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModularMachinesApi.handler = new InternalMethodHandler();
		configFolder = new File(event.getModConfigurationDirectory(), "Modular-Machines");
		configFile = new File(configFolder, "Modular-Machines.cfg");
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
	}

	@Mod.EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandModularMachines());
	}
}
