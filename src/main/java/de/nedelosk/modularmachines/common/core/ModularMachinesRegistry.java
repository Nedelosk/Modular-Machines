package de.nedelosk.modularmachines.common.core;

import de.nedelosk.modularmachines.client.core.ModelManager;
import de.nedelosk.modularmachines.common.config.Config;
import de.nedelosk.modularmachines.common.events.EventHandler;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.plugins.PluginManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
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
		PacketHandler.preInit();
		FluidManager.registerFluids();
		BlockManager.registerBlocks();
		ItemManager.registerItems();
		BlockManager.registerTiles();
		super.preInit(instance, event);
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
			ModelManager.getInstance().registerModels();
		}
	}

	@Override
	public void init(Object instance, FMLInitializationEvent event) {
		AchievementManager achManager = new AchievementManager();
		MinecraftForge.EVENT_BUS.register(achManager);
		OreDictionaryManager.registerOres();
		ModuleManager.registerModuels();
		ModuleManager.registerModuleContainers();
		RecipeManager.registerRecipes();
		ModuleManager.registerCapability();
		AchievementManager.registerPage();
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
			ModelManager.getInstance().registerItemAndBlockColors();
		}
		super.init(instance, event);
	}

	@Override
	public void postInit(Object instance, FMLPostInitializationEvent event) {
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
			ModelManager.getInstance().registerModuleModels();
		}
		GameRegistry.registerWorldGenerator(new WorldGenerator(), 0);
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
