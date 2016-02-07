package de.nedelosk.forestmods.common.core;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import de.nedelosk.forestcore.core.Registry;
import de.nedelosk.forestcore.plugins.APluginManager;
import de.nedelosk.forestmods.api.crafting.ForestDayCrafting;
import de.nedelosk.forestmods.common.config.Config;
import de.nedelosk.forestmods.common.crafting.CampfireRecipeManager;
import de.nedelosk.forestmods.common.crafting.WoodTypeManager;
import de.nedelosk.forestmods.common.crafting.WorkbenchRecipeManager;
import de.nedelosk.forestmods.common.events.EventHandler;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.plugins.PluginManager;
import de.nedelosk.forestmods.common.world.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

public class MMRegistry extends Registry {

	public static Config config;

	@Override
	public void preInit(Object instance, FMLPreInitializationEvent event) {
		config = new Config();
		Config.config = new Configuration(ModularMachines.configFile, Constants.VERSION);
		Config.syncConfig(false);
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		PacketHandler.preInit();
		ModuleManager.registerCategorys();
		ItemManager.registerItems();
		FluidManager.registerFluids();
		BlockManager.registerBlocks();
		BlockManager.registerTiles();
		super.preInit(instance, event);
		ForestDayCrafting.workbenchRecipe = new WorkbenchRecipeManager();
		ForestDayCrafting.campfireRecipe = new CampfireRecipeManager();
		ForestDayCrafting.woodManager = new WoodTypeManager();
	}

	@Override
	public void init(Object instance, FMLInitializationEvent event) {
		AchievementManager achManager = new AchievementManager();
		MinecraftForge.EVENT_BUS.register(achManager);
		FMLCommonHandler.instance().bus().register(achManager);
		OreManager.registerOres();
		RecipeManager.registerRecipes();
		ModuleManager.registerModuels();
		AchievementManager.registerPage();
		super.init(instance, event);
	}

	@Override
	public void postInit(Object instance, FMLPostInitializationEvent event) {
		Config.postInit();
		GameRegistry.registerWorldGenerator(new WorldGenerator(), 0);
		RecipeManager.registerPostRecipes();
		super.postInit(instance, event);
	}

	@Override
	public APluginManager getPluginManager() {
		return new PluginManager();
	}

	@Override
	public IGuiHandler getGuiHandler() {
		return ModularMachines.proxy;
	}
}
