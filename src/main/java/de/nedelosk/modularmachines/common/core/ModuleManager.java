package de.nedelosk.modularmachines.common.core;

import java.util.concurrent.Callable;

import de.nedelosk.modularmachines.api.energy.EnergyRegistry;
import de.nedelosk.modularmachines.api.material.EnumBlockMaterials;
import de.nedelosk.modularmachines.api.material.EnumMetalMaterials;
import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.ModuleContainer;
import de.nedelosk.modularmachines.api.modules.items.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.items.ModuleProvider;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.IModuleModuleStorage;
import de.nedelosk.modularmachines.api.modules.storaged.drives.IModuleEngine;
import de.nedelosk.modularmachines.api.modules.storaged.drives.IModuleTurbine;
import de.nedelosk.modularmachines.api.modules.storaged.drives.heaters.IModuleHeater;
import de.nedelosk.modularmachines.common.items.ItemModule;
import de.nedelosk.modularmachines.common.modular.handlers.ModularHandler;
import de.nedelosk.modularmachines.common.modules.ModuleCasing;
import de.nedelosk.modularmachines.common.modules.ModuleModuleStorage;
import de.nedelosk.modularmachines.common.modules.storaged.drives.engine.ModuleEngineElectric;
import de.nedelosk.modularmachines.common.modules.storaged.drives.engine.ModuleEngineSteam;
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

	//public static IModuleController moduleControllerIron;
	//public static IModuleController moduleControllerBronze;

	public static IModuleCasing moduleCasingIron;
	public static IModuleCasing moduleCasingBronze;

	public static IModuleModuleStorage moduleDrawerBrickLarge;
	public static IModuleModuleStorage moduleDrawerBrickSmall;

	public static IModuleTurbine moduleTurbineSteamIron;
	public static IModuleTurbine moduleTurbineSteamBronze;
	public static IModuleTurbine moduleTurbineSteamSteel;
	public static IModuleTurbine moduleTurbineSteamMagmarium;

	public static IModuleEngine moduleEngineSteamIron;
	public static IModuleEngine moduleEngineSteamBronze;
	public static IModuleEngine moduleEngineSteamSteel;
	public static IModuleEngine moduleEngineSteamMagmarium;

	public static IModuleEngine moduleEngineRFIron;
	public static IModuleEngine moduleEngineRFBronze;
	public static IModuleEngine moduleEngineRFSteel;
	public static IModuleEngine moduleEngineRFMagmarium;

	public static IModuleEngine moduleEngineEUIron;
	public static IModuleEngine moduleEngineEUBronze;
	public static IModuleEngine moduleEngineEUSteel;
	public static IModuleEngine moduleEngineEUMagmarium;

	public static IModuleHeater moduleHeaterSteamIron;

	public static IModuleHeater moduleHeaterBurningIron;
	public static IModuleHeater moduleHeaterBronzeLarge;
	public static IModuleHeater moduleHeaterBronzeMiddle;
	public static IModuleHeater moduleHeaterSteelLarge;
	public static IModuleHeater moduleHeaterSteelMiddle;
	public static IModuleHeater moduleHeaterSteelSmall;
	public static IModuleHeater moduleHeaterMagmariumLarge;
	public static IModuleHeater moduleHeaterMagmariumMiddle;
	public static IModuleHeater moduleHeaterMagmariumSmall;

	public static ModuleBoiler moduleBoilerIron;
	public static ModuleBoiler moduleBoilerBronze;
	public static ModuleBoiler moduleBoilerSteel;
	public static ModuleBoiler moduleBoilerMagmarium;

	public static ModuleAlloySmelter moduleAlloySmelterIron;
	public static ModuleAlloySmelter moduleAlloySmelterBronze;

	public static ModuleSawMill moduleSawMillIron;
	public static ModuleSawMill moduleSawMillBronze;

	public static ModulePulverizer modulePulverizerIron;
	public static ModulePulverizer modulePulverizerBronze;

	public static ModuleLathe moduleLatheIron;
	public static ModuleLathe moduleLatheBronze;

	public static void registerModuels() {

		/*moduleControllerIron = new ModuleController(6, 2, 2, 2);
		moduleControllerIron.setRegistryName(new ResourceLocation("modularmachines:controller.iron"));
		GameRegistry.register(moduleControllerIron);

		moduleControllerBronze = new ModuleController(12, 3, 3, 3);
		moduleControllerBronze.setRegistryName(new ResourceLocation("modularmachines:controller.bronze"));
		GameRegistry.register(moduleControllerBronze);*/

		/* CASINGS */
		moduleCasingIron = new ModuleCasing(1, 6, 400, 10.0F, 5.0F, "pickaxe", 1);
		moduleCasingIron.setRegistryName(new ResourceLocation("modularmachines:casing.iron"));
		GameRegistry.register(moduleCasingIron);

		moduleCasingBronze = new ModuleCasing(2, 12, 550, 10.0F, 1.5F, "pickaxe", 1);
		moduleCasingBronze.setRegistryName(new ResourceLocation("modularmachines:casing.bronze"));
		GameRegistry.register(moduleCasingBronze);

		moduleDrawerBrickLarge = new ModuleModuleStorage(3, 1, EnumModuleSize.LARGE);
		moduleDrawerBrickLarge.setRegistryName(new ResourceLocation("modularmachines:drawer.brick.large"));
		GameRegistry.register(moduleDrawerBrickLarge);

		moduleDrawerBrickSmall = new ModuleModuleStorage(3, 1, EnumModuleSize.SMALL);
		moduleDrawerBrickSmall.setRegistryName(new ResourceLocation("modularmachines:drawer.brick.small"));
		GameRegistry.register(moduleDrawerBrickSmall);

		/* TURBINES */
		//Steam
		moduleTurbineSteamIron = new ModuleTurbineSteam(1, EnumModuleSize.LARGE, 10, 350, 70);
		moduleTurbineSteamIron.setRegistryName(new ResourceLocation("modularmachines:turbine.steam.iron"));
		GameRegistry.register(moduleTurbineSteamIron);

		moduleTurbineSteamBronze =new ModuleTurbineSteam(2, EnumModuleSize.LARGE, 13, 450, 55);
		moduleTurbineSteamBronze.setRegistryName(new ResourceLocation("modularmachines:turbine.steam.bronze"));
		GameRegistry.register(moduleTurbineSteamBronze);

		moduleTurbineSteamSteel = new ModuleTurbineSteam(3, EnumModuleSize.LARGE, 16, 500, 40);
		moduleTurbineSteamSteel.setRegistryName(new ResourceLocation("modularmachines:turbine.steam.steel"));
		GameRegistry.register(moduleTurbineSteamSteel);

		moduleTurbineSteamMagmarium = new ModuleTurbineSteam(4, EnumModuleSize.LARGE, 25, 550, 20);
		moduleTurbineSteamMagmarium.setRegistryName(new ResourceLocation("modularmachines:turbine.steam.magmarium"));
		GameRegistry.register(moduleTurbineSteamMagmarium);

		/* ENGINES */
		//Steam
		moduleEngineSteamIron = new ModuleEngineSteam(1, 3, 150, 20);
		moduleEngineSteamIron.setRegistryName(new ResourceLocation("modularmachines:engine.steam.iron"));
		GameRegistry.register(moduleEngineSteamIron);

		moduleEngineSteamBronze =new ModuleEngineSteam(2, 4, 250, 15);
		moduleEngineSteamBronze.setRegistryName(new ResourceLocation("modularmachines:engine.steam.bronze"));
		GameRegistry.register(moduleEngineSteamBronze);

		moduleEngineSteamSteel = new ModuleEngineSteam(3, 5, 275, 10);
		moduleEngineSteamSteel.setRegistryName(new ResourceLocation("modularmachines:engine.steam.steel"));
		GameRegistry.register(moduleEngineSteamSteel);

		moduleEngineSteamMagmarium = new ModuleEngineSteam(4, 8, 350, 5);
		moduleEngineSteamMagmarium.setRegistryName(new ResourceLocation("modularmachines:engine.steam.magmarium"));
		GameRegistry.register(moduleEngineSteamMagmarium);

		//RF
		if(EnergyRegistry.redstoneFlux != null){
			moduleEngineRFIron = new ModuleEngineElectric(2, 2, 250, 20, EnergyRegistry.redstoneFlux);
			moduleEngineRFIron.setRegistryName(new ResourceLocation("modularmachines:engine.rf.iron"));
			GameRegistry.register(moduleEngineRFIron);

			moduleEngineRFBronze = new ModuleEngineElectric(4, 4, 250, 25, EnergyRegistry.redstoneFlux);
			moduleEngineRFBronze.setRegistryName(new ResourceLocation("modularmachines:engine.rf.bronze"));
			GameRegistry.register(moduleEngineRFBronze);

			moduleEngineRFSteel = new ModuleEngineElectric(6, 6, 250, 35, EnergyRegistry.redstoneFlux);
			moduleEngineRFSteel.setRegistryName(new ResourceLocation("modularmachines:engine.rf.steel"));
			GameRegistry.register(moduleEngineRFSteel);

			moduleEngineRFMagmarium = new ModuleEngineElectric(8, 8, 250, 20, EnergyRegistry.redstoneFlux);
			moduleEngineRFMagmarium.setRegistryName(new ResourceLocation("modularmachines:engine.rf.magmarium"));
			GameRegistry.register(moduleEngineRFMagmarium);
		}

		//EU
		if(EnergyRegistry.energyUnit != null){
			moduleEngineEUIron = new ModuleEngineElectric(2, 2, 250, 40, EnergyRegistry.energyUnit);
			moduleEngineEUIron.setRegistryName(new ResourceLocation("modularmachines:engine.eu.iron"));
			GameRegistry.register(moduleEngineEUIron);

			moduleEngineEUBronze = new ModuleEngineElectric(4, 4, 250, 50, EnergyRegistry.energyUnit);
			moduleEngineEUBronze.setRegistryName(new ResourceLocation("modularmachines:engine.eu.bronze"));
			GameRegistry.register(moduleEngineEUBronze);

			moduleEngineEUSteel = new ModuleEngineElectric(6, 6, 250, 70, EnergyRegistry.energyUnit);
			moduleEngineEUSteel.setRegistryName(new ResourceLocation("modularmachines:engine.eu.steel"));
			GameRegistry.register(moduleEngineEUSteel);

			moduleEngineEUMagmarium = new ModuleEngineElectric(8, 8, 250, 40, EnergyRegistry.energyUnit);
			moduleEngineEUMagmarium.setRegistryName(new ResourceLocation("modularmachines:engine.eu.magmarium"));
			GameRegistry.register(moduleEngineEUMagmarium);
		}

		/* HEATERS */

		//Steam
		moduleHeaterSteamIron = new ModuleHeaterSteam(2, 150, 2, EnumModuleSize.LARGE);
		moduleHeaterSteamIron.setRegistryName(new ResourceLocation("modularmachines:heater.steam.iron"));
		GameRegistry.register(moduleHeaterSteamIron);

		//Heat
		moduleHeaterBurningIron = new ModuleHeaterBurning(1, 350, 3, EnumModuleSize.LARGE);
		moduleHeaterBurningIron.setRegistryName(new ResourceLocation("modularmachines:heater.iron.large"));
		GameRegistry.register(moduleHeaterBurningIron);

		moduleHeaterBronzeLarge = new ModuleHeaterBurning(2, 450, 2, EnumModuleSize.LARGE);
		moduleHeaterBronzeLarge.setRegistryName(new ResourceLocation("modularmachines:heater.bronze.large"));
		GameRegistry.register(moduleHeaterBronzeLarge);

		moduleHeaterBronzeMiddle = new ModuleHeaterBurning(3, 350, 1, EnumModuleSize.MIDDLE);
		moduleHeaterBronzeMiddle.setRegistryName(new ResourceLocation("modularmachines:heater.bronze.middle"));
		GameRegistry.register(moduleHeaterBronzeMiddle);

		moduleHeaterSteelLarge = new ModuleHeaterBurning(3, 550, 3, EnumModuleSize.LARGE);
		moduleHeaterSteelLarge.setRegistryName(new ResourceLocation("modularmachines:heater.steel.large"));
		GameRegistry.register(moduleHeaterSteelLarge);

		moduleHeaterSteelMiddle = new ModuleHeaterBurning(4, 450, 2, EnumModuleSize.MIDDLE);
		moduleHeaterSteelMiddle.setRegistryName(new ResourceLocation("modularmachines:heater.steel.middle"));
		GameRegistry.register(moduleHeaterSteelMiddle);

		moduleHeaterSteelSmall = new ModuleHeaterBurning(5, 350, 1, EnumModuleSize.SMALL);
		moduleHeaterSteelSmall.setRegistryName(new ResourceLocation("modularmachines:heater.steel.small"));
		GameRegistry.register(moduleHeaterSteelSmall);

		moduleHeaterMagmariumLarge = new ModuleHeaterBurning(4, 700, 5, EnumModuleSize.LARGE);
		moduleHeaterMagmariumLarge.setRegistryName(new ResourceLocation("modularmachines:heater.magmarium.large"));
		GameRegistry.register(moduleHeaterMagmariumLarge);

		moduleHeaterMagmariumMiddle = new ModuleHeaterBurning(5, 600, 3, EnumModuleSize.MIDDLE);
		moduleHeaterMagmariumMiddle.setRegistryName(new ResourceLocation("modularmachines:heater.magmarium.middle"));
		GameRegistry.register(moduleHeaterMagmariumMiddle);

		moduleHeaterMagmariumSmall = new ModuleHeaterBurning(6, 500, 2, EnumModuleSize.SMALL);
		moduleHeaterMagmariumSmall.setRegistryName(new ResourceLocation("modularmachines:heater.magmarium.small"));
		GameRegistry.register(moduleHeaterMagmariumSmall);

		/* BOILERS */
		moduleBoilerIron = new ModuleBoiler(1, EnumModuleSize.LARGE, 1);
		moduleBoilerIron.setRegistryName(new ResourceLocation("modularmachines:boiler.iron"));
		GameRegistry.register(moduleBoilerIron);

		moduleBoilerBronze = new ModuleBoiler(2, EnumModuleSize.LARGE, 3);
		moduleBoilerBronze.setRegistryName(new ResourceLocation("modularmachines:boiler.bronze"));
		GameRegistry.register(moduleBoilerBronze);

		moduleBoilerSteel = new ModuleBoiler(3, EnumModuleSize.LARGE, 5);
		moduleBoilerSteel.setRegistryName(new ResourceLocation("modularmachines:boiler.steel"));
		GameRegistry.register(moduleBoilerSteel);

		moduleBoilerMagmarium = new ModuleBoiler(4, EnumModuleSize.LARGE, 7);
		moduleBoilerMagmarium.setRegistryName(new ResourceLocation("modularmachines:boiler.magmarium"));
		GameRegistry.register(moduleBoilerMagmarium);

		/* PULVERIZER */
		modulePulverizerIron = new ModulePulverizer(2, 35, EnumModuleSize.LARGE);
		modulePulverizerIron.setRegistryName(new ResourceLocation("modularmachines:pulverizer.iron"));
		GameRegistry.register(modulePulverizerIron);

		modulePulverizerBronze = new ModulePulverizer(4, 25, EnumModuleSize.LARGE);
		modulePulverizerBronze.setRegistryName(new ResourceLocation("modularmachines:pulverizer.bronze"));
		GameRegistry.register(modulePulverizerBronze);

		/* ALLOY SMELTERS*/
		moduleAlloySmelterIron = new ModuleAlloySmelter(2, 35, EnumModuleSize.LARGE);
		moduleAlloySmelterIron.setRegistryName(new ResourceLocation("modularmachines:alloysmelter.iron"));
		GameRegistry.register(moduleAlloySmelterIron);

		moduleAlloySmelterBronze = new ModuleAlloySmelter(4, 25, EnumModuleSize.LARGE);
		moduleAlloySmelterBronze.setRegistryName(new ResourceLocation("modularmachines:alloysmelter.bronze"));
		GameRegistry.register(moduleAlloySmelterBronze);
	}

	public static void registerModuleContainers(){
		ModularManager.registerModuleItem(ItemManager.itemEngineSteam);
		ModularManager.registerModuleItem(ItemManager.itemDrawer);
		ModularManager.registerModuleItem(ItemManager.itemCasings);
		ModularManager.registerModuleItem(ItemManager.itemModules);
		ModularManager.registerModuleItem(ItemManager.itemTurbineSteam);

		//Engines
		if(ItemManager.itemEngineRF != null){
			ModularManager.registerModuleItem(ItemManager.itemEngineRF);
			GameRegistry.register(new ModuleContainer(moduleEngineRFIron, new ItemStack(ItemManager.itemEngineRF, 1, 0), EnumMetalMaterials.IRON));
			GameRegistry.register(new ModuleContainer(moduleEngineRFBronze, new ItemStack(ItemManager.itemEngineRF, 1, 1), EnumMetalMaterials.BRONZE));
			GameRegistry.register(new ModuleContainer(moduleEngineRFSteel, new ItemStack(ItemManager.itemEngineRF, 1, 2), EnumMetalMaterials.STEEL));
			GameRegistry.register(new ModuleContainer(moduleEngineRFMagmarium, new ItemStack(ItemManager.itemEngineRF, 1, 3), EnumMetalMaterials.MAGMARIUM));
		}

		if(ItemManager.itemEngineEU != null){
			ModularManager.registerModuleItem(ItemManager.itemEngineEU);
			GameRegistry.register(new ModuleContainer(moduleEngineEUIron, new ItemStack(ItemManager.itemEngineEU, 1, 0), EnumMetalMaterials.IRON));
			GameRegistry.register(new ModuleContainer(moduleEngineEUBronze, new ItemStack(ItemManager.itemEngineEU, 1, 1), EnumMetalMaterials.BRONZE));
			GameRegistry.register(new ModuleContainer(moduleEngineEUSteel, new ItemStack(ItemManager.itemEngineEU, 1, 2), EnumMetalMaterials.STEEL));
			GameRegistry.register(new ModuleContainer(moduleEngineEUMagmarium, new ItemStack(ItemManager.itemEngineEU, 1, 3), EnumMetalMaterials.MAGMARIUM));
		}

		//Steam
		GameRegistry.register(new ModuleContainer(moduleEngineSteamIron, new ItemStack(ItemManager.itemEngineSteam, 1, 0), EnumMetalMaterials.IRON));
		GameRegistry.register(new ModuleContainer(moduleEngineSteamBronze, new ItemStack(ItemManager.itemEngineSteam, 1, 1), EnumMetalMaterials.BRONZE));
		GameRegistry.register(new ModuleContainer(moduleEngineSteamSteel, new ItemStack(ItemManager.itemEngineSteam, 1, 2), EnumMetalMaterials.STEEL));
		GameRegistry.register(new ModuleContainer(moduleEngineSteamMagmarium, new ItemStack(ItemManager.itemEngineSteam, 1, 3), EnumMetalMaterials.MAGMARIUM));

		//Turbine Steam
		GameRegistry.register(new ModuleContainer(moduleTurbineSteamIron, new ItemStack(ItemManager.itemTurbineSteam, 1, 0), EnumMetalMaterials.IRON));
		GameRegistry.register(new ModuleContainer(moduleTurbineSteamBronze, new ItemStack(ItemManager.itemTurbineSteam, 1, 1), EnumMetalMaterials.BRONZE));
		GameRegistry.register(new ModuleContainer(moduleTurbineSteamSteel, new ItemStack(ItemManager.itemTurbineSteam, 1, 2), EnumMetalMaterials.STEEL));
		GameRegistry.register(new ModuleContainer(moduleTurbineSteamMagmarium, new ItemStack(ItemManager.itemTurbineSteam, 1, 3), EnumMetalMaterials.MAGMARIUM));

		//Controller
		//addDefaultModuleItem(moduleControllerIron, EnumMetalMaterials.IRON);
		//addDefaultModuleItem(moduleControllerBronze, EnumMetalMaterials.BRONZE);

		//Casings
		GameRegistry.register(new ModuleContainer(moduleCasingIron, new ItemStack(ItemManager.itemCasings, 1, 0), EnumMetalMaterials.IRON));
		GameRegistry.register(new ModuleContainer(moduleCasingBronze, new ItemStack(ItemManager.itemCasings, 1, 1), EnumMetalMaterials.BRONZE));

		//Drawers
		GameRegistry.register(new ModuleContainer(moduleDrawerBrickLarge, new ItemStack(ItemManager.itemDrawer, 1, 0), EnumBlockMaterials.BRICK));
		GameRegistry.register(new ModuleContainer(moduleDrawerBrickSmall, new ItemStack(ItemManager.itemDrawer, 1, 1), EnumBlockMaterials.BRICK));

		//Boilers
		registerModuleItem(moduleBoilerIron, EnumMetalMaterials.IRON);
		registerModuleItem(moduleBoilerBronze, EnumMetalMaterials.BRONZE);
		registerModuleItem(moduleBoilerSteel, EnumMetalMaterials.STEEL);
		registerModuleItem(moduleBoilerMagmarium, EnumMetalMaterials.MAGMARIUM);

		//Heaters
		registerModuleItem(moduleHeaterSteamIron, EnumMetalMaterials.IRON);

		registerModuleItem(moduleHeaterBurningIron, EnumMetalMaterials.IRON);
		registerModuleItem(moduleHeaterBronzeLarge, EnumMetalMaterials.BRONZE);
		registerModuleItem(moduleHeaterSteelLarge, EnumMetalMaterials.STEEL);
		registerModuleItem(moduleHeaterMagmariumLarge, EnumMetalMaterials.MAGMARIUM);

		//Alloy Smelters
		registerModuleItem(moduleAlloySmelterIron, EnumMetalMaterials.IRON);
		registerModuleItem(moduleAlloySmelterBronze, EnumMetalMaterials.BRONZE);

		//Pulverizer
		registerModuleItem(modulePulverizerIron, EnumMetalMaterials.IRON);
		registerModuleItem(modulePulverizerBronze, EnumMetalMaterials.BRONZE);
	}

	private static void registerModuleItem(IModule module, IMaterial material){
		GameRegistry.register(new ModuleContainer(module, ItemModule.registerAndCreateStack(module, material), material));
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
