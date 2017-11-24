/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.common;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.data.IModuleData;
import modularmachines.common.compat.theoneprobe.TheOneProbeCompat;
import modularmachines.common.config.Config;
import modularmachines.common.core.CommonProxy;
import modularmachines.common.core.Constants;
import modularmachines.common.core.GuiHandler;
import modularmachines.common.core.RecipeManager;
import modularmachines.common.core.managers.ModBlocks;
import modularmachines.common.core.managers.ModFluids;
import modularmachines.common.core.managers.ModItems;
import modularmachines.common.core.managers.ModOreDicts;
import modularmachines.common.modules.ModuleComponentFactory;
import modularmachines.common.modules.ModuleDefinition;
import modularmachines.common.modules.ModuleFactory;
import modularmachines.common.modules.ModuleRegistry;
import modularmachines.common.network.PacketHandler;

@Mod(modid = Constants.MOD_ID, name = Constants.NAME, version = Constants.VERSION)
public class ModularMachines {
	
	@Instance(Constants.MOD_ID)
	public static ModularMachines instance;
	
	@SidedProxy(clientSide = "modularmachines.client.core.ClientProxy", serverSide = "modularmachines.common.core.CommonProxy")
	public static CommonProxy proxy;
	
	public static IForgeRegistry<IModuleData> dataRegistry;
	public static Config config;
	public static File configFile;
	
	public ModularMachines() {
		FluidRegistry.enableUniversalBucket();
	}
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModuleManager.factory = ModuleFactory.INSTANCE;
		ModuleManager.registry = ModuleRegistry.INSTANCE;
		ModuleManager.componentFactory = ModuleComponentFactory.INSTANCE;
		//new ModuleLoadManager();
		configFile = event.getSuggestedConfigurationFile();
		MinecraftForge.EVENT_BUS.register(Config.class);
		Config.config = new Configuration(ModularMachines.configFile);
		Config.syncConfig(true);
		new PacketHandler();
		MinecraftForge.EVENT_BUS.register(ModuleDefinition.class);
		ModFluids.preInit();
		ModBlocks.preInit();
		ModItems.preInit();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		proxy.preInit();
		ModOreDicts.registerOres();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		ModuleDefinition.registerModuleContainers();
		RecipeManager.registerRecipes();
		proxy.init();
		TheOneProbeCompat.postInit();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
		//Config.syncConfig(true);
	}
	
}
