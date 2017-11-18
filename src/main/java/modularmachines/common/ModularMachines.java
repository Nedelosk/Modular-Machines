/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.common;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import modularmachines.api.modules.ModuleData;
import modularmachines.common.config.Config;
import modularmachines.common.core.CommonProxy;
import modularmachines.common.core.Constants;
import modularmachines.common.core.GuiHandler;
import modularmachines.common.core.RecipeManager;
import modularmachines.common.core.managers.BlockManager;
import modularmachines.common.core.managers.FluidManager;
import modularmachines.common.core.managers.ItemManager;
import modularmachines.common.core.managers.ModuleManager;
import modularmachines.common.core.managers.OreDictionaryManager;
import modularmachines.common.event.EventHandler;
import modularmachines.common.modules.ModuleDefinition;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.plugins.PluginManager;

@Mod(modid = Constants.MOD_ID, name = Constants.NAME, version = Constants.VERSION)
public class ModularMachines {
	
	@Instance(Constants.MOD_ID)
	public static ModularMachines instance;
	
	@SidedProxy(clientSide = "modularmachines.client.core.ClientProxy", serverSide = "modularmachines.common.core.CommonProxy")
	public static CommonProxy proxy;
	
	public static IForgeRegistry<ModuleData> dataRegistry;
	public static final PluginManager PLUGIN_MANAGER = new PluginManager();
	public static Config config;
	
	public ModularMachines() {
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		FluidRegistry.enableUniversalBucket();
		dataRegistry = new RegistryBuilder<ModuleData>().setMaxID(4095).setName(new ResourceLocation("modularmachines:modulecontainers")).setType(ModuleData.class).create();
	}
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//new ModuleLoadManager();
		//configFolder = new File(event.getModConfigurationDirectory(), "modularmachines");
		//configFile = new File(configFolder, "Modular-Machines.cfg");
		//ModularManager.helper = new ModularHelper();
		//config = new Config();
		//Config.config = new Configuration(ModularMachines.configFile);
		//Config.syncConfig(true);
		ModuleManager.registerCapability();
		new PacketHandler();
		FluidManager.registerFluids();
		BlockManager.registerBlocks();
		ItemManager.registerItems();
		BlockManager.registerTiles();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		PLUGIN_MANAGER.preInit();
		proxy.preInit();
		proxy.loadModuleModels();
		OreDictionaryManager.registerOres();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		//ModuleManager.registerModuels();
		ModuleDefinition.registerModuleContainers();
		RecipeManager.registerRecipes();
		proxy.init();
		PLUGIN_MANAGER.init();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		//ModuleLoadManager.loadModules();
		//ModuleLoadManager.loadModuleContainers();
		proxy.postInit();
		//GameRegistry.registerWorldGenerator(new WorldGenerator(), 0);
		//Config.syncConfig(true);
		RecipeManager.registerHolderRecipes();
		PLUGIN_MANAGER.postInit();
	}
}
