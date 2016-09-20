package de.nedelosk.modularmachines.common.core;

import java.util.concurrent.Callable;

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
import de.nedelosk.modularmachines.api.modules.controller.IModuleController;
import de.nedelosk.modularmachines.api.modules.heaters.IModuleHeater;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.items.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.items.ModuleContainer;
import de.nedelosk.modularmachines.api.modules.items.ModuleProvider;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModuleManager {

	public static IModuleController moduleController;
	public static IModuleControllerProperties[] moduleControllerProperties = new IModuleControllerProperties[4];
	public static IModuleContainer[] moduleControllerContainers = new IModuleContainer[4];

	public static IModuleCasing moduleCasing;
	public static ModuleCasingProperties[] moduleCasingProperties = new ModuleCasingProperties[4];

	public static IModuleModuleStorage moduleDrawer;
	public static IModuleModuleStorageProperties[] moduleDrawerProperties = new IModuleModuleStorageProperties[6];

	public static IModuleHeater moduleHeaterSteam;
	public static IModuleHeaterProperties moduleHeaterSteamProperties;
	public static IModuleContainer moduleHeaterSteamContainer;

	public static ModuleModuleCleaner moduleModuleCleaner;
	public static IModuleContainer moduleModuleCleanerContainer;

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
	public static IModuleContainer moduleHeaterBronzeContainer;
	public static IModuleContainer[] moduleHeaterIronContainers = new IModuleContainer[2];
	public static IModuleContainer[] moduleHeaterSteelContainers = new IModuleContainer[3];
	public static IModuleContainer[] moduleHeaterMagmariumContainers = new IModuleContainer[3];

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
	public static IModuleContainer[] moduleBoilerContainers = new IModuleContainer[4];
	public static IModuleContainer[] moduleAlloySmelterContainers = new IModuleContainer[4];
	public static IModuleContainer[] moduleSawMillContainers = new IModuleContainer[4];
	public static IModuleContainer[] modulePulverizerContainers = new IModuleContainer[4];
	public static IModuleContainer[] moduleLatheContainers = new IModuleContainer[4];
	public static IModuleContainer[] moduleFurnaceContainers = new IModuleContainer[4];

	public static void registerModuels() {

		moduleController = new ModuleController();
		moduleController.setRegistryName(new ResourceLocation("modularmachines:controller"));
		GameRegistry.register(moduleController);

		moduleControllerProperties[0] = new ModuleControllerProperties(1, null, 16);
		moduleControllerProperties[1] = new ModuleControllerProperties(1, null, 32);
		moduleControllerProperties[2] = new ModuleControllerProperties(1, null, 64);
		moduleControllerProperties[3] = new ModuleControllerProperties(1, null, 128);

		/* CASINGS */
		moduleCasing = new ModuleCasing();
		moduleCasing.setRegistryName(new ResourceLocation("modularmachines:casing"));
		GameRegistry.register(moduleCasing);

		moduleCasingProperties[0] = new ModuleCasingProperties(1, EnumModuleSizes.LARGE, 8, 550, 7.0F, 1.5F, 1, "pickaxe");
		moduleCasingProperties[1] = new ModuleCasingProperties(2, EnumModuleSizes.LARGE, 12, 550, 9.0F, 5.0F, 1, "pickaxe");
		moduleCasingProperties[2] = new ModuleCasingProperties(3, EnumModuleSizes.LARGE, 16, 650, 10.0F, 5.0F, 1, "pickaxe");
		moduleCasingProperties[3] = new ModuleCasingProperties(4, EnumModuleSizes.LARGE, 20, 750, 11.0F, 5.0F, 1, "pickaxe");

		/* MODULE STORAGES */
		moduleDrawer = new ModuleModuleStorage();
		moduleDrawer.setRegistryName(new ResourceLocation("modularmachines:drawer"));
		GameRegistry.register(moduleDrawer);

		moduleDrawerProperties[0] = new ModuleModuleStorageProperties(1, EnumModuleSizes.LARGE, 4, EnumModulePositions.SIDE);
		moduleDrawerProperties[1] = new ModuleModuleStorageProperties(1, EnumModuleSizes.SMALL, 3, EnumModulePositions.TOP);
		moduleDrawerProperties[2] = new ModuleModuleStorageProperties(2, EnumModuleSizes.LARGE, 8, EnumModulePositions.SIDE);
		moduleDrawerProperties[3] = new ModuleModuleStorageProperties(3, EnumModuleSizes.LARGE, 16, EnumModulePositions.SIDE);
		moduleDrawerProperties[4] = new ModuleModuleStorageProperties(4, EnumModuleSizes.LARGE, 32, EnumModulePositions.SIDE);
		moduleDrawerProperties[5] = new ModuleModuleStorageProperties(5, EnumModuleSizes.LARGE, 64, EnumModulePositions.SIDE);

		/* TURBINES */
		//Steam
		moduleTurbineSteam = new ModuleTurbineSteam();
		moduleTurbineSteam.setRegistryName(new ResourceLocation("modularmachines:turbine.steam"));
		GameRegistry.register(moduleTurbineSteam);

		moduleTurbineSteamProperties[0] = new ModuleKineticProperties(9, EnumModuleSizes.LARGE, 10, 350, 70);
		moduleTurbineSteamProperties[1] = new ModuleKineticProperties(12, EnumModuleSizes.LARGE, 13, 450, 55);
		moduleTurbineSteamProperties[2] = new ModuleKineticProperties(15, EnumModuleSizes.LARGE, 16, 500, 40);
		moduleTurbineSteamProperties[3] = new ModuleKineticProperties(16, EnumModuleSizes.LARGE, 25, 550, 20);

		/* ENGINES */
		//Steam
		moduleEngineSteam = new ModuleEngineSteam();
		moduleEngineSteam.setRegistryName(new ResourceLocation("modularmachines:engine.steam"));
		GameRegistry.register(moduleEngineSteam);

		moduleEngineSteamProperties[0] = new ModuleKineticProperties(3, EnumModuleSizes.SMALL, 6, 150, 15);
		moduleEngineSteamProperties[1] = new ModuleKineticProperties(4, EnumModuleSizes.SMALL, 8, 250, 20);
		moduleEngineSteamProperties[2] = new ModuleKineticProperties(5, EnumModuleSizes.SMALL, 10, 275, 35);
		moduleEngineSteamProperties[3] = new ModuleKineticProperties(6, EnumModuleSizes.SMALL, 12, 350, 50);

		//Electric
		moduleEngineElectric = new ModuleEngineElectric();
		moduleEngineElectric.setRegistryName(new ResourceLocation("modularmachines:engine.electric"));
		GameRegistry.register(moduleEngineElectric);

		moduleEngineElectricProperties[0] = new ModuleKineticProperties(6, EnumModuleSizes.SMALL, 6, 150, 20); 
		moduleEngineElectricProperties[1] = new ModuleKineticProperties(8, EnumModuleSizes.SMALL, 8, 250, 35); 
		moduleEngineElectricProperties[2] = new ModuleKineticProperties(10, EnumModuleSizes.SMALL, 10, 275, 50); 
		moduleEngineElectricProperties[3] = new ModuleKineticProperties(12, EnumModuleSizes.SMALL, 12, 350, 70); 

		moduleChest = new ModuleChest("chest");
		moduleChest.setRegistryName(new ResourceLocation("modularmachines:chest"));
		GameRegistry.register(moduleChest);

		moduleChestProperties = new StorageModuleProperties(1, EnumModuleSizes.LARGE, EnumModulePositions.SIDE, EnumModulePositions.BACK);

		/* CLEANER */
		moduleModuleCleaner = new ModuleModuleCleaner("cleaner");
		moduleModuleCleaner.setRegistryName(new ResourceLocation("modularmachines:cleaner"));
		GameRegistry.register(moduleModuleCleaner);

		/* HEATERS */
		//Steam
		moduleHeaterSteam = new ModuleHeaterSteam();
		moduleHeaterSteam.setRegistryName(new ResourceLocation("modularmachines:heater.steam"));
		GameRegistry.register(moduleHeaterSteam);

		moduleHeaterSteamProperties = new ModuleHeaterProperties(2, EnumModuleSizes.LARGE, 150, 2);

		//Heat
		moduleHeaterBronze = new ModuleHeaterBurning();
		moduleHeaterBronze.setRegistryName(new ResourceLocation("modularmachines:heater.bronze"));
		GameRegistry.register(moduleHeaterBronze);

		moduleHeaterBronzeProperties = new ModuleHeaterProperties(1, EnumModuleSizes.LARGE, 350, 2);

		moduleHeaterIron = new ModuleHeaterBurning();
		moduleHeaterIron.setRegistryName(new ResourceLocation("modularmachines:heater.iron"));
		GameRegistry.register(moduleHeaterIron);

		moduleHeaterIronProperties[0] = new ModuleHeaterProperties(2, EnumModuleSizes.LARGE, 500, 3);
		moduleHeaterIronProperties[1] = new ModuleHeaterProperties(1, EnumModuleSizes.MEDIUM, 250, 2);

		moduleHeaterSteel = new ModuleHeaterBurning();
		moduleHeaterSteel.setRegistryName(new ResourceLocation("modularmachines:heater.steel"));
		GameRegistry.register(moduleHeaterSteel);

		moduleHeaterSteelProperties[0] = new ModuleHeaterProperties(3, EnumModuleSizes.LARGE, 750, 4);
		moduleHeaterSteelProperties[1] = new ModuleHeaterProperties(2, EnumModuleSizes.MEDIUM, 500, 3);
		moduleHeaterSteelProperties[2] = new ModuleHeaterProperties(1, EnumModuleSizes.SMALL, 250, 2);

		moduleHeaterMagmarium = new ModuleHeaterBurning();
		moduleHeaterMagmarium.setRegistryName(new ResourceLocation("modularmachines:heater.magmarium"));
		GameRegistry.register(moduleHeaterMagmarium);

		moduleHeaterMagmariumProperties[0] = new ModuleHeaterProperties(4, EnumModuleSizes.LARGE, 1000, 5);
		moduleHeaterMagmariumProperties[1] = new ModuleHeaterProperties(3, EnumModuleSizes.MEDIUM, 670, 4);
		moduleHeaterMagmariumProperties[2] = new ModuleHeaterProperties(2, EnumModuleSizes.SMALL, 330, 3);

		/* BOILERS */
		moduleBoiler = new ModuleBoiler();
		moduleBoiler.setRegistryName(new ResourceLocation("modularmachines:boiler"));
		GameRegistry.register(moduleBoiler);

		moduleBoilerProperties[0] = new ModuleBoilerProperties(1, EnumModuleSizes.LARGE, 1);
		moduleBoilerProperties[1] = new ModuleBoilerProperties(2, EnumModuleSizes.LARGE, 3);
		moduleBoilerProperties[2] = new ModuleBoilerProperties(3, EnumModuleSizes.LARGE, 5);
		moduleBoilerProperties[3] = new ModuleBoilerProperties(4, EnumModuleSizes.LARGE, 7);

		/* PULVERIZER */
		modulePulverizer = new ModulePulverizer();
		modulePulverizer.setRegistryName(new ResourceLocation("modularmachines:pulverizer"));
		GameRegistry.register(modulePulverizer);

		modulePulverizerProperties[0] = new ModuleMachineProperties(2, EnumModuleSizes.LARGE, 35, 3);
		modulePulverizerProperties[1] = new ModuleMachineProperties(4, EnumModuleSizes.LARGE, 25, 6);

		/* LATHE */
		moduleLathe = new ModuleLathe();
		moduleLathe.setRegistryName(new ResourceLocation("modularmachines:lathe"));
		GameRegistry.register(moduleLathe);

		moduleLatheProperties[0] = new ModuleMachineProperties(2, EnumModuleSizes.LARGE, 40, 2);
		moduleLatheProperties[1] = new ModuleMachineProperties(4, EnumModuleSizes.LARGE, 30, 5);

		/* ALLOY SMELTERS */
		moduleAlloySmelter = new ModuleAlloySmelter();
		moduleAlloySmelter.setRegistryName(new ResourceLocation("modularmachines:alloysmelter"));
		GameRegistry.register(moduleAlloySmelter);

		moduleAlloySmelterProperties[0] = new ModuleMachineProperties(2, EnumModuleSizes.LARGE, 35);
		moduleAlloySmelterProperties[1] = new ModuleMachineProperties(4, EnumModuleSizes.LARGE, 25);

		/* FURNACE */
		moduleFurnace = new ModuleFurnace();
		moduleFurnace.setRegistryName(new ResourceLocation("modularmachines:furnace"));
		GameRegistry.register(moduleFurnace);

		moduleFurnaceProperties[0] = new ModuleMachineProperties(1, EnumModuleSizes.LARGE, 30);
		moduleFurnaceProperties[1] = new ModuleMachineProperties(2, EnumModuleSizes.LARGE, 20);
	}

	public static void registerModuleContainers(){
		// Engines
		//Steam
		GameRegistry.register(new ModuleContainer(moduleEngineSteam, moduleEngineSteamProperties[0], new ItemStack(ItemManager.itemEngineSteam, 1, 0), EnumMetalMaterials.BRONZE));
		GameRegistry.register(new ModuleContainer(moduleEngineSteam, moduleEngineSteamProperties[1], new ItemStack(ItemManager.itemEngineSteam, 1, 1), EnumMetalMaterials.IRON));
		GameRegistry.register(new ModuleContainer(moduleEngineSteam, moduleEngineSteamProperties[2], new ItemStack(ItemManager.itemEngineSteam, 1, 2), EnumMetalMaterials.STEEL));
		GameRegistry.register(new ModuleContainer(moduleEngineSteam, moduleEngineSteamProperties[3], new ItemStack(ItemManager.itemEngineSteam, 1, 3), EnumMetalMaterials.MAGMARIUM));

		GameRegistry.register(new ModuleContainer(ModuleManager.moduleEngineElectric, moduleEngineElectricProperties[0], new ItemStack(ItemManager.itemEngineElectric, 1, 0), EnumMetalMaterials.BRONZE));
		GameRegistry.register(new ModuleContainer(ModuleManager.moduleEngineElectric, moduleEngineElectricProperties[1], new ItemStack(ItemManager.itemEngineElectric, 1, 1), EnumMetalMaterials.IRON));
		GameRegistry.register(new ModuleContainer(ModuleManager.moduleEngineElectric, moduleEngineElectricProperties[2], new ItemStack(ItemManager.itemEngineElectric, 1, 2), EnumMetalMaterials.STEEL));
		GameRegistry.register(new ModuleContainer(ModuleManager.moduleEngineElectric, moduleEngineElectricProperties[3], new ItemStack(ItemManager.itemEngineElectric, 1, 3), EnumMetalMaterials.MAGMARIUM));

		//Turbine Steam
		GameRegistry.register(new ModuleContainer(moduleTurbineSteam, moduleTurbineSteamProperties[0], new ItemStack(ItemManager.itemTurbineSteam, 1, 0), EnumMetalMaterials.BRONZE));
		GameRegistry.register(new ModuleContainer(moduleTurbineSteam, moduleTurbineSteamProperties[1], new ItemStack(ItemManager.itemTurbineSteam, 1, 1), EnumMetalMaterials.IRON));
		GameRegistry.register(new ModuleContainer(moduleTurbineSteam, moduleTurbineSteamProperties[2], new ItemStack(ItemManager.itemTurbineSteam, 1, 2), EnumMetalMaterials.STEEL));
		GameRegistry.register(new ModuleContainer(moduleTurbineSteam, moduleTurbineSteamProperties[3], new ItemStack(ItemManager.itemTurbineSteam, 1, 3), EnumMetalMaterials.MAGMARIUM));

		//Chest
		GameRegistry.register(new ModuleContainer(moduleChest, moduleChestProperties, new ItemStack(Blocks.CHEST), EnumVanillaMaterials.WOOD));

		//Controller
		moduleControllerContainers[0] = registerModuleItem(moduleController, moduleControllerProperties[0], EnumMetalMaterials.BRONZE);
		moduleControllerContainers[1] = registerModuleItem(moduleController, moduleControllerProperties[1], EnumMetalMaterials.IRON);
		moduleControllerContainers[2] = registerModuleItem(moduleController, moduleControllerProperties[2], EnumMetalMaterials.STEEL);
		moduleControllerContainers[3] = registerModuleItem(moduleController, moduleControllerProperties[3], EnumMetalMaterials.MAGMARIUM);

		//Cleaner
		moduleModuleCleanerContainer = registerModuleItem(moduleModuleCleaner, null, EnumMetalMaterials.IRON);

		//Casings
		GameRegistry.register(new ModuleContainer(moduleCasing, moduleCasingProperties[0], new ItemStack(ItemManager.itemCasings, 1, 0), EnumMetalMaterials.BRONZE));
		GameRegistry.register(new ModuleContainer(moduleCasing, moduleCasingProperties[1], new ItemStack(ItemManager.itemCasings, 1, 1), EnumMetalMaterials.IRON));
		GameRegistry.register(new ModuleContainer(moduleCasing, moduleCasingProperties[2], new ItemStack(ItemManager.itemCasings, 1, 2), EnumMetalMaterials.STEEL));
		GameRegistry.register(new ModuleContainer(moduleCasing, moduleCasingProperties[3], new ItemStack(ItemManager.itemCasings, 1, 3), EnumMetalMaterials.MAGMARIUM));

		//Drawers
		GameRegistry.register(new ModuleContainer(moduleDrawer, moduleDrawerProperties[0], new ItemStack(ItemManager.itemDrawer, 1, 0), EnumBlockMaterials.BRICK));
		GameRegistry.register(new ModuleContainer(moduleDrawer, moduleDrawerProperties[1], new ItemStack(ItemManager.itemDrawer, 1, 1), EnumBlockMaterials.BRICK));
		GameRegistry.register(new ModuleContainer(moduleDrawer, moduleDrawerProperties[2], new ItemStack(ItemManager.itemDrawer, 1, 2), EnumMetalMaterials.BRONZE));
		GameRegistry.register(new ModuleContainer(moduleDrawer, moduleDrawerProperties[3], new ItemStack(ItemManager.itemDrawer, 1, 3), EnumMetalMaterials.IRON));
		GameRegistry.register(new ModuleContainer(moduleDrawer, moduleDrawerProperties[4], new ItemStack(ItemManager.itemDrawer, 1, 4), EnumMetalMaterials.STEEL));
		GameRegistry.register(new ModuleContainer(moduleDrawer, moduleDrawerProperties[5], new ItemStack(ItemManager.itemDrawer, 1, 5), EnumMetalMaterials.MAGMARIUM));

		//Boilers
		moduleBoilerContainers[0] = registerModuleItem(moduleBoiler, moduleBoilerProperties[0], EnumMetalMaterials.BRONZE);
		moduleBoilerContainers[1] = registerModuleItem(moduleBoiler, moduleBoilerProperties[1], EnumMetalMaterials.IRON);
		moduleBoilerContainers[2] = registerModuleItem(moduleBoiler, moduleBoilerProperties[2], EnumMetalMaterials.STEEL);
		moduleBoilerContainers[3] = registerModuleItem(moduleBoiler, moduleBoilerProperties[3], EnumMetalMaterials.MAGMARIUM);

		//Heaters
		moduleHeaterSteamContainer = registerModuleItem(moduleHeaterSteam, moduleHeaterSteamProperties, EnumMetalMaterials.BRONZE);

		moduleHeaterBronzeContainer = registerModuleItem(moduleHeaterBronze, moduleHeaterBronzeProperties, EnumMetalMaterials.BRONZE);
		moduleHeaterIronContainers[0] = registerModuleItem(moduleHeaterIron, moduleHeaterIronProperties[0], EnumMetalMaterials.IRON);
		moduleHeaterIronContainers[1] = registerModuleItem(moduleHeaterIron, moduleHeaterIronProperties[1], EnumMetalMaterials.IRON);
		moduleHeaterSteelContainers[0] = registerModuleItem(moduleHeaterSteel, moduleHeaterSteelProperties[0], EnumMetalMaterials.STEEL);
		moduleHeaterSteelContainers[1] = registerModuleItem(moduleHeaterSteel, moduleHeaterSteelProperties[1], EnumMetalMaterials.STEEL);
		moduleHeaterSteelContainers[2] = registerModuleItem(moduleHeaterSteel, moduleHeaterSteelProperties[2], EnumMetalMaterials.STEEL);
		moduleHeaterMagmariumContainers[0] = registerModuleItem(moduleHeaterMagmarium, moduleHeaterMagmariumProperties[0], EnumMetalMaterials.MAGMARIUM);
		moduleHeaterMagmariumContainers[1] = registerModuleItem(moduleHeaterMagmarium, moduleHeaterMagmariumProperties[1], EnumMetalMaterials.MAGMARIUM);
		moduleHeaterMagmariumContainers[2] = registerModuleItem(moduleHeaterMagmarium, moduleHeaterMagmariumProperties[2], EnumMetalMaterials.MAGMARIUM);

		//Alloy Smelters
		moduleAlloySmelterContainers[0] = registerModuleItem(moduleAlloySmelter, moduleAlloySmelterProperties[0], EnumMetalMaterials.BRONZE);
		moduleAlloySmelterContainers[1] = registerModuleItem(moduleAlloySmelter, moduleAlloySmelterProperties[1], EnumMetalMaterials.IRON);

		//Pulverizer
		modulePulverizerContainers[0] = registerModuleItem(modulePulverizer, modulePulverizerProperties[0], EnumMetalMaterials.BRONZE);
		modulePulverizerContainers[1] = registerModuleItem(modulePulverizer, modulePulverizerProperties[1], EnumMetalMaterials.IRON);

		//Lathe
		moduleLatheContainers[0] = registerModuleItem(moduleLathe, moduleLatheProperties[0], EnumMetalMaterials.BRONZE);
		moduleLatheContainers[1] = registerModuleItem(moduleLathe, moduleLatheProperties[1], EnumMetalMaterials.IRON);

		//Furnace
		moduleFurnaceContainers[0] = registerModuleItem(moduleFurnace, moduleFurnaceProperties[0], EnumMetalMaterials.BRONZE);
		moduleFurnaceContainers[1] = registerModuleItem(moduleFurnace, moduleFurnaceProperties[1], EnumMetalMaterials.IRON);
	}

	private static IModuleContainer registerModuleItem(IModule module, IModuleProperties properties, IMaterial material){
		return GameRegistry.register(new ModuleContainer(module, properties, material));
	}

	public static void registerCapability(){
		CapabilityManager.INSTANCE.register(IModularHandler.class, new Capability.IStorage<IModularHandler>(){
			@Override
			public NBTBase writeNBT(Capability<IModularHandler> capability, IModularHandler instance, EnumFacing side) {
				return instance.serializeNBT();
			}

			@Override
			public void readNBT(Capability<IModularHandler> capability, IModularHandler instance, EnumFacing side, NBTBase nbt) {
				instance.deserializeNBT(nbt);
			}
		}, new Callable<IModularHandler>(){
			@Override
			public IModularHandler call() throws Exception{
				return new ModularHandler(null, ModularManager.DEFAULT_STORAGE_POSITIONS) {
					@Override
					public void markDirty() {
					}
				};
			}
		});

		CapabilityManager.INSTANCE.register(IModuleProvider.class, new Capability.IStorage<IModuleProvider>(){
			@Override
			public NBTBase writeNBT(Capability<IModuleProvider> capability, IModuleProvider instance, EnumFacing side) {
				return instance.serializeNBT();
			}

			@Override
			public void readNBT(Capability<IModuleProvider> capability, IModuleProvider instance, EnumFacing side, NBTBase nbt) {
				instance.deserializeNBT((NBTTagCompound) nbt);
			}
		}, new Callable<IModuleProvider>(){
			@Override
			public IModuleProvider call() throws Exception{
				return new ModuleProvider();
			}
		});
	}
}
