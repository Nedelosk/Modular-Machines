package de.nedelosk.modularmachines.common.core;

import java.util.concurrent.Callable;

import de.nedelosk.modularmachines.api.energy.EnergyRegistry;
import de.nedelosk.modularmachines.api.material.EnumBlockMaterials;
import de.nedelosk.modularmachines.api.material.EnumMetalMaterials;
import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.items.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.items.ModuleContainer;
import de.nedelosk.modularmachines.api.modules.items.ModuleProvider;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.IModuleController;
import de.nedelosk.modularmachines.api.modules.storaged.IModuleModuleStorage;
import de.nedelosk.modularmachines.api.modules.storaged.drives.IModuleEngine;
import de.nedelosk.modularmachines.api.modules.storaged.drives.IModuleKineticProperties;
import de.nedelosk.modularmachines.api.modules.storaged.drives.IModuleTurbine;
import de.nedelosk.modularmachines.api.modules.storaged.drives.ModuleKineticProperties;
import de.nedelosk.modularmachines.api.modules.storaged.drives.heaters.IModuleHeater;
import de.nedelosk.modularmachines.api.modules.storaged.drives.heaters.IModuleHeaterProperties;
import de.nedelosk.modularmachines.api.modules.storaged.drives.heaters.ModuleHeaterProperties;
import de.nedelosk.modularmachines.api.modules.storaged.tools.IModuleBoilerProperties;
import de.nedelosk.modularmachines.api.modules.storaged.tools.IModuleMachineProperties;
import de.nedelosk.modularmachines.api.modules.storaged.tools.ModuleBoilerProperties;
import de.nedelosk.modularmachines.api.modules.storaged.tools.ModuleMachineProperties;
import de.nedelosk.modularmachines.common.modular.handlers.ModularHandler;
import de.nedelosk.modularmachines.common.modules.ModuleCasing;
import de.nedelosk.modularmachines.common.modules.ModuleController;
import de.nedelosk.modularmachines.common.modules.ModuleModuleCleaner;
import de.nedelosk.modularmachines.common.modules.ModuleModuleStorage;
import de.nedelosk.modularmachines.common.modules.storaged.drives.engine.ModuleEngineElectric;
import de.nedelosk.modularmachines.common.modules.storaged.drives.engine.ModuleEngineSteam;
import de.nedelosk.modularmachines.common.modules.storaged.drives.heater.ModuleHeater;
import de.nedelosk.modularmachines.common.modules.storaged.drives.heater.ModuleHeaterBurning;
import de.nedelosk.modularmachines.common.modules.storaged.drives.heater.ModuleHeaterSteam;
import de.nedelosk.modularmachines.common.modules.storaged.drives.turbines.ModuleTurbineSteam;
import de.nedelosk.modularmachines.common.modules.storaged.tools.ModuleAlloySmelter;
import de.nedelosk.modularmachines.common.modules.storaged.tools.ModuleBoiler;
import de.nedelosk.modularmachines.common.modules.storaged.tools.ModuleLathe;
import de.nedelosk.modularmachines.common.modules.storaged.tools.ModulePulverizer;
import de.nedelosk.modularmachines.common.modules.storaged.tools.ModuleSawMill;
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
	public static IModuleContainer moduleControllerContainer;

	public static IModuleCasing moduleCasingBronze;
	public static IModuleCasing moduleCasingIron;

	public static IModuleModuleStorage moduleDrawerBrickLarge;
	public static IModuleModuleStorage moduleDrawerBrickSmall;

	public static IModuleHeater moduleHeaterSteam;
	public static IModuleHeaterProperties moduleHeaterSteamProperties;
	public static IModuleContainer moduleHeaterSteamContainer;

	public static ModuleModuleCleaner moduleModuleCleaner;
	public static IModuleContainer moduleModuleCleanerContainer;

	public static IModuleTurbine moduleTurbineSteam;
	public static IModuleKineticProperties[] moduleTurbineSteamProperties = new IModuleKineticProperties[4];

	public static IModuleEngine moduleEngineSteam;
	public static IModuleKineticProperties[] moduleEngineSteamProperties = new IModuleKineticProperties[4];	
	public static IModuleEngine moduleEngineRF;	
	public static IModuleKineticProperties[] moduleEngineRFProperties = new IModuleKineticProperties[4];	
	public static IModuleEngine moduleEngineEU;
	public static IModuleKineticProperties[] moduleEngineEUProperties = new IModuleKineticProperties[4];		

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
	public static IModuleBoilerProperties[] moduleBoilerProperties = new IModuleBoilerProperties[4];
	public static IModuleMachineProperties[] moduleAlloySmelterProperties = new IModuleMachineProperties[4];
	public static IModuleMachineProperties[] moduleSawMillProperties = new IModuleMachineProperties[4];
	public static IModuleMachineProperties[] modulePulverizerProperties = new IModuleMachineProperties[4];
	public static IModuleMachineProperties[] moduleLatheProperties = new IModuleMachineProperties[4];
	public static IModuleContainer[] moduleBoilerContainers = new IModuleContainer[4];
	public static IModuleContainer[] moduleAlloySmelterContainers = new IModuleContainer[4];
	public static IModuleContainer[] moduleSawMillContainers = new IModuleContainer[4];
	public static IModuleContainer[] modulePulverizerContainers = new IModuleContainer[4];
	public static IModuleContainer[] moduleLatheContainers = new IModuleContainer[4];

	public static void registerModuels() {

		moduleController = new ModuleController();
		moduleController.setRegistryName(new ResourceLocation("modularmachines:controller"));
		GameRegistry.register(moduleController);

		/* CASINGS */
		moduleCasingBronze = new ModuleCasing(1, 6, 550, 10.0F, 1.5F, "pickaxe", 1);
		moduleCasingBronze.setRegistryName(new ResourceLocation("modularmachines:casing.bronze"));
		GameRegistry.register(moduleCasingBronze);

		moduleCasingIron = new ModuleCasing(2, 12, 550, 10.0F, 5.0F, "pickaxe", 1);
		moduleCasingIron.setRegistryName(new ResourceLocation("modularmachines:casing.iron"));
		GameRegistry.register(moduleCasingIron);

		moduleDrawerBrickLarge = new ModuleModuleStorage(3, 1, EnumModuleSize.LARGE);
		moduleDrawerBrickLarge.setRegistryName(new ResourceLocation("modularmachines:drawer.brick.large"));
		GameRegistry.register(moduleDrawerBrickLarge);

		moduleDrawerBrickSmall = new ModuleModuleStorage(3, 1, EnumModuleSize.SMALL);
		moduleDrawerBrickSmall.setRegistryName(new ResourceLocation("modularmachines:drawer.brick.small"));
		GameRegistry.register(moduleDrawerBrickSmall);

		/* TURBINES */
		//Steam
		moduleTurbineSteam = new ModuleTurbineSteam();
		moduleTurbineSteam.setRegistryName(new ResourceLocation("modularmachines:turbine.steam"));
		GameRegistry.register(moduleTurbineSteam);

		moduleTurbineSteamProperties[0] = new ModuleKineticProperties(1, EnumModuleSize.LARGE, 10, 350, 70);
		moduleTurbineSteamProperties[1] = new ModuleKineticProperties(2, EnumModuleSize.LARGE, 13, 450, 55);
		moduleTurbineSteamProperties[2] = new ModuleKineticProperties(3, EnumModuleSize.LARGE, 16, 500, 40);
		moduleTurbineSteamProperties[3] = new ModuleKineticProperties(4, EnumModuleSize.LARGE, 25, 550, 20);

		/* ENGINES */
		//Steam
		moduleEngineSteam = new ModuleEngineSteam();
		moduleEngineSteam.setRegistryName(new ResourceLocation("modularmachines:engine.steam"));
		GameRegistry.register(moduleEngineSteam);

		moduleEngineSteamProperties[0] = new ModuleKineticProperties(1, EnumModuleSize.SMALL, 3, 150, 20);
		moduleEngineSteamProperties[1] = new ModuleKineticProperties(2, EnumModuleSize.SMALL, 4, 250, 15);
		moduleEngineSteamProperties[2] = new ModuleKineticProperties(3, EnumModuleSize.SMALL, 5, 275, 10);
		moduleEngineSteamProperties[3] = new ModuleKineticProperties(4, EnumModuleSize.SMALL, 8, 350, 5);

		moduleEngineRFProperties[0] = new ModuleKineticProperties(2, EnumModuleSize.SMALL, 2, 250, 20); 
		moduleEngineRFProperties[1] = new ModuleKineticProperties(4, EnumModuleSize.SMALL, 4, 250, 25); 
		moduleEngineRFProperties[2] = new ModuleKineticProperties(6, EnumModuleSize.SMALL, 6, 250, 35); 
		moduleEngineRFProperties[3] = new ModuleKineticProperties(8, EnumModuleSize.SMALL, 8, 250, 40); 

		moduleEngineEUProperties[0] = new ModuleKineticProperties(2, EnumModuleSize.SMALL, 2, 250, 40); 
		moduleEngineEUProperties[1] = new ModuleKineticProperties(4, EnumModuleSize.SMALL, 4, 250, 50); 
		moduleEngineEUProperties[2] = new ModuleKineticProperties(6, EnumModuleSize.SMALL, 6, 250, 70); 
		moduleEngineEUProperties[3] = new ModuleKineticProperties(8, EnumModuleSize.SMALL, 8, 250, 80); 

		//RF
		if(EnergyRegistry.redstoneFlux != null){
			moduleEngineRF = new ModuleEngineElectric(EnergyRegistry.redstoneFlux);
			moduleEngineRF.setRegistryName(new ResourceLocation("modularmachines:engine.rf"));
			GameRegistry.register(moduleEngineRF);
		}

		//EU
		if(EnergyRegistry.energyUnit != null){
			moduleEngineEU = new ModuleEngineElectric(EnergyRegistry.energyUnit);
			moduleEngineEU.setRegistryName(new ResourceLocation("modularmachines:engine.eu"));
			GameRegistry.register(moduleEngineEU);
		}

		/* CLEANER */
		moduleModuleCleaner = new ModuleModuleCleaner("cleaner");
		moduleModuleCleaner.setRegistryName(new ResourceLocation("modularmachines:cleaner"));
		GameRegistry.register(moduleModuleCleaner);

		/* HEATERS */
		//Steam
		moduleHeaterSteam = new ModuleHeaterSteam();
		moduleHeaterSteam.setRegistryName(new ResourceLocation("modularmachines:heater.steam"));
		GameRegistry.register(moduleHeaterSteam);

		moduleHeaterSteamProperties = new ModuleHeaterProperties(2, EnumModuleSize.LARGE, 150, 2);

		//Heat
		moduleHeaterBronze = new ModuleHeaterBurning();
		moduleHeaterBronze.setRegistryName(new ResourceLocation("modularmachines:heater.bronze"));
		GameRegistry.register(moduleHeaterBronze);

		moduleHeaterBronzeProperties = new ModuleHeaterProperties(1, EnumModuleSize.LARGE, 350, 2);

		moduleHeaterIron = new ModuleHeaterBurning();
		moduleHeaterIron.setRegistryName(new ResourceLocation("modularmachines:heater.iron"));
		GameRegistry.register(moduleHeaterIron);

		moduleHeaterIronProperties[0] = new ModuleHeaterProperties(2, EnumModuleSize.LARGE, 500, 3);
		moduleHeaterIronProperties[1] = new ModuleHeaterProperties(1, EnumModuleSize.MIDDLE, 250, 2);

		moduleHeaterSteel = new ModuleHeaterBurning();
		moduleHeaterSteel.setRegistryName(new ResourceLocation("modularmachines:heater.steel"));
		GameRegistry.register(moduleHeaterSteel);

		moduleHeaterSteelProperties[0] = new ModuleHeaterProperties(3, EnumModuleSize.LARGE, 750, 4);
		moduleHeaterSteelProperties[1] = new ModuleHeaterProperties(2, EnumModuleSize.MIDDLE, 500, 3);
		moduleHeaterSteelProperties[2] = new ModuleHeaterProperties(1, EnumModuleSize.SMALL, 250, 2);

		moduleHeaterMagmarium = new ModuleHeaterBurning();
		moduleHeaterMagmarium.setRegistryName(new ResourceLocation("modularmachines:heater.magmarium"));
		GameRegistry.register(moduleHeaterMagmarium);

		moduleHeaterMagmariumProperties[0] = new ModuleHeaterProperties(4, EnumModuleSize.LARGE, 1000, 5);
		moduleHeaterMagmariumProperties[1] = new ModuleHeaterProperties(3, EnumModuleSize.MIDDLE, 670, 4);
		moduleHeaterMagmariumProperties[2] = new ModuleHeaterProperties(2, EnumModuleSize.SMALL, 330, 3);

		/* BOILERS */
		moduleBoiler = new ModuleBoiler();
		moduleBoiler.setRegistryName(new ResourceLocation("modularmachines:boiler"));
		GameRegistry.register(moduleBoiler);

		moduleBoilerProperties[0] = new ModuleBoilerProperties(1, EnumModuleSize.LARGE, 1);
		moduleBoilerProperties[1] = new ModuleBoilerProperties(2, EnumModuleSize.LARGE, 3);
		moduleBoilerProperties[2] = new ModuleBoilerProperties(3, EnumModuleSize.LARGE, 5);
		moduleBoilerProperties[3] = new ModuleBoilerProperties(4, EnumModuleSize.LARGE, 7);

		/* PULVERIZER */
		modulePulverizer = new ModulePulverizer();
		modulePulverizer.setRegistryName(new ResourceLocation("modularmachines:pulverizer"));
		GameRegistry.register(modulePulverizer);

		modulePulverizerProperties[0] = new ModuleMachineProperties(2, EnumModuleSize.LARGE, 35, 3);
		modulePulverizerProperties[1] = new ModuleMachineProperties(4, EnumModuleSize.LARGE, 25, 6);

		/* LATHE */
		moduleLathe = new ModuleLathe();
		moduleLathe.setRegistryName(new ResourceLocation("modularmachines:lathe"));
		GameRegistry.register(moduleLathe);

		moduleLatheProperties[0] = new ModuleMachineProperties(2, EnumModuleSize.LARGE, 40, 2);
		moduleLatheProperties[1] = new ModuleMachineProperties(4, EnumModuleSize.LARGE, 30, 5);

		/* ALLOY SMELTERS*/
		moduleAlloySmelter = new ModuleAlloySmelter();
		moduleAlloySmelter.setRegistryName(new ResourceLocation("modularmachines:alloysmelter"));
		GameRegistry.register(moduleAlloySmelter);

		moduleAlloySmelterProperties[0] = new ModuleMachineProperties(2, EnumModuleSize.LARGE, 35);
		moduleAlloySmelterProperties[1] = new ModuleMachineProperties(4, EnumModuleSize.LARGE, 25);
	}

	public static void registerModuleContainers(){
		//Engines
		if(ItemManager.itemEngineRF != null){
			GameRegistry.register(new ModuleContainer(moduleEngineRF, moduleEngineRFProperties[0], new ItemStack(ItemManager.itemEngineRF, 1, 0), EnumMetalMaterials.BRONZE));
			GameRegistry.register(new ModuleContainer(moduleEngineRF, moduleEngineRFProperties[1], new ItemStack(ItemManager.itemEngineRF, 1, 1), EnumMetalMaterials.IRON));
			GameRegistry.register(new ModuleContainer(moduleEngineRF, moduleEngineRFProperties[2], new ItemStack(ItemManager.itemEngineRF, 1, 2), EnumMetalMaterials.STEEL));
			GameRegistry.register(new ModuleContainer(moduleEngineRF, moduleEngineRFProperties[3], new ItemStack(ItemManager.itemEngineRF, 1, 3), EnumMetalMaterials.MAGMARIUM));
		}

		if(ItemManager.itemEngineEU != null){
			GameRegistry.register(new ModuleContainer(moduleEngineEU, moduleEngineEUProperties[0], new ItemStack(ItemManager.itemEngineEU, 1, 0), EnumMetalMaterials.BRONZE));
			GameRegistry.register(new ModuleContainer(moduleEngineEU, moduleEngineEUProperties[1], new ItemStack(ItemManager.itemEngineEU, 1, 1), EnumMetalMaterials.IRON));
			GameRegistry.register(new ModuleContainer(moduleEngineEU, moduleEngineEUProperties[2], new ItemStack(ItemManager.itemEngineEU, 1, 2), EnumMetalMaterials.STEEL));
			GameRegistry.register(new ModuleContainer(moduleEngineEU, moduleEngineEUProperties[3], new ItemStack(ItemManager.itemEngineEU, 1, 3), EnumMetalMaterials.MAGMARIUM));
		}

		//Steam
		GameRegistry.register(new ModuleContainer(moduleEngineSteam, moduleEngineSteamProperties[0], new ItemStack(ItemManager.itemEngineSteam, 1, 0), EnumMetalMaterials.BRONZE));
		GameRegistry.register(new ModuleContainer(moduleEngineSteam, moduleEngineSteamProperties[1], new ItemStack(ItemManager.itemEngineSteam, 1, 1), EnumMetalMaterials.IRON));
		GameRegistry.register(new ModuleContainer(moduleEngineSteam, moduleEngineSteamProperties[2], new ItemStack(ItemManager.itemEngineSteam, 1, 2), EnumMetalMaterials.STEEL));
		GameRegistry.register(new ModuleContainer(moduleEngineSteam, moduleEngineSteamProperties[3], new ItemStack(ItemManager.itemEngineSteam, 1, 3), EnumMetalMaterials.MAGMARIUM));

		//Turbine Steam
		GameRegistry.register(new ModuleContainer(moduleTurbineSteam, moduleTurbineSteamProperties[0], new ItemStack(ItemManager.itemTurbineSteam, 1, 0), EnumMetalMaterials.BRONZE));
		GameRegistry.register(new ModuleContainer(moduleTurbineSteam, moduleTurbineSteamProperties[1], new ItemStack(ItemManager.itemTurbineSteam, 1, 1), EnumMetalMaterials.IRON));
		GameRegistry.register(new ModuleContainer(moduleTurbineSteam, moduleTurbineSteamProperties[2], new ItemStack(ItemManager.itemTurbineSteam, 1, 2), EnumMetalMaterials.STEEL));
		GameRegistry.register(new ModuleContainer(moduleTurbineSteam, moduleTurbineSteamProperties[3], new ItemStack(ItemManager.itemTurbineSteam, 1, 3), EnumMetalMaterials.MAGMARIUM));

		//Controller
		moduleControllerContainer = registerModuleItem(moduleController, null, EnumMetalMaterials.IRON);

		//Cleaner
		moduleModuleCleanerContainer = registerModuleItem(moduleModuleCleaner, null, EnumMetalMaterials.IRON);

		//Casings
		GameRegistry.register(new ModuleContainer(moduleCasingBronze, new ItemStack(ItemManager.itemCasings, 1, 0), EnumMetalMaterials.BRONZE));
		GameRegistry.register(new ModuleContainer(moduleCasingIron, new ItemStack(ItemManager.itemCasings, 1, 1), EnumMetalMaterials.IRON));

		//Drawers
		GameRegistry.register(new ModuleContainer(moduleDrawerBrickLarge, new ItemStack(ItemManager.itemDrawer, 1, 0), EnumBlockMaterials.BRICK));
		GameRegistry.register(new ModuleContainer(moduleDrawerBrickSmall, new ItemStack(ItemManager.itemDrawer, 1, 1), EnumBlockMaterials.BRICK));

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
		moduleLatheContainers[1] = registerModuleItem(moduleLathe, moduleLatheProperties[0], EnumMetalMaterials.IRON);
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
				return new ModularHandler(null) {
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
