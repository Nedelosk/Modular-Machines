/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.common;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
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

import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.container.IModuleContainer;
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
import modularmachines.common.modules.ModuleHelper;
import modularmachines.common.modules.container.EmptyModuleContainer;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.utils.capabilitys.DefaultStorage;

@Mod(modid = Constants.MOD_ID, name = Constants.NAME, version = Constants.VERSION)
public class ModularMachines {
	
	@Instance(Constants.MOD_ID)
	public static ModularMachines instance;
	
	@SidedProxy(clientSide = "modularmachines.client.core.ClientProxy", serverSide = "modularmachines.common.core.CommonProxy")
	public static CommonProxy proxy;
	
	public static IForgeRegistry<IModuleData> dataRegistry;
	public static Config config;
	
	public ModularMachines() {
		FluidRegistry.enableUniversalBucket();
		dataRegistry = new RegistryBuilder<IModuleData>().setMaxID(4095).setName(new ResourceLocation("modularmachines:moduledatas")).setType(IModuleData.class).create();
	}
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModuleManager.factory = ModuleFactory.INSTANCE;
		ModuleManager.helper = ModuleHelper.INSTANCE;
		ModuleManager.componentFactory = ModuleComponentFactory.INSTANCE;
		//new ModuleLoadManager();
		//configFolder = new File(event.getModConfigurationDirectory(), "modularmachines");
		//configFile = new File(configFolder, "Modular-Machines.cfg");
		//config = new Config();
		//Config.config = new Configuration(ModularMachines.configFile);
		//Config.syncConfig(true);
		CapabilityManager.INSTANCE.register(IModuleContainer.class, new DefaultStorage(), () -> EmptyModuleContainer.INSTANCE);
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
