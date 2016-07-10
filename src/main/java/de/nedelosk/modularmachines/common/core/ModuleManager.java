package de.nedelosk.modularmachines.common.core;

import java.util.concurrent.Callable;

import de.nedelosk.modularmachines.api.material.EnumBlockMaterials;
import de.nedelosk.modularmachines.api.material.EnumMetalMaterials;
import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modules.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleController;
import de.nedelosk.modularmachines.api.modules.IModuleDrawer;
import de.nedelosk.modularmachines.api.modules.engine.IModuleEngine;
import de.nedelosk.modularmachines.api.modules.heater.IModuleHeater;
import de.nedelosk.modularmachines.common.items.ItemModule;
import de.nedelosk.modularmachines.common.modular.AssemblerLogicBasic;
import de.nedelosk.modularmachines.common.modular.handlers.ModularHandler;
import de.nedelosk.modularmachines.common.modules.ModuleCasing;
import de.nedelosk.modularmachines.common.modules.ModuleContainer;
import de.nedelosk.modularmachines.common.modules.ModuleController;
import de.nedelosk.modularmachines.common.modules.ModuleDrawer;
import de.nedelosk.modularmachines.common.modules.engine.ModuleEngineEnergy;
import de.nedelosk.modularmachines.common.modules.engine.ModuleEngineSteam;
import de.nedelosk.modularmachines.common.modules.heater.ModuleHeaterBurning;
import de.nedelosk.modularmachines.common.modules.heater.ModuleHeaterSteam;
import de.nedelosk.modularmachines.common.modules.tools.ModuleAlloySmelter;
import de.nedelosk.modularmachines.common.modules.tools.ModuleBoiler;
import de.nedelosk.modularmachines.common.modules.tools.ModuleLathe;
import de.nedelosk.modularmachines.common.modules.tools.ModulePulverizer;
import de.nedelosk.modularmachines.common.modules.tools.ModuleSawMill;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModuleManager {

	public static IModuleController moduleControllerIron;
	public static IModuleController moduleControllerBronze;

	public static IModuleCasing moduleCasingIron;
	public static IModuleCasing moduleCasingBronze;

	public static IModuleDrawer moduleDrawerBrick;

	public static IModuleEngine moduleEngineSteamIron;
	public static IModuleEngine moduleEngineSteamBronze;
	public static IModuleEngine moduleEngineSteamSteel;
	public static IModuleEngine moduleEngineSteamMagmarium;

	public static IModuleEngine moduleEngineIron;
	public static IModuleEngine moduleEngineBronze;
	public static IModuleEngine moduleEngineSteel;
	public static IModuleEngine moduleEngineMagmarium;

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

	public static ModuleAlloySmelter moduleAlloySmelterIron;
	public static ModuleAlloySmelter moduleAlloySmelterBronze;

	public static ModuleSawMill moduleSawMillIron;
	public static ModuleSawMill moduleSawMillBronze;

	public static ModulePulverizer modulePulverizerIron;
	public static ModulePulverizer modulePulverizerBronze;

	public static ModuleLathe moduleLatheIron;
	public static ModuleLathe moduleLatheBronze;

	public static void registerModuels() {

		moduleControllerIron = new ModuleController(6, 2, 2, 2);
		moduleControllerIron.setRegistryName(new ResourceLocation("modularmachines:controller.iron"));
		GameRegistry.register(moduleControllerIron);

		moduleControllerBronze = new ModuleController(12, 3, 3, 3);
		moduleControllerBronze.setRegistryName(new ResourceLocation("modularmachines:controller.bronze"));
		GameRegistry.register(moduleControllerBronze);

		/* CASINGS */
		moduleCasingIron = new ModuleCasing(1, 400, 10.0F, 5.0F, "pickaxe", 1);
		moduleCasingIron.setRegistryName(new ResourceLocation("modularmachines:casing.iron"));
		GameRegistry.register(moduleCasingIron);

		moduleCasingBronze = new ModuleCasing(2, 550, 10.0F, 1.5F, "pickaxe", 1);
		moduleCasingBronze.setRegistryName(new ResourceLocation("modularmachines:casing.bronze"));
		GameRegistry.register(moduleCasingBronze);

		moduleDrawerBrick = new ModuleDrawer(3, 1);
		moduleDrawerBrick.setRegistryName(new ResourceLocation("modularmachines:drawer.brick"));
		GameRegistry.register(moduleDrawerBrick);

		/* ENGINES */
		//Steam
		moduleEngineSteamIron = new ModuleEngineSteam(1, 2, 5);
		moduleEngineSteamIron.setRegistryName(new ResourceLocation("modularmachines:engine.steam.iron"));
		GameRegistry.register(moduleEngineSteamIron);

		moduleEngineSteamBronze = new ModuleEngineSteam(2, 3, 7);
		moduleEngineSteamBronze.setRegistryName(new ResourceLocation("modularmachines:engine.steam.bronze"));
		GameRegistry.register(moduleEngineSteamBronze);

		moduleEngineSteamSteel = new ModuleEngineSteam(3, 4, 15);
		moduleEngineSteamSteel.setRegistryName(new ResourceLocation("modularmachines:engine.steam.steel"));
		GameRegistry.register(moduleEngineSteamSteel);

		moduleEngineSteamMagmarium = new ModuleEngineSteam(4, 5, 25);
		moduleEngineSteamMagmarium.setRegistryName(new ResourceLocation("modularmachines:engine.steam.magmarium"));
		GameRegistry.register(moduleEngineSteamMagmarium);

		//Normal		
		moduleEngineIron = new ModuleEngineEnergy(2, 2, 20);
		moduleEngineIron.setRegistryName(new ResourceLocation("modularmachines:engine.iron"));
		GameRegistry.register(moduleEngineIron);

		moduleEngineBronze = new ModuleEngineEnergy(4, 4, 25);
		moduleEngineBronze.setRegistryName(new ResourceLocation("modularmachines:engine.bronze"));
		GameRegistry.register(moduleEngineBronze);

		moduleEngineSteel = new ModuleEngineEnergy(6, 6, 35);
		moduleEngineSteel.setRegistryName(new ResourceLocation("modularmachines:engine.steel"));
		GameRegistry.register(moduleEngineSteel);

		moduleEngineMagmarium = new ModuleEngineEnergy(8, 8, 15);
		moduleEngineMagmarium.setRegistryName(new ResourceLocation("modularmachines:engine.magmarium"));
		GameRegistry.register(moduleEngineMagmarium);

		/* HEATERS */

		//Steam
		moduleHeaterSteamIron = new ModuleHeaterSteam(2, 150, EnumModuleSize.LARGE);
		moduleHeaterSteamIron.setRegistryName(new ResourceLocation("modularmachines:heater.steam.iron"));
		GameRegistry.register(moduleHeaterSteamIron);

		//Heat
		moduleHeaterBurningIron = new ModuleHeaterBurning(1, 350, EnumModuleSize.LARGE);
		moduleHeaterBurningIron.setRegistryName(new ResourceLocation("modularmachines:heater.iron.large"));
		GameRegistry.register(moduleHeaterBurningIron);

		moduleHeaterBronzeLarge = new ModuleHeaterBurning(2, 450, EnumModuleSize.LARGE);
		moduleHeaterBronzeLarge.setRegistryName(new ResourceLocation("modularmachines:heater.bronze.large"));
		GameRegistry.register(moduleHeaterBronzeLarge);

		moduleHeaterBronzeMiddle = new ModuleHeaterBurning(3, 350, EnumModuleSize.MIDDLE);
		moduleHeaterBronzeMiddle.setRegistryName(new ResourceLocation("modularmachines:heater.bronze.middle"));
		GameRegistry.register(moduleHeaterBronzeMiddle);

		moduleHeaterSteelLarge = new ModuleHeaterBurning(3, 550, EnumModuleSize.LARGE);
		moduleHeaterSteelLarge.setRegistryName(new ResourceLocation("modularmachines:heater.steel.large"));
		GameRegistry.register(moduleHeaterSteelLarge);

		moduleHeaterSteelMiddle = new ModuleHeaterBurning(4, 450, EnumModuleSize.MIDDLE);
		moduleHeaterSteelMiddle.setRegistryName(new ResourceLocation("modularmachines:heater.steel.middle"));
		GameRegistry.register(moduleHeaterSteelMiddle);

		moduleHeaterSteelSmall = new ModuleHeaterBurning(5, 350, EnumModuleSize.SMALL);
		moduleHeaterSteelSmall.setRegistryName(new ResourceLocation("modularmachines:heater.steel.small"));
		GameRegistry.register(moduleHeaterSteelSmall);

		moduleHeaterMagmariumLarge = new ModuleHeaterBurning(4, 700, EnumModuleSize.LARGE);
		moduleHeaterMagmariumLarge.setRegistryName(new ResourceLocation("modularmachines:heater.magmarium.large"));
		GameRegistry.register(moduleHeaterMagmariumLarge);

		moduleHeaterMagmariumMiddle = new ModuleHeaterBurning(5, 600, EnumModuleSize.MIDDLE);
		moduleHeaterMagmariumMiddle.setRegistryName(new ResourceLocation("modularmachines:heater.magmarium.middle"));
		GameRegistry.register(moduleHeaterMagmariumMiddle);

		moduleHeaterMagmariumSmall = new ModuleHeaterBurning(6, 500, EnumModuleSize.SMALL);
		moduleHeaterMagmariumSmall.setRegistryName(new ResourceLocation("modularmachines:heater.magmarium.small"));
		GameRegistry.register(moduleHeaterMagmariumSmall);

		/* BOILERS */
		moduleBoilerIron = new ModuleBoiler(1, 22, EnumModuleSize.LARGE);
		moduleBoilerIron.setRegistryName(new ResourceLocation("modularmachines:boiler.iron"));
		GameRegistry.register(moduleBoilerIron);

		moduleBoilerBronze = new ModuleBoiler(2, 15, EnumModuleSize.LARGE);
		moduleBoilerBronze.setRegistryName(new ResourceLocation("modularmachines:boiler.bronze"));
		GameRegistry.register(moduleBoilerBronze);

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

		//RecipeRegistry.registerRecipeHandler(new RecipeAdvancedHandler(LatheModes.class, "Lathe"));
		ModularManager.registerAssemblerLogic(new AssemblerLogicBasic());
	}

	public static void registerModuleContainers(){
		//Engines
		GameRegistry.register(new ModuleContainer(moduleEngineIron, new ItemStack(ItemManager.itemEngine, 1, 0), EnumMetalMaterials.IRON));
		GameRegistry.register(new ModuleContainer(moduleEngineBronze, new ItemStack(ItemManager.itemEngine, 1, 1), EnumMetalMaterials.BRONZE));
		GameRegistry.register(new ModuleContainer(moduleEngineSteel, new ItemStack(ItemManager.itemEngine, 1, 2), EnumMetalMaterials.STEEL));
		GameRegistry.register(new ModuleContainer(moduleEngineMagmarium, new ItemStack(ItemManager.itemEngine, 1, 3), EnumMetalMaterials.MAGMARIUM));

		//Engines Steam
		GameRegistry.register(new ModuleContainer(moduleEngineSteamIron, new ItemStack(ItemManager.itemEngineSteam, 1, 0), EnumMetalMaterials.IRON));
		GameRegistry.register(new ModuleContainer(moduleEngineSteamBronze, new ItemStack(ItemManager.itemEngineSteam, 1, 1), EnumMetalMaterials.BRONZE));
		GameRegistry.register(new ModuleContainer(moduleEngineSteamSteel, new ItemStack(ItemManager.itemEngineSteam, 1, 2), EnumMetalMaterials.STEEL));
		GameRegistry.register(new ModuleContainer(moduleEngineSteamMagmarium, new ItemStack(ItemManager.itemEngineSteam, 1, 3), EnumMetalMaterials.MAGMARIUM));

		//Controller
		addDefaultModuleItem(moduleControllerIron, EnumMetalMaterials.IRON);
		addDefaultModuleItem(moduleControllerBronze, EnumMetalMaterials.BRONZE);

		//Casings
		GameRegistry.register(new ModuleContainer(moduleCasingIron, new ItemStack(BlockManager.blockCasings, 1, 1), EnumMetalMaterials.IRON));
		GameRegistry.register(new ModuleContainer(moduleCasingBronze, new ItemStack(BlockManager.blockCasings, 1, 2), EnumMetalMaterials.BRONZE));

		//Drawers
		GameRegistry.register(new ModuleContainer(moduleDrawerBrick, new ItemStack(ItemManager.itemDrawer, 1, 0), EnumBlockMaterials.BRICK));

		//Boilers
		addDefaultModuleItem(moduleBoilerIron, EnumMetalMaterials.IRON);
		addDefaultModuleItem(moduleBoilerBronze, EnumMetalMaterials.BRONZE);

		//Heaters
		addDefaultModuleItem(moduleHeaterSteamIron, EnumMetalMaterials.IRON);

		addDefaultModuleItem(moduleHeaterBurningIron, EnumMetalMaterials.IRON);
		addDefaultModuleItem(moduleHeaterBronzeLarge, EnumMetalMaterials.BRONZE);
		addDefaultModuleItem(moduleHeaterSteelLarge, EnumMetalMaterials.STEEL);
		addDefaultModuleItem(moduleHeaterMagmariumLarge, EnumMetalMaterials.MAGMARIUM);

		//Alloy Smelters
		addDefaultModuleItem(moduleAlloySmelterIron, EnumMetalMaterials.IRON);
		addDefaultModuleItem(moduleAlloySmelterBronze, EnumMetalMaterials.BRONZE);

		//Pulverizer
		addDefaultModuleItem(modulePulverizerIron, EnumMetalMaterials.IRON);
		addDefaultModuleItem(modulePulverizerBronze, EnumMetalMaterials.BRONZE);
	}

	private static void addDefaultModuleItem(IModule module, IMaterial material){
		GameRegistry.register(new ModuleContainer(module, ItemModule.registerAndCreateItem(module, material), material));
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
	}
}
