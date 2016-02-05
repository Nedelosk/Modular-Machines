package de.nedelosk.forestmods.common.core;

import java.io.File;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import de.nedelosk.forestcore.multiblock.MultiblockEventHandler;
import de.nedelosk.forestmods.api.ForestModsApi;
import de.nedelosk.forestmods.common.crafting.recipes.RecipeManager;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Constants.MODID, name = Constants.NAME, version = Constants.VERSION, dependencies = Constants.DEPENDENCIES, guiFactory = "de.nedelosk.forestmods.common.config.ConfigFactory")
public class ForestMods {

	public static File configFolder;
	public static File configFile;
	@Mod.Instance(Constants.MODID)
	public static ForestMods instance;
	@SidedProxy(clientSide = "de.nedelosk.forestmods.client.core.ClientProxy", serverSide = "de.nedelosk.forestmods.common.core.CommonProxy")
	public static CommonProxy proxy;
	FMRegistry registry = new FMRegistry();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new MultiblockEventHandler());
		ForestModsApi.handler = new InternalMethodHandler();
		configFolder = new File(event.getModConfigurationDirectory(), "Forest-Mods");
		configFile = new File(configFolder, "Forest-Mods.cfg");
		registry.preInit(instance, event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		registry.init(instance, event);
		proxy.registerRenderers();
		proxy.registerTickHandlers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		registry.postInit(instance, event);
		RecipeManager.checkRecipes();
	}
}
