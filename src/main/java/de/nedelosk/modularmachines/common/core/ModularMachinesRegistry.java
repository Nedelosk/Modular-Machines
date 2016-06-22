package de.nedelosk.modularmachines.common.core;

import de.nedelosk.forestmods.library.Tabs;
import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.client.core.ModelManager;
import de.nedelosk.modularmachines.common.config.Config;
import de.nedelosk.modularmachines.common.events.EventHandler;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.plugins.APluginManager;
import de.nedelosk.modularmachines.common.plugins.PluginManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class ModularMachinesRegistry extends Registry {

	public static Config config;

	@Override
	public void preInit(Object instance, FMLPreInitializationEvent event) {
		config = new Config();
		Config.config = new Configuration(ModularMachines.configFile);
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
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
			ModelManager.getInstance().registerModels();
		}
		super.preInit(instance, event);
	}

	@Override
	public void init(Object instance, FMLInitializationEvent event) {
		AchievementManager achManager = new AchievementManager();
		MinecraftForge.EVENT_BUS.register(achManager);
		OreManager.registerOres();
		RecipeManager.registerRecipes();
		EnumModules.init();
		// ModuleManager.registerModuels();
		AchievementManager.registerPage();
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
			ModelManager.getInstance().registerItemAndBlockColors();
		}
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
