package de.nedelosk.techtree.common;

import java.io.File;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import de.nedelosk.forestmods.common.core.ForestMods;
import de.nedelosk.techtree.api.TechTreeApi;
import de.nedelosk.techtree.common.config.TechTreeConfigs;
import de.nedelosk.techtree.common.events.EventHandler;
import de.nedelosk.techtree.common.events.EventHandlerNetwork;
import de.nedelosk.techtree.common.events.KeyHandler;
import de.nedelosk.techtree.common.proxy.CommonProxy;
import de.nedelosk.techtree.utils.TechTreeUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = "TechTree", version = "0.0.1", dependencies = "after:NedeloskCore")
public class TechTree {

	public static Configuration config_TechTree;
	public static File configFolder;
	public static File configFile;
	@Mod.Instance("TechTree")
	public static TechTree instance;
	@SidedProxy(clientSide = "nedelosk.techtree.client.proxy.ClientProxy", serverSide = "nedelosk.techtree.common.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		configFolder = new File(ForestMods.configFolder, "techtree");
		configFile = new File(configFolder, "Tech-Tree.cfg");
		config_TechTree = new Configuration(configFolder);
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			TechTreeApi.currentLanguage = Minecraft.getMinecraft().gameSettings.language;
			FMLCommonHandler.instance().bus().register(new KeyHandler());
		}
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		FMLCommonHandler.instance().bus().register(new EventHandlerNetwork());
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
		TechTreeConfigs.init();
		TechTreeUtils.readTechPoints();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
		TechTreeConfigs.postInit();
		TechTreeUtils.checkJsonData();
	}
}
