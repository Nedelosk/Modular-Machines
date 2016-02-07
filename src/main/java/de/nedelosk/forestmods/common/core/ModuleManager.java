package de.nedelosk.forestmods.common.core;

import static de.nedelosk.forestmods.api.utils.ModuleRegistry.addModuleToItem;
import static de.nedelosk.forestmods.api.utils.ModuleRegistry.registerCategory;
import static de.nedelosk.forestmods.api.utils.ModuleRegistry.registerModular;
import static de.nedelosk.forestmods.common.items.ItemModule.addModule;

import de.nedelosk.forestmods.api.modular.material.Materials;
import de.nedelosk.forestmods.api.modules.basic.IModuleCasing;
import de.nedelosk.forestmods.api.modules.basic.ModuleCategory;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipe;
import de.nedelosk.forestmods.api.modules.managers.fluids.IModuleTankManager;
import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBattery;
import de.nedelosk.forestmods.api.modules.storage.tanks.IModuleTank;
import de.nedelosk.forestmods.api.utils.ModuleCategoryUIDs;
import de.nedelosk.forestmods.api.utils.ModuleRegistry;
import de.nedelosk.forestmods.common.modular.ModularMachine;
import de.nedelosk.forestmods.common.modules.basic.ModuleCasing;
import de.nedelosk.forestmods.common.modules.basic.ModuleCasingType;
import de.nedelosk.forestmods.common.modules.engine.ModuleEngineEnergy;
import de.nedelosk.forestmods.common.modules.engine.ModuleEngineType;
import de.nedelosk.forestmods.common.modules.machines.recipe.ModuleMachineRecipeType;
import de.nedelosk.forestmods.common.modules.machines.recipe.alloysmelter.ModuleAlloySmelter;
import de.nedelosk.forestmods.common.modules.machines.recipe.assembler.ModuleAssembler;
import de.nedelosk.forestmods.common.modules.machines.recipe.assembler.module.ModuleModuleAssembler;
import de.nedelosk.forestmods.common.modules.machines.recipe.centrifuge.ModuleCentrifuge;
import de.nedelosk.forestmods.common.modules.machines.recipe.mode.lathe.ModuleLathe;
import de.nedelosk.forestmods.common.modules.machines.recipe.pulverizer.ModulePulverizer;
import de.nedelosk.forestmods.common.modules.machines.recipe.sawmill.ModuleSawMill;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ModuleManager {

	public static IModuleEngine moduleEngine;
	public static IModuleCasing moduleCasing;
	public static IModuleTank moduleTank;
	public static IModuleBattery moduleBattery;
	public static IModuleMachineRecipe moduleAlloySmelter;
	public static IModuleMachineRecipe moduleAssembler;
	public static IModuleMachineRecipe moduleModuleAssembler;
	public static IModuleMachineRecipe moduleLathe;
	public static IModuleMachineRecipe moduleCentrifuge;
	public static IModuleMachineRecipe modulePulverizer;
	public static IModuleMachineRecipe moduleSawMill;
	public static IModuleTankManager moduleTankManager;

	public static void registerModuels() {
		registerCasings();
		registerMachines();
		registerManagers();
		registerEnergy();
		registerStorage();
		registerMachine();
	}

	private static void registerMachine() {
		registerModular(ModularMachine.class, "modular.machine");
	}

	private static void registerStorage() {
		// registerProducer(new ItemStack(Blocks.chest), Modules.CHEST, new
		// ModuleSimpleChest("Chest", 27), Materials.WOOD);
	}

	public static void registerCategorys() {
		registerCategory(new ModuleCategory(ModuleCategoryUIDs.CASING, false));
		registerCategory(new ModuleCategory(ModuleCategoryUIDs.TANK, false));
		registerCategory(new ModuleCategory(ModuleCategoryUIDs.MACHINE, false));
		registerCategory(new ModuleCategory(ModuleCategoryUIDs.ENGINE, false));
		registerCategory(new ModuleCategory(ModuleCategoryUIDs.MANAGERS, true));
		registerCategory(new ModuleCategory(ModuleCategoryUIDs.BATTERY, false));
		registerCategory(new ModuleCategory(ModuleCategoryUIDs.CAPACITOR, false));
	}

	private static void registerEnergy() {
		/*
		 * registerProducer(addModule(new ModuleStack(Modules.GENERATOR, new
		 * ProducerBurningGenerator(15, 5), Materials.STONE, true)));
		 * registerProducer(addModule(new ModuleStack(Modules.GENERATOR, new
		 * ProducerBurningGenerator(13, 15), Materials.IRON, true)));
		 * registerProducer(addModule(new ModuleStack(Modules.GENERATOR, new
		 * ProducerBurningGenerator(10, 45), Materials.BRONZE, true)));
		 */
		moduleEngine = ModuleRegistry.registerModule(new ModuleEngineEnergy("Noraml"));
		addModuleToItem(new ItemStack(ItemManager.itemEngines, 1, 0), moduleEngine, new ModuleEngineType(60), Materials.IRON);
		addModuleToItem(new ItemStack(ItemManager.itemEngines, 1, 1), moduleEngine, new ModuleEngineType(50), Materials.BRONZE);
		addModuleToItem(new ItemStack(ItemManager.itemEngines, 1, 2), moduleEngine, new ModuleEngineType(45), Materials.STEEL);
		addModuleToItem(new ItemStack(ItemManager.itemEngines, 1, 3), moduleEngine, new ModuleEngineType(40), Materials.MAGMARIUM);
		// addModuleToItem(new ItemStack(ItemManager.itemCapacitors, 1, 0), new
		// ModuleCapacitor("metal_paper_capacitor", 10, 20), Materials.IRON);
		// addModuleToItem(new ItemStack(ItemManager.itemCapacitors, 1, 1), new
		// ModuleCapacitor("electrolyte_niobium_capacitor", 20, 30),
		// Materials.IRON);
		// addModuleToItem(new ItemStack(ItemManager.itemCapacitors, 1, 2), new
		// ModuleCapacitor("electrolyte_tantalum_capacitor", 25, 40),
		// Materials.IRON);
		// addModuleToItem(new ItemStack(ItemManager.itemCapacitors, 1, 3), new
		// ModuleCapacitor("double_layer_capacitor", 40, 60), Materials.BRONZE);
	}

	private static void registerManagers() {
		// addModule(new ModuleTankManager(2), Materials.STONE);
		// addModule(new ModuleTankManager(4), Materials.BRONZE);
		// addModule(new ModuleTankManager(6), Materials.IRON);
		/*
		 * addModule(new ModuleStorageManager(1), Materials.STONE);
		 * addModule(new ModuleStorageManager(1), Materials.BRONZE);
		 * addModule(new ModuleStorageManager(2), Materials.IRON);
		 */
	}

	private static void registerCasings() {
		moduleCasing = ModuleRegistry.registerModule(new ModuleCasing("Default"));
		addModuleToItem(new ItemStack(Blocks.log, 1, 0), moduleCasing, new ModuleCasingType(), Materials.WOOD);
		addModuleToItem(new ItemStack(Blocks.log, 1, 1), moduleCasing, new ModuleCasingType(), Materials.WOOD);
		addModuleToItem(new ItemStack(Blocks.log, 1, 2), moduleCasing, new ModuleCasingType(), Materials.WOOD);
		addModuleToItem(new ItemStack(Blocks.log, 1, 3), moduleCasing, new ModuleCasingType(), Materials.WOOD);
		addModuleToItem(new ItemStack(Blocks.log2, 1, 0), moduleCasing, new ModuleCasingType(), Materials.WOOD);
		addModuleToItem(new ItemStack(Blocks.log2, 1, 1), moduleCasing, new ModuleCasingType(), Materials.WOOD);
		addModuleToItem(new ItemStack(Blocks.stone), moduleCasing, new ModuleCasingType(), Materials.STONE);
		addModuleToItem(new ItemStack(BlockManager.blockCasings, 1, 0), moduleCasing, new ModuleCasingType(), Materials.STONE);
		addModuleToItem(new ItemStack(BlockManager.blockCasings, 1, 1), moduleCasing, new ModuleCasingType(), Materials.STONE);
		addModuleToItem(new ItemStack(BlockManager.blockCasings, 1, 2), moduleCasing, new ModuleCasingType(), Materials.IRON);
		addModuleToItem(new ItemStack(BlockManager.blockCasings, 1, 3), moduleCasing, new ModuleCasingType(), Materials.BRONZE);
	}

	private static void registerMachines() {
		moduleAlloySmelter = ModuleRegistry.registerModule(new ModuleAlloySmelter());
		addModule(moduleAlloySmelter, new ModuleMachineRecipeType(350), Materials.STONE);
		addModule(moduleAlloySmelter, new ModuleMachineRecipeType(300), Materials.IRON);
		addModule(moduleAlloySmelter, new ModuleMachineRecipeType(250), Materials.BRONZE);
		moduleAssembler = ModuleRegistry.registerModule(new ModuleAssembler());
		addModule(moduleAssembler, new ModuleMachineRecipeType(300), Materials.STONE);
		addModule(moduleAssembler, new ModuleMachineRecipeType(250), Materials.IRON);
		addModule(moduleAssembler, new ModuleMachineRecipeType(200), Materials.BRONZE);
		moduleModuleAssembler = ModuleRegistry.registerModule(new ModuleModuleAssembler());
		addModule(moduleModuleAssembler, new ModuleMachineRecipeType(300), Materials.STONE);
		addModule(moduleModuleAssembler, new ModuleMachineRecipeType(250), Materials.IRON);
		addModule(moduleModuleAssembler, new ModuleMachineRecipeType(200), Materials.BRONZE);
		moduleLathe = ModuleRegistry.registerModule(new ModuleLathe());
		addModule(moduleLathe, new ModuleMachineRecipeType(275), Materials.IRON);
		addModule(moduleLathe, new ModuleMachineRecipeType(225), Materials.BRONZE);
		moduleSawMill = ModuleRegistry.registerModule(new ModuleSawMill());
		addModule(moduleSawMill, new ModuleMachineRecipeType(350), Materials.STONE);
		addModule(moduleSawMill, new ModuleMachineRecipeType(300), Materials.IRON);
		addModule(moduleSawMill, new ModuleMachineRecipeType(250), Materials.BRONZE);
		modulePulverizer = ModuleRegistry.registerModule(new ModulePulverizer());
		addModule(modulePulverizer, new ModuleMachineRecipeType(350), Materials.STONE);
		addModule(modulePulverizer, new ModuleMachineRecipeType(300), Materials.IRON);
		addModule(modulePulverizer, new ModuleMachineRecipeType(250), Materials.BRONZE);
		moduleCentrifuge = ModuleRegistry.registerModule(new ModuleCentrifuge());
		addModule(moduleCentrifuge, new ModuleMachineRecipeType(350), Materials.STONE);
		addModule(moduleCentrifuge, new ModuleMachineRecipeType(300), Materials.IRON);
		addModule(moduleCentrifuge, new ModuleMachineRecipeType(250), Materials.BRONZE);
		/*
		 * addModule(new ModuleBurningBoiler(15, 100, 1000), Materials.STONE);
		 * addModule(new ModuleBurningBoiler(13, 250, 1500), Materials.IRON);
		 * addModule(new ModuleBurningBoiler(10, 500, 2000), Materials.BRONZE);
		 */
	}
}
