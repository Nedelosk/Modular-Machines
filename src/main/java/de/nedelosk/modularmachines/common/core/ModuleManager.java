package de.nedelosk.modularmachines.common.core;

import static de.nedelosk.modularmachines.api.modules.ModuleManager.register;

import de.nedelosk.modularmachines.api.material.EnumBlockMaterials;
import de.nedelosk.modularmachines.api.material.EnumMetalMaterials;
import de.nedelosk.modularmachines.api.material.EnumVanillaMaterials;
import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.ModularHandler;
import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleEngine;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.IModuleTurbine;
import de.nedelosk.modularmachines.api.modules.containers.IModuleItemContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleItemProvider;
import de.nedelosk.modularmachines.api.modules.containers.ModuleContainer;
import de.nedelosk.modularmachines.api.modules.containers.ModuleItemContainer;
import de.nedelosk.modularmachines.api.modules.containers.ModuleItemProvider;
import de.nedelosk.modularmachines.api.modules.controller.IModuleController;
import de.nedelosk.modularmachines.api.modules.heaters.IModuleHeater;
import de.nedelosk.modularmachines.api.modules.position.EnumModulePositions;
import de.nedelosk.modularmachines.api.modules.properties.IModuleControllerProperties;
import de.nedelosk.modularmachines.api.modules.properties.IModuleHeaterProperties;
import de.nedelosk.modularmachines.api.modules.properties.IModuleKineticProperties;
import de.nedelosk.modularmachines.api.modules.properties.IModuleModuleStorageProperties;
import de.nedelosk.modularmachines.api.modules.properties.ModuleCasingProperties;
import de.nedelosk.modularmachines.api.modules.properties.ModuleControllerProperties;
import de.nedelosk.modularmachines.api.modules.properties.ModuleHeaterProperties;
import de.nedelosk.modularmachines.api.modules.properties.ModuleKineticProperties;
import de.nedelosk.modularmachines.api.modules.storage.IStorageModule;
import de.nedelosk.modularmachines.api.modules.storage.IStorageModuleProperties;
import de.nedelosk.modularmachines.api.modules.storage.module.IModuleModuleStorage;
import de.nedelosk.modularmachines.api.modules.storage.module.ModuleModuleStorageProperties;
import de.nedelosk.modularmachines.api.modules.storage.module.StorageModuleProperties;
import de.nedelosk.modularmachines.api.modules.tools.properties.IModuleBoilerProperties;
import de.nedelosk.modularmachines.api.modules.tools.properties.IModuleMachineProperties;
import de.nedelosk.modularmachines.api.modules.tools.properties.ModuleBoilerProperties;
import de.nedelosk.modularmachines.api.modules.tools.properties.ModuleMachineProperties;
import de.nedelosk.modularmachines.common.modules.ModuleCasing;
import de.nedelosk.modularmachines.common.modules.ModuleController;
import de.nedelosk.modularmachines.common.modules.ModuleModuleCleaner;
import de.nedelosk.modularmachines.common.modules.ModuleModuleStorage;
import de.nedelosk.modularmachines.common.modules.engines.ModuleEngineElectric;
import de.nedelosk.modularmachines.common.modules.engines.ModuleEngineSteam;
import de.nedelosk.modularmachines.common.modules.heaters.ModuleHeater;
import de.nedelosk.modularmachines.common.modules.heaters.ModuleHeaterBurning;
import de.nedelosk.modularmachines.common.modules.heaters.ModuleHeaterSteam;
import de.nedelosk.modularmachines.common.modules.storages.ModuleChest;
import de.nedelosk.modularmachines.common.modules.tools.ModuleAlloySmelter;
import de.nedelosk.modularmachines.common.modules.tools.ModuleBoiler;
import de.nedelosk.modularmachines.common.modules.tools.ModuleFurnace;
import de.nedelosk.modularmachines.common.modules.tools.ModuleLathe;
import de.nedelosk.modularmachines.common.modules.tools.ModulePulverizer;
import de.nedelosk.modularmachines.common.modules.tools.ModuleSawMill;
import de.nedelosk.modularmachines.common.modules.turbines.ModuleTurbineSteam;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.INBTSerializable;

