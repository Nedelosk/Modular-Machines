package de.nedelosk.modularmachines.common.core;

import java.util.concurrent.Callable;

import de.nedelosk.modularmachines.api.material.EnumMaterials;
import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.IModuleController;
import de.nedelosk.modularmachines.api.modules.engine.IModuleEngine;
import de.nedelosk.modularmachines.api.modules.heater.IModuleHeater;
import de.nedelosk.modularmachines.common.items.ItemModule;
import de.nedelosk.modularmachines.common.modular.AssemblerLogicBasic;
import de.nedelosk.modularmachines.common.modular.handlers.ModularHandler;
import de.nedelosk.modularmachines.common.modules.ModuleCasing;
import de.nedelosk.modularmachines.common.modules.ModuleContainer;
import de.nedelosk.modularmachines.common.modules.ModuleController;
import de.nedelosk.modularmachines.common.modules.engine.ModuleEngine;
import de.nedelosk.modularmachines.common.modules.engine.ModuleEngineEnergy;
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
		moduleCasingIron = new ModuleCasing(400, 10.0F, 5.0F, "pickaxe", 1);
		moduleCasingIron.setRegistryName(new ResourceLocation("modularmachines:casing.iron"));
		GameRegistry.register(moduleCasingIron);

		moduleCasingBronze = new ModuleCasing(550, 10.0F, 1.5F, "pickaxe", 1);
		moduleCasingBronze.setRegistryName(new ResourceLocation("modularmachines:casing.bronze"));
		GameRegistry.register(moduleCasingBronze);

		/* ENGINES */
		moduleEngineIron = new ModuleEngineEnergy(2, 15);
		moduleEngineIron.setRegistryName(new ResourceLocation("modularmachines:engine.iron"));
		GameRegistry.register(moduleEngineIron);

		moduleEngineBronze = new ModuleEngineEnergy(3, 15);
		moduleEngineBronze.setRegistryName(new ResourceLocation("modularmachines:engine.bronze"));
		GameRegistry.register(moduleEngineBronze);

		moduleEngineSteel = new ModuleEngineEnergy(3, 15);
		moduleEngineSteel.setRegistryName(new ResourceLocation("modularmachines:engine.steel"));
		GameRegistry.register(moduleEngineSteel);

		moduleEngineMagmarium = new ModuleEngineEnergy(4, 15);
		moduleEngineMagmarium.setRegistryName(new ResourceLocation("modularmachines:engine.magmarium"));
		GameRegistry.register(moduleEngineMagmarium);
		
		/* HEATERS */
		
		//Steam
		moduleHeaterSteamIron = new ModuleHeaterSteam();
		moduleHeaterSteamIron.setRegistryName(new ResourceLocation("modularmachines:heater.steam.iron"));
		GameRegistry.register(moduleHeaterSteamIron);
		
		//Heat
		moduleHeaterBurningIron = new ModuleHeaterBurning(350, 3);
		moduleHeaterBurningIron.setRegistryName(new ResourceLocation("modularmachines:heater.iron.large"));
		GameRegistry.register(moduleHeaterBurningIron);

		moduleHeaterBronzeLarge = new ModuleHeaterBurning(450, 3);
		moduleHeaterBronzeLarge.setRegistryName(new ResourceLocation("modularmachines:heater.bronze.large"));
		GameRegistry.register(moduleHeaterBronzeLarge);

		moduleHeaterBronzeMiddle = new ModuleHeaterBurning(350, 2);
		moduleHeaterBronzeMiddle.setRegistryName(new ResourceLocation("modularmachines:heater.bronze.middle"));
		GameRegistry.register(moduleHeaterBronzeMiddle);

		moduleHeaterSteelLarge = new ModuleHeaterBurning(550, 3);
		moduleHeaterSteelLarge.setRegistryName(new ResourceLocation("modularmachines:heater.steel.large"));
		GameRegistry.register(moduleHeaterSteelLarge);

		moduleHeaterSteelMiddle = new ModuleHeaterBurning(450, 2);
		moduleHeaterSteelMiddle.setRegistryName(new ResourceLocation("modularmachines:heater.steel.middle"));
		GameRegistry.register(moduleHeaterSteelMiddle);

		moduleHeaterSteelSmall = new ModuleHeaterBurning(350, 1);
		moduleHeaterSteelSmall.setRegistryName(new ResourceLocation("modularmachines:heater.steel.small"));
		GameRegistry.register(moduleHeaterSteelSmall);

		moduleHeaterMagmariumLarge = new ModuleHeaterBurning(700, 3);
		moduleHeaterMagmariumLarge.setRegistryName(new ResourceLocation("modularmachines:engine.magmarium.large"));
		GameRegistry.register(moduleHeaterMagmariumLarge);

		moduleHeaterMagmariumMiddle = new ModuleHeaterBurning(600, 3);
		moduleHeaterMagmariumMiddle.setRegistryName(new ResourceLocation("modularmachines:engine.magmarium.middle"));
		GameRegistry.register(moduleHeaterMagmariumMiddle);

		moduleHeaterMagmariumSmall = new ModuleHeaterBurning(500, 3);
		moduleHeaterMagmariumSmall.setRegistryName(new ResourceLocation("modularmachines:engine.magmarium.small"));
		GameRegistry.register(moduleHeaterMagmariumSmall);

		/* BOILERS */
		moduleBoilerIron = new ModuleBoiler(22, 3);
		moduleBoilerIron.setRegistryName(new ResourceLocation("modularmachines:boiler.iron"));
		GameRegistry.register(moduleBoilerIron);

		moduleBoilerBronze = new ModuleBoiler(15, 3);
		moduleBoilerBronze.setRegistryName(new ResourceLocation("modularmachines:boiler.bronze"));
		GameRegistry.register(moduleBoilerBronze);

		/* PULVERIZER */
		modulePulverizerIron = new ModulePulverizer(35, 3);
		modulePulverizerIron.setRegistryName(new ResourceLocation("modularmachines:pulverizer.iron"));
		GameRegistry.register(modulePulverizerIron);

		modulePulverizerBronze = new ModulePulverizer(25, 3);
		modulePulverizerBronze.setRegistryName(new ResourceLocation("modularmachines:pulverizer.bronze"));
		GameRegistry.register(modulePulverizerBronze);
		
		/* ALLOY SMELTERS*/
		moduleAlloySmelterIron = new ModuleAlloySmelter(35, 3);
		moduleAlloySmelterIron.setRegistryName(new ResourceLocation("modularmachines:alloysmelter.iron"));
		GameRegistry.register(moduleAlloySmelterIron);

		moduleAlloySmelterBronze = new ModuleAlloySmelter(25, 3);
		moduleAlloySmelterBronze.setRegistryName(new ResourceLocation("modularmachines:alloysmelter.bronze"));
		GameRegistry.register(moduleAlloySmelterBronze);

		//RecipeRegistry.registerRecipeHandler(new RecipeAdvancedHandler(LatheModes.class, "Lathe"));
		ModularManager.registerAssemblerLogic(new AssemblerLogicBasic());
	}

	public static void registerModuleContainers(){
		//Engines
		GameRegistry.register(new ModuleContainer(moduleEngineIron, new ItemStack(ItemManager.itemEngine, 1, 0), EnumMaterials.IRON));
		GameRegistry.register(new ModuleContainer(moduleEngineBronze, new ItemStack(ItemManager.itemEngine, 1, 1), EnumMaterials.BRONZE));
		GameRegistry.register(new ModuleContainer(moduleEngineSteel, new ItemStack(ItemManager.itemEngine, 1, 2), EnumMaterials.STEEL));
		GameRegistry.register(new ModuleContainer(moduleEngineMagmarium, new ItemStack(ItemManager.itemEngine, 1, 3), EnumMaterials.MAGMARIUM));
		
		//Controller
		addDefaultModuleItem(moduleControllerIron, EnumMaterials.IRON);
		addDefaultModuleItem(moduleControllerBronze, EnumMaterials.BRONZE);

		//Casings
		GameRegistry.register(new ModuleContainer(moduleCasingIron, new ItemStack(BlockManager.blockCasings, 1, 1), EnumMaterials.IRON));
		GameRegistry.register(new ModuleContainer(moduleCasingBronze, new ItemStack(BlockManager.blockCasings, 1, 2), EnumMaterials.BRONZE));

		//Boilers
		addDefaultModuleItem(moduleBoilerIron, EnumMaterials.IRON);
		addDefaultModuleItem(moduleBoilerBronze, EnumMaterials.BRONZE);

		//Heaters
		addDefaultModuleItem(moduleHeaterSteamIron, EnumMaterials.IRON);
		
		addDefaultModuleItem(moduleHeaterBurningIron, EnumMaterials.IRON);
		addDefaultModuleItem(moduleHeaterBronzeLarge, EnumMaterials.BRONZE);
		addDefaultModuleItem(moduleHeaterSteelLarge, EnumMaterials.STEEL);
		addDefaultModuleItem(moduleHeaterMagmariumLarge, EnumMaterials.MAGMARIUM);

		//Alloy Smelters
		addDefaultModuleItem(moduleAlloySmelterIron, EnumMaterials.IRON);
		addDefaultModuleItem(moduleAlloySmelterBronze, EnumMaterials.BRONZE);
		
		//Pulverizer
		addDefaultModuleItem(modulePulverizerIron, EnumMaterials.IRON);
		addDefaultModuleItem(modulePulverizerBronze, EnumMaterials.BRONZE);
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
