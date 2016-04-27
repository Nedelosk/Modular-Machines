package de.nedelosk.forestmods.common.core;

import static net.minecraftforge.oredict.RecipeSorter.Category.SHAPED;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import de.nedelosk.forestmods.common.config.Config;
import de.nedelosk.forestmods.common.crafting.CampfireRecipeManager;
import de.nedelosk.forestmods.common.crafting.CraftingRecipeKiln;
import de.nedelosk.forestmods.common.events.EventHandler;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.plugins.PluginManager;
import de.nedelosk.forestmods.common.world.WorldGenerator;
import de.nedelosk.forestmods.library.ForestModsApi;
import de.nedelosk.forestmods.library.Tabs;
import de.nedelosk.forestmods.library.core.Registry;
import de.nedelosk.forestmods.library.plugins.APluginManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.RecipeSorter;

public class FMRegistry extends Registry {

	public static Config config;

	@Override
	public void preInit(Object instance, FMLPreInitializationEvent event) {
		RecipeSorter.register("forestmods:mapextending", CraftingRecipeKiln.class, SHAPED, "after:minecraft:shaped before:minecraft:shapeless");
		config = new Config();
		Config.config = new Configuration(ForestMods.configFile);
		Config.syncConfig(false);
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		Tabs.tabForestMods = TabModularMachines.tabForestMods;
		Tabs.tabComponents = TabModularMachines.tabComponents;
		Tabs.tabModule = TabModularMachines.tabModules;
		PacketHandler.preInit();
		ItemManager.registerItems();
		FluidManager.registerFluids();
		BlockManager.registerBlocks();
		BlockManager.registerTiles();
		TransportManager.registerTransport();
		ForestModsApi.campfireRecipe = new CampfireRecipeManager();
		super.preInit(instance, event);
	}

	@Override
	public void init(Object instance, FMLInitializationEvent event) {
		ForestModsApi.fluidFuel.put(FluidRegistry.LAVA, 20000);
		AchievementManager achManager = new AchievementManager();
		MinecraftForge.EVENT_BUS.register(achManager);
		FMLCommonHandler.instance().bus().register(achManager);
		OreManager.registerOres();
		RecipeManager.registerRecipes();
		EnumModules.init();
		// ModuleManager.registerModuels();
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
		return ForestMods.proxy;
	}
}