public class ModuleManager {

	public static IModuleController moduleController;
	public static IModuleControllerProperties[] moduleControllerProperties = new IModuleControllerProperties[4];
	public static IModuleItemContainer[] moduleControllerContainers = new IModuleItemContainer[4];

	public static IModuleCasing moduleCasing;
	public static ModuleCasingProperties[] moduleCasingProperties = new ModuleCasingProperties[4];

	public static IModuleModuleStorage moduleStorage;
	public static IModuleModuleStorageProperties[] moduleDrawerProperties = new IModuleModuleStorageProperties[6];

	public static IModuleHeater moduleHeaterSteam;
	public static IModuleHeaterProperties moduleHeaterSteamProperties;
	public static IModuleItemContainer moduleHeaterSteamContainer;

	public static ModuleModuleCleaner moduleModuleCleaner;
	public static IModuleItemContainer moduleModuleCleanerContainer;

	public static IModuleTurbine moduleTurbineSteam;
	public static IModuleKineticProperties[] moduleTurbineSteamProperties = new IModuleKineticProperties[4];

	public static IModuleEngine moduleEngineSteam;
	public static IModuleKineticProperties[] moduleEngineSteamProperties = new IModuleKineticProperties[4];		

	public static IModuleEngine moduleEngineElectric;
	public static IModuleKineticProperties[] moduleEngineElectricProperties = new IModuleKineticProperties[4];	

	public static IStorageModule moduleChest;
	public static IStorageModuleProperties moduleChestProperties;

	public static ModuleHeater moduleHeaterBronze;
	public static ModuleHeater moduleHeaterIron;
	public static ModuleHeater moduleHeaterSteel;
	public static ModuleHeater moduleHeaterMagmarium;
	public static IModuleHeaterProperties moduleHeaterBronzeProperties;
	public static IModuleHeaterProperties[] moduleHeaterIronProperties = new IModuleHeaterProperties[2];
	public static IModuleHeaterProperties[] moduleHeaterSteelProperties = new IModuleHeaterProperties[3];
	public static IModuleHeaterProperties[] moduleHeaterMagmariumProperties = new IModuleHeaterProperties[3];
	public static IModuleItemContainer moduleHeaterBronzeContainer;
	public static IModuleItemContainer[] moduleHeaterIronContainers = new IModuleItemContainer[2];
	public static IModuleItemContainer[] moduleHeaterSteelContainers = new IModuleItemContainer[3];
	public static IModuleItemContainer[] moduleHeaterMagmariumContainers = new IModuleItemContainer[3];

	public static ModuleBoiler moduleBoiler;
	public static ModuleAlloySmelter moduleAlloySmelter;
	public static ModuleSawMill moduleSawMill;
	public static ModulePulverizer modulePulverizer;
	public static ModuleLathe moduleLathe;
	public static ModuleFurnace moduleFurnace;
	public static IModuleBoilerProperties[] moduleBoilerProperties = new IModuleBoilerProperties[4];
	public static IModuleMachineProperties[] moduleAlloySmelterProperties = new IModuleMachineProperties[4];
	public static IModuleMachineProperties[] moduleSawMillProperties = new IModuleMachineProperties[4];
	public static IModuleMachineProperties[] modulePulverizerProperties = new IModuleMachineProperties[4];
	public static IModuleMachineProperties[] moduleLatheProperties = new IModuleMachineProperties[4];
	public static IModuleMachineProperties[] moduleFurnaceProperties = new IModuleMachineProperties[4];
	public static IModuleItemContainer[] moduleBoilerContainers = new IModuleItemContainer[4];
	public static IModuleItemContainer[] moduleAlloySmelterContainers = new IModuleItemContainer[4];
	public static IModuleItemContainer[] moduleSawMillContainers = new IModuleItemContainer[4];
	public static IModuleItemContainer[] modulePulverizerContainers = new IModuleItemContainer[4];
	public static IModuleItemContainer[] moduleLatheContainers = new IModuleItemContainer[4];
	public static IModuleItemContainer[] moduleFurnaceContainers = new IModuleItemContainer[4];

