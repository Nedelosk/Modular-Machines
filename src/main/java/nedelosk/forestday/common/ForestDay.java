package nedelosk.forestday.common;

import java.io.File;

import nedelosk.forestcore.library.multiblock.MultiblockEventHandler;
import nedelosk.forestday.common.configs.ForestDayConfig;
import nedelosk.forestday.common.core.FRegistry;
import nedelosk.forestday.common.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "ForestDay", version = "0.3.2", dependencies = "after:Forestry;after:NotEnoughItems")
public class ForestDay {
	public static Configuration config_forestday;

	@Mod.Instance("ForestDay")
	public static ForestDay instance;

	FRegistry registry = new FRegistry();

	@SidedProxy(clientSide = "nedelosk.forestday.client.proxy.ClientProxy", serverSide = "nedelosk.forestday.common.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		MinecraftForge.EVENT_BUS.register(new MultiblockEventHandler());

		proxy.registerTickHandlers();

		File configFileForestdayFolder = new File(event.getModConfigurationDirectory(), "Forest-Day");
		File configFileForestday = new File(configFileForestdayFolder, "Forest-Day.cfg");
		config_forestday = new Configuration(configFileForestday);
		ForestDayConfig.loadConfig(configFileForestday);
		registry.preInit(instance, event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

		registry.init(instance, event);

		proxy.registerRenderers();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		registry.postInit(instance, event);
	}

}
