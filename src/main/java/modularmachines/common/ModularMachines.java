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
import net.minecraftforge.fml.common.registry.GameRegistry;

import modularmachines.api.modules.IModuleData;
import modularmachines.api.modules.IModuleDefinition;
import modularmachines.api.modules.IModuleRegistry;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.positions.CasingPosition;
import modularmachines.api.modules.positions.RackPosition;
import modularmachines.common.compat.CompatManager;
import modularmachines.common.config.Config;
import modularmachines.common.core.CommonProxy;
import modularmachines.common.core.Constants;
import modularmachines.common.core.EventHandler;
import modularmachines.common.core.GuiHandler;
import modularmachines.common.modules.ModuleComponentFactory;
import modularmachines.common.modules.ModuleDefinition;
import modularmachines.common.modules.ModuleFactory;
import modularmachines.common.modules.ModuleRegistry;
import modularmachines.common.modules.json.ModuleParser;
import modularmachines.common.network.PacketHandler;
import modularmachines.registry.ModFluids;

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
		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModuleManager.factory = ModuleFactory.INSTANCE;
		IModuleRegistry registry = ModuleManager.registry = ModuleRegistry.INSTANCE;
		registry.registerPositions(CasingPosition.values());
		registry.registerPositions(RackPosition.values());
		ModuleManager.componentFactory = ModuleComponentFactory.INSTANCE;
		//new ModuleLoadManager();
		configFile = event.getSuggestedConfigurationFile();
		MinecraftForge.EVENT_BUS.register(Config.class);
		Config.config = new Configuration(ModularMachines.configFile);
		Config.syncConfig(true);
		new PacketHandler();
		ModFluids.preInit();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		proxy.preInit();
		ModuleDefinition.preInit();
		CompatManager.preInit();
		ModuleParser parser = new ModuleParser(new File(event.getModConfigurationDirectory(), Constants.MOD_ID + "/modules"));
		parser.load();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		registerTypes();
		proxy.init();
		CompatManager.init();
	}
	
	public static void registerTypes() {
		for (IModuleData data : GameRegistry.findRegistry(IModuleData.class)) {
			IModuleDefinition definition = data.getDefinition();
			definition.registerTypes(ModuleRegistry.INSTANCE);
		}
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
		CompatManager.postInit();
		//Config.syncConfig(true);
	}
}