	public static void registerModuels() {
		moduleController = new ModuleController();
		register(moduleController, "controller");

		moduleControllerProperties[0] = new ModuleControllerProperties(1, 16);
		moduleControllerProperties[1] = new ModuleControllerProperties(1, 32);
		moduleControllerProperties[2] = new ModuleControllerProperties(1, 64);
		moduleControllerProperties[3] = new ModuleControllerProperties(1, 128);

		/* CASINGS */
		moduleCasing = new ModuleCasing();
		register(moduleCasing, "casing");

		moduleCasingProperties[0] = new ModuleCasingProperties(1, 8, 550, 7.0F, 1.5F);
		moduleCasingProperties[1] = new ModuleCasingProperties(2, 12, 550, 9.0F, 5.0F);
		moduleCasingProperties[2] = new ModuleCasingProperties(3, 16, 650, 10.0F, 5.0F);
		moduleCasingProperties[3] = new ModuleCasingProperties(4, 20, 750, 11.0F, 5.0F);

		/* MODULE STORAGES */
		moduleStorage = new ModuleModuleStorage();
		register(moduleStorage, "modulestorage");

		moduleDrawerProperties[0] = new ModuleModuleStorageProperties(1, 4, EnumModulePositions.SIDE);
		moduleDrawerProperties[1] = new ModuleModuleStorageProperties(1, 3, EnumModulePositions.TOP);
		moduleDrawerProperties[2] = new ModuleModuleStorageProperties(2, 8, EnumModulePositions.SIDE);
		moduleDrawerProperties[3] = new ModuleModuleStorageProperties(3, 16, EnumModulePositions.SIDE);
		moduleDrawerProperties[4] = new ModuleModuleStorageProperties(4, 32, EnumModulePositions.SIDE);
		moduleDrawerProperties[5] = new ModuleModuleStorageProperties(5, 64, EnumModulePositions.SIDE);

		/* TURBINES */
		//Steam
		moduleTurbineSteam = new ModuleTurbineSteam();
		register(moduleTurbineSteam, "turbine.steam");

		moduleTurbineSteamProperties[0] = new ModuleKineticProperties(9, 10, 350, 70);
		moduleTurbineSteamProperties[1] = new ModuleKineticProperties(12, 13, 450, 55);
		moduleTurbineSteamProperties[2] = new ModuleKineticProperties(15, 16, 500, 40);
		moduleTurbineSteamProperties[3] = new ModuleKineticProperties(16, 25, 550, 20);

		/* ENGINES */
		//Steam
		moduleEngineSteam = new ModuleEngineSteam();
		register(moduleEngineSteam, "engine.steam");

		moduleEngineSteamProperties[0] = new ModuleKineticProperties(3, 6, 150, 15);
		moduleEngineSteamProperties[1] = new ModuleKineticProperties(4, 8, 250, 20);
		moduleEngineSteamProperties[2] = new ModuleKineticProperties(5, 10, 275, 35);
		moduleEngineSteamProperties[3] = new ModuleKineticProperties(6, 12, 350, 50);

		//Electric
		moduleEngineElectric = new ModuleEngineElectric();
		register(moduleEngineElectric, "engine.electric");

		moduleEngineElectricProperties[0] = new ModuleKineticProperties(6, 6, 150, 20); 
		moduleEngineElectricProperties[1] = new ModuleKineticProperties(8, 8, 250, 35); 
		moduleEngineElectricProperties[2] = new ModuleKineticProperties(10, 10, 275, 50); 
		moduleEngineElectricProperties[3] = new ModuleKineticProperties(12, 12, 350, 70); 

		moduleChest = new ModuleChest("chest");
		register(moduleChest, "chest");

		moduleChestProperties = new StorageModuleProperties(1, EnumModulePositions.SIDE, EnumModulePositions.BACK);

		/* CLEANER */
		moduleModuleCleaner = new ModuleModuleCleaner("cleaner");
		register(moduleModuleCleaner, "cleaner");

		/* HEATERS */
		//Steam
		moduleHeaterSteam = new ModuleHeaterSteam();
		register(moduleHeaterSteam, "heater.steam");

		moduleHeaterSteamProperties = new ModuleHeaterProperties(2, 150, 2);

		//Heat
		moduleHeaterBronze = new ModuleHeaterBurning();
		register(moduleHeaterBronze, "heater.bronze");

		moduleHeaterBronzeProperties = new ModuleHeaterProperties(1, 350, 2);

		moduleHeaterIron = new ModuleHeaterBurning();
		register(moduleHeaterIron, "heater.iron");

		moduleHeaterIronProperties[0] = new ModuleHeaterProperties(2, 500, 3);
		moduleHeaterIronProperties[1] = new ModuleHeaterProperties(1, 250, 2);

		moduleHeaterSteel = new ModuleHeaterBurning();
		register(moduleHeaterSteel, "heater.steel");

		moduleHeaterSteelProperties[0] = new ModuleHeaterProperties(3, 750, 4);
		moduleHeaterSteelProperties[1] = new ModuleHeaterProperties(2, 500, 3);
		moduleHeaterSteelProperties[2] = new ModuleHeaterProperties(1, 250, 2);

		moduleHeaterMagmarium = new ModuleHeaterBurning();
		register(moduleHeaterMagmarium, "heater.magmarium");

		moduleHeaterMagmariumProperties[0] = new ModuleHeaterProperties(4, 1000, 5);
		moduleHeaterMagmariumProperties[1] = new ModuleHeaterProperties(3, 670, 4);
		moduleHeaterMagmariumProperties[2] = new ModuleHeaterProperties(2, 330, 3);

		/* BOILERS */
		moduleBoiler = new ModuleBoiler();
		register(moduleBoiler, "boiler");

		moduleBoilerProperties[0] = new ModuleBoilerProperties(1, 1);
		moduleBoilerProperties[1] = new ModuleBoilerProperties(2, 3);
		moduleBoilerProperties[2] = new ModuleBoilerProperties(3, 5);
		moduleBoilerProperties[3] = new ModuleBoilerProperties(4, 7);

		/* PULVERIZER */
		modulePulverizer = new ModulePulverizer();
		register(modulePulverizer, "pulverizer");

		modulePulverizerProperties[0] = new ModuleMachineProperties(2, 35, 3);
		modulePulverizerProperties[1] = new ModuleMachineProperties(4, 25, 6);

		/* LATHE */
		moduleLathe = new ModuleLathe();
		register(moduleLathe, "lathe");

		moduleLatheProperties[0] = new ModuleMachineProperties(2, 40, 2);
		moduleLatheProperties[1] = new ModuleMachineProperties(4, 30, 5);

		/* ALLOY SMELTERS */
		moduleAlloySmelter = new ModuleAlloySmelter();
		register(moduleAlloySmelter, "alloysmelter");

		moduleAlloySmelterProperties[0] = new ModuleMachineProperties(2, 35);
		moduleAlloySmelterProperties[1] = new ModuleMachineProperties(4, 25);

		/* FURNACE */
		moduleFurnace = new ModuleFurnace();
		register(moduleFurnace, "furnace");

		moduleFurnaceProperties[0] = new ModuleMachineProperties(1, 30);
		moduleFurnaceProperties[1] = new ModuleMachineProperties(2, 20);
	}

