package de.nedelosk.forestmods.common.core;

import static de.nedelosk.forestmods.api.utils.ModuleRegistry.addModuleToItem;
import static de.nedelosk.forestmods.api.utils.ModuleRegistry.registerCategory;
import static de.nedelosk.forestmods.api.utils.ModuleRegistry.registerModular;
import static de.nedelosk.forestmods.common.items.ItemModule.addModule;

import de.nedelosk.forestmods.api.modular.material.Materials;
import de.nedelosk.forestmods.api.modules.basic.ModuleCategory;
import de.nedelosk.forestmods.api.utils.ModuleCategoryUIDs;
import de.nedelosk.forestmods.common.modular.ModularMachine;
import de.nedelosk.forestmods.common.modules.basic.ModuleCasing;
import de.nedelosk.forestmods.common.modules.engine.ModuleEngineEnergy;
import de.nedelosk.forestmods.common.modules.machines.recipe.alloysmelter.ModuleAlloySmelter;
import de.nedelosk.forestmods.common.modules.machines.recipe.assembler.ModuleAssembler;
import de.nedelosk.forestmods.common.modules.machines.recipe.assembler.module.ModuleModuleAssembler;
import de.nedelosk.forestmods.common.modules.machines.recipe.centrifuge.ModuleCentrifuge;
import de.nedelosk.forestmods.common.modules.machines.recipe.mode.lathe.ModuleLathe;
import de.nedelosk.forestmods.common.modules.machines.recipe.pulverizer.ModulePulverizer;
import de.nedelosk.forestmods.common.modules.machines.recipe.sawmill.ModuleSawMill;
import de.nedelosk.forestmods.common.modules.managers.ModuleTankManager;
import de.nedelosk.forestmods.common.modules.storage.ModuleCapacitor;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ModuleManager {

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
		addModuleToItem(new ItemStack(ItemManager.itemEngines, 1, 0), new ModuleEngineEnergy("EngineIron", 60), Materials.IRON);
		addModuleToItem(new ItemStack(ItemManager.itemEngines, 1, 1), new ModuleEngineEnergy("EngineBronze", 50), Materials.BRONZE);
		addModuleToItem(new ItemStack(ItemManager.itemEngines, 1, 2), new ModuleEngineEnergy("EngineSteel", 45), Materials.STEEL);
		addModuleToItem(new ItemStack(ItemManager.itemEngines, 1, 3), new ModuleEngineEnergy("EngineMagmarium", 40), Materials.MAGMARIUM);
		addModuleToItem(new ItemStack(ItemManager.itemCapacitors, 1, 0), new ModuleCapacitor("metal_paper_capacitor", 10, 20), Materials.IRON);
		addModuleToItem(new ItemStack(ItemManager.itemCapacitors, 1, 1), new ModuleCapacitor("electrolyte_niobium_capacitor", 20, 30), Materials.IRON);
		addModuleToItem(new ItemStack(ItemManager.itemCapacitors, 1, 2), new ModuleCapacitor("electrolyte_tantalum_capacitor", 25, 40), Materials.IRON);
		addModuleToItem(new ItemStack(ItemManager.itemCapacitors, 1, 3), new ModuleCapacitor("double_layer_capacitor", 40, 60), Materials.BRONZE);
	}

	private static void registerManagers() {
		addModule(new ModuleTankManager(2), Materials.STONE);
		addModule(new ModuleTankManager(4), Materials.BRONZE);
		addModule(new ModuleTankManager(6), Materials.IRON);
		/*
		 * addModule(new ModuleStorageManager(1), Materials.STONE);
		 * addModule(new ModuleStorageManager(1), Materials.BRONZE);
		 * addModule(new ModuleStorageManager(2), Materials.IRON);
		 */
	}

	private static void registerCasings() {
		addModuleToItem(new ItemStack(Blocks.log, 1, 0), new ModuleCasing("wood_oak"), Materials.WOOD);
		addModuleToItem(new ItemStack(Blocks.log, 1, 1), new ModuleCasing("wood_bspruce"), Materials.WOOD);
		addModuleToItem(new ItemStack(Blocks.log, 1, 2), new ModuleCasing("wood_birch"), Materials.WOOD);
		addModuleToItem(new ItemStack(Blocks.log, 1, 3), new ModuleCasing("wood_jungle"), Materials.WOOD);
		addModuleToItem(new ItemStack(Blocks.log2, 1, 0), new ModuleCasing("wood_acacia"), Materials.WOOD);
		addModuleToItem(new ItemStack(Blocks.log2, 1, 1), new ModuleCasing("wood_dark_oak"), Materials.WOOD);
		addModuleToItem(new ItemStack(Blocks.stone), new ModuleCasing("stone"), Materials.STONE);
		addModuleToItem(new ItemStack(BlockManager.blockCasings, 1, 0), new ModuleCasing("casing_stone"), Materials.STONE);
		addModuleToItem(new ItemStack(BlockManager.blockCasings, 1, 1), new ModuleCasing("casing_stone_brick"), Materials.STONE);
		addModuleToItem(new ItemStack(BlockManager.blockCasings, 1, 2), new ModuleCasing("casing_iron"), Materials.IRON);
		addModuleToItem(new ItemStack(BlockManager.blockCasings, 1, 3), new ModuleCasing("casing_bronze"), Materials.BRONZE);
	}

	private static void registerMachines() {
		addModule(new ModuleAlloySmelter("stone", 350), Materials.STONE);
		addModule(new ModuleAlloySmelter("iron", 300), Materials.IRON);
		addModule(new ModuleAlloySmelter("bronze", 250), Materials.BRONZE);
		addModule(new ModuleAssembler("stone", 300), Materials.STONE);
		addModule(new ModuleAssembler("iron", 250), Materials.IRON);
		addModule(new ModuleAssembler("bronze", 200), Materials.BRONZE);
		addModule(new ModuleModuleAssembler("stone", 300), Materials.STONE);
		addModule(new ModuleModuleAssembler("iron", 250), Materials.IRON);
		addModule(new ModuleModuleAssembler("bronze", 200), Materials.BRONZE);
		addModule(new ModuleLathe("iron", 275), Materials.IRON);
		addModule(new ModuleLathe("bronze", 225), Materials.BRONZE);
		addModule(new ModuleSawMill("stone", 350), Materials.STONE);
		addModule(new ModuleSawMill("iron", 300), Materials.IRON);
		addModule(new ModuleSawMill("bronze", 250), Materials.BRONZE);
		addModule(new ModulePulverizer("stone", 350), Materials.STONE);
		addModule(new ModulePulverizer("iron", 300), Materials.IRON);
		addModule(new ModulePulverizer("bronze", 250), Materials.BRONZE);
		addModule(new ModuleCentrifuge("stone", 350), Materials.STONE);
		addModule(new ModuleCentrifuge("iron", 300), Materials.IRON);
		addModule(new ModuleCentrifuge("bronze", 250), Materials.BRONZE);
		/*
		 * addModule(new ModuleBurningBoiler(15, 100, 1000), Materials.STONE);
		 * addModule(new ModuleBurningBoiler(13, 250, 1500), Materials.IRON);
		 * addModule(new ModuleBurningBoiler(10, 500, 2000), Materials.BRONZE);
		 */
	}
}
