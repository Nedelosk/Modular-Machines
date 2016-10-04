package de.nedelosk.modularmachines.common.core;

import static net.minecraftforge.oredict.RecipeSorter.Category.SHAPED;

import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modules.models.ModuleModelLoader;
import de.nedelosk.modularmachines.client.model.ModelManager;
import de.nedelosk.modularmachines.client.model.ModelModular;
import de.nedelosk.modularmachines.common.config.Config;
import de.nedelosk.modularmachines.common.events.EventHandler;
import de.nedelosk.modularmachines.common.modular.ModularHelper;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.plugins.PluginManager;
import de.nedelosk.modularmachines.common.recipse.ModuleCrafterRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.RecipeSorter;

public class ModRegistry extends Registry {

	public static Config config;

	@Override
	public void preInit(Object instance, FMLPreInitializationEvent event) {
		ModularManager.helper = new ModularHelper();
		config = new Config();
		Config.config = new Configuration(ModularMachines.configFile);
		Config.syncConfig(true);
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		ModuleManager.registerCapability();
		new PacketHandler();
		FluidManager.registerFluids();
		BlockManager.registerBlocks();
		ItemManager.registerItems();
		BlockManager.registerTiles();
		super.preInit(instance, event);
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
			ModelManager.getInstance().registerModels();
			MinecraftForge.EVENT_BUS.register(ModelModular.class);
		}
		RecipeSorter.register("modularmachines:module_crafter", ModuleCrafterRecipe.class, SHAPED, "before:minecraft:shapeless");
		OreDictionaryManager.registerOres();
	}

	@Override
	public void init(Object instance, FMLInitializationEvent event) {
		AchievementManager achManager = new AchievementManager();
		MinecraftForge.EVENT_BUS.register(achManager);
		ModuleManager.registerModuels();
		ModuleManager.registerModuleContainers();
		RecipeManager.registerRecipes();
		AchievementManager.registerPage();
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
			ModelManager.getInstance().registerItemAndBlockColors();
		}
		super.init(instance, event);
	}

	@Override
	public void postInit(Object instance, FMLPostInitializationEvent event) {
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
			ModuleModelLoader.loadModels();
		}
		GameRegistry.registerWorldGenerator(new WorldGenerator(), 0);
		Config.syncConfig(true);
		RecipeManager.registerHolderRecipes();
		super.postInit(instance, event);
	}

	@Override
	public PluginManager getPluginManager() {
		return new PluginManager();
	}

	@Override
	public IGuiHandler getGuiHandler() {
		return ModularMachines.proxy;
	}
}