	public static void registerModuleContainers(){
		// Engines
		//Steam
		register(new ModuleItemContainer(new ItemStack(ItemManager.itemEngineSteam, 1, 0), EnumMetalMaterials.BRONZE, EnumModuleSizes.SMALL, new ModuleContainer(moduleEngineSteam, moduleEngineSteamProperties[0])), "engine.steam.bronze");
		register(new ModuleItemContainer(new ItemStack(ItemManager.itemEngineSteam, 1, 1), EnumMetalMaterials.IRON, EnumModuleSizes.SMALL, new ModuleContainer(moduleEngineSteam, moduleEngineSteamProperties[1])), "engine.steam.iron");
		register(new ModuleItemContainer(new ItemStack(ItemManager.itemEngineSteam, 1, 2), EnumMetalMaterials.STEEL, EnumModuleSizes.SMALL, new ModuleContainer(moduleEngineSteam, moduleEngineSteamProperties[2])), "engine.steam.steel");
		register(new ModuleItemContainer(new ItemStack(ItemManager.itemEngineSteam, 1, 3), EnumMetalMaterials.MAGMARIUM, EnumModuleSizes.SMALL, new ModuleContainer(moduleEngineSteam, moduleEngineSteamProperties[3])), "engine.steam.magmarium");

		register(new ModuleItemContainer(new ItemStack(ItemManager.itemEngineElectric, 1, 0), EnumMetalMaterials.BRONZE, EnumModuleSizes.SMALL, new ModuleContainer(ModuleManager.moduleEngineElectric, moduleEngineElectricProperties[0])), "engine.electric.bronze");
		register(new ModuleItemContainer(new ItemStack(ItemManager.itemEngineElectric, 1, 1), EnumMetalMaterials.IRON, EnumModuleSizes.SMALL, new ModuleContainer(ModuleManager.moduleEngineElectric, moduleEngineElectricProperties[1])), "engine.electric.iron");
		register(new ModuleItemContainer(new ItemStack(ItemManager.itemEngineElectric, 1, 2), EnumMetalMaterials.STEEL, EnumModuleSizes.SMALL, new ModuleContainer(ModuleManager.moduleEngineElectric, moduleEngineElectricProperties[2])), "engine.electric.steel");
		register(new ModuleItemContainer(new ItemStack(ItemManager.itemEngineElectric, 1, 3), EnumMetalMaterials.MAGMARIUM, EnumModuleSizes.SMALL, new ModuleContainer(ModuleManager.moduleEngineElectric, moduleEngineElectricProperties[3])), "engine.electric.magmarium");

		//Turbine Steam
		register(new ModuleItemContainer(new ItemStack(ItemManager.itemTurbineSteam, 1, 0), EnumMetalMaterials.BRONZE, EnumModuleSizes.LARGE, new ModuleContainer(moduleTurbineSteam, moduleTurbineSteamProperties[0])), "turbine.steam.bronze");
		register(new ModuleItemContainer(new ItemStack(ItemManager.itemTurbineSteam, 1, 1), EnumMetalMaterials.IRON, EnumModuleSizes.LARGE, new ModuleContainer(moduleTurbineSteam, moduleTurbineSteamProperties[1])), "turbine.steam.iron");
		register(new ModuleItemContainer(new ItemStack(ItemManager.itemTurbineSteam, 1, 2), EnumMetalMaterials.STEEL, EnumModuleSizes.LARGE, new ModuleContainer(moduleTurbineSteam, moduleTurbineSteamProperties[2])), "turbine.steam.steel");
		register(new ModuleItemContainer(new ItemStack(ItemManager.itemTurbineSteam, 1, 3), EnumMetalMaterials.MAGMARIUM, EnumModuleSizes.LARGE, new ModuleContainer(moduleTurbineSteam, moduleTurbineSteamProperties[3])), "turbine.steam.magmarium");

		//Chest
		register(new ModuleItemContainer(new ItemStack(Blocks.CHEST), EnumVanillaMaterials.WOOD, EnumModuleSizes.LARGEST, true, new ModuleContainer(moduleChest, moduleChestProperties)));

		//Controller
		moduleControllerContainers[0] = registerModuleItem(moduleController, moduleControllerProperties[0], EnumMetalMaterials.BRONZE, EnumModuleSizes.LARGE);
		moduleControllerContainers[1] = registerModuleItem(moduleController, moduleControllerProperties[1], EnumMetalMaterials.IRON, EnumModuleSizes.LARGE);
		moduleControllerContainers[2] = registerModuleItem(moduleController, moduleControllerProperties[2], EnumMetalMaterials.STEEL, EnumModuleSizes.LARGE);
		moduleControllerContainers[3] = registerModuleItem(moduleController, moduleControllerProperties[3], EnumMetalMaterials.MAGMARIUM, EnumModuleSizes.LARGE);

		//Cleaner
		moduleModuleCleanerContainer = registerModuleItem(moduleModuleCleaner, null, EnumMetalMaterials.IRON, EnumModuleSizes.SMALL);

		//Casings
		register(new ModuleItemContainer(new ItemStack(ItemManager.itemCasings, 1, 0), EnumMetalMaterials.BRONZE, EnumModuleSizes.LARGEST, new ModuleContainer(moduleCasing, moduleCasingProperties[0])), "casing.bronze");
		register(new ModuleItemContainer(new ItemStack(ItemManager.itemCasings, 1, 1), EnumMetalMaterials.IRON, EnumModuleSizes.LARGEST, new ModuleContainer(moduleCasing, moduleCasingProperties[1])), "casing.iron");
		register(new ModuleItemContainer(new ItemStack(ItemManager.itemCasings, 1, 2), EnumMetalMaterials.STEEL, EnumModuleSizes.LARGEST, new ModuleContainer(moduleCasing, moduleCasingProperties[2])), "casing.steel");
		register(new ModuleItemContainer(new ItemStack(ItemManager.itemCasings, 1, 3), EnumMetalMaterials.MAGMARIUM, EnumModuleSizes.LARGEST, new ModuleContainer(moduleCasing, moduleCasingProperties[3])), "casing.magmarium");

		//Drawers
		register(new ModuleItemContainer(new ItemStack(ItemManager.itemDrawer, 1, 0), EnumBlockMaterials.BRICK, EnumModuleSizes.LARGE, new ModuleContainer(moduleStorage, moduleDrawerProperties[0])), "modulestorage.brick.large");
		register(new ModuleItemContainer(new ItemStack(ItemManager.itemDrawer, 1, 1), EnumBlockMaterials.BRICK, EnumModuleSizes.SMALL, new ModuleContainer(moduleStorage, moduleDrawerProperties[1])), "modulestorage.brick.small");
		register(new ModuleItemContainer(new ItemStack(ItemManager.itemDrawer, 1, 2), EnumMetalMaterials.BRONZE, EnumModuleSizes.LARGE, new ModuleContainer(moduleStorage, moduleDrawerProperties[2])), "modulestorage.bronze.large");
		register(new ModuleItemContainer(new ItemStack(ItemManager.itemDrawer, 1, 3), EnumMetalMaterials.IRON, EnumModuleSizes.LARGE, new ModuleContainer(moduleStorage, moduleDrawerProperties[3])), "modulestorage.iron.large");
		register(new ModuleItemContainer(new ItemStack(ItemManager.itemDrawer, 1, 4), EnumMetalMaterials.STEEL, EnumModuleSizes.LARGE, new ModuleContainer(moduleStorage, moduleDrawerProperties[4])), "modulestorage.steel.large");
		register(new ModuleItemContainer(new ItemStack(ItemManager.itemDrawer, 1, 5), EnumMetalMaterials.MAGMARIUM, EnumModuleSizes.LARGE, new ModuleContainer(moduleStorage, moduleDrawerProperties[5])), "modulestorage.magmarium.large");

		//Boilers
		moduleBoilerContainers[0] = registerModuleItem(moduleBoiler, moduleBoilerProperties[0], EnumMetalMaterials.BRONZE, EnumModuleSizes.LARGE);
		moduleBoilerContainers[1] = registerModuleItem(moduleBoiler, moduleBoilerProperties[1], EnumMetalMaterials.IRON, EnumModuleSizes.LARGE);
		moduleBoilerContainers[2] = registerModuleItem(moduleBoiler, moduleBoilerProperties[2], EnumMetalMaterials.STEEL, EnumModuleSizes.LARGE);
		moduleBoilerContainers[3] = registerModuleItem(moduleBoiler, moduleBoilerProperties[3], EnumMetalMaterials.MAGMARIUM, EnumModuleSizes.LARGE);

		//Heaters
		moduleHeaterSteamContainer = registerModuleItem(moduleHeaterSteam, moduleHeaterSteamProperties, EnumMetalMaterials.BRONZE, EnumModuleSizes.LARGE);

		moduleHeaterBronzeContainer = registerModuleItem(moduleHeaterBronze, moduleHeaterBronzeProperties, EnumMetalMaterials.BRONZE, EnumModuleSizes.LARGE);
		moduleHeaterIronContainers[0] = registerModuleItem(moduleHeaterIron, moduleHeaterIronProperties[0], EnumMetalMaterials.IRON, EnumModuleSizes.LARGE);
		moduleHeaterIronContainers[1] = registerModuleItem(moduleHeaterIron, moduleHeaterIronProperties[1], EnumMetalMaterials.IRON, EnumModuleSizes.MEDIUM);
		moduleHeaterSteelContainers[0] = registerModuleItem(moduleHeaterSteel, moduleHeaterSteelProperties[0], EnumMetalMaterials.STEEL, EnumModuleSizes.LARGE);
		moduleHeaterSteelContainers[1] = registerModuleItem(moduleHeaterSteel, moduleHeaterSteelProperties[1], EnumMetalMaterials.STEEL, EnumModuleSizes.MEDIUM);
		moduleHeaterSteelContainers[2] = registerModuleItem(moduleHeaterSteel, moduleHeaterSteelProperties[2], EnumMetalMaterials.STEEL, EnumModuleSizes.SMALL);
		moduleHeaterMagmariumContainers[0] = registerModuleItem(moduleHeaterMagmarium, moduleHeaterMagmariumProperties[0], EnumMetalMaterials.MAGMARIUM, EnumModuleSizes.LARGE);
		moduleHeaterMagmariumContainers[1] = registerModuleItem(moduleHeaterMagmarium, moduleHeaterMagmariumProperties[1], EnumMetalMaterials.MAGMARIUM, EnumModuleSizes.MEDIUM);
		moduleHeaterMagmariumContainers[2] = registerModuleItem(moduleHeaterMagmarium, moduleHeaterMagmariumProperties[2], EnumMetalMaterials.MAGMARIUM, EnumModuleSizes.SMALL);

		//Alloy Smelters
		moduleAlloySmelterContainers[0] = registerModuleItem(moduleAlloySmelter, moduleAlloySmelterProperties[0], EnumMetalMaterials.BRONZE, EnumModuleSizes.LARGE);
		moduleAlloySmelterContainers[1] = registerModuleItem(moduleAlloySmelter, moduleAlloySmelterProperties[1], EnumMetalMaterials.IRON, EnumModuleSizes.LARGE);

		//Pulverizer
		modulePulverizerContainers[0] = registerModuleItem(modulePulverizer, modulePulverizerProperties[0], EnumMetalMaterials.BRONZE, EnumModuleSizes.LARGE);
		modulePulverizerContainers[1] = registerModuleItem(modulePulverizer, modulePulverizerProperties[1], EnumMetalMaterials.IRON, EnumModuleSizes.LARGE);

		//Lathe
		moduleLatheContainers[0] = registerModuleItem(moduleLathe, moduleLatheProperties[0], EnumMetalMaterials.BRONZE, EnumModuleSizes.LARGE);
		moduleLatheContainers[1] = registerModuleItem(moduleLathe, moduleLatheProperties[1], EnumMetalMaterials.IRON, EnumModuleSizes.LARGE);

		//Furnace
		moduleFurnaceContainers[0] = registerModuleItem(moduleFurnace, moduleFurnaceProperties[0], EnumMetalMaterials.BRONZE, EnumModuleSizes.LARGE);
		moduleFurnaceContainers[1] = registerModuleItem(moduleFurnace, moduleFurnaceProperties[1], EnumMetalMaterials.IRON, EnumModuleSizes.LARGE);
	}

	private static IModuleItemContainer registerModuleItem(IModule module, IModuleProperties properties, IMaterial material, EnumModuleSizes size){
		return register(new ModuleItemContainer(null, material, size, new ModuleContainer(module, properties)), module.getRegistryName().getResourcePath() + "." + material.getName() + "." + size.getName());
	}

	public static void registerCapability(){
		CapabilityManager.INSTANCE.register(IModularHandler.class, new DefaultStorage<>(), () -> new ModularHandler(null, ModularManager.DEFAULT_STORAGE_POSITIONS) {
			@Override
			public void markDirty() {
			}
		});

		CapabilityManager.INSTANCE.register(IModuleItemProvider.class, new DefaultStorage<>(), () -> new ModuleItemProvider());
	}

	private static class DefaultStorage<T extends INBTSerializable> implements IStorage<T>{
		@Override
		public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
			return instance.serializeNBT();
		}

		@Override
		public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt) {
			instance.deserializeNBT(nbt);
		}
	}
}
