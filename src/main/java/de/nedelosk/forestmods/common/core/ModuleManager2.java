package de.nedelosk.forestmods.common.core;

import de.nedelosk.forestmods.common.modules.basic.ModuleCasing;
import de.nedelosk.forestmods.common.modules.engine.ModuleEngine;
import de.nedelosk.forestmods.common.modules.machines.ModuleAlloySmelter;
import de.nedelosk.forestmods.library.ForestModsApi;
import de.nedelosk.forestmods.library.material.EnumMaterials;
import de.nedelosk.forestmods.library.modules.IModuleMachine;
import de.nedelosk.forestmods.library.modules.IModuleRegistry;
import de.nedelosk.forestmods.library.modules.casing.IModuleCasing;
import de.nedelosk.forestmods.library.modules.engine.IModuleEngine;
import net.minecraft.item.ItemStack;

public class ModuleManager2 {

	public static IModuleCasing moduleCasing;
	public static IModuleEngine moduleEngine;
	// Machines
	public static IModuleMachine moduleAlloySmelter;
	/*
	 * public static IModuleHeater moduleBurningHeater; public static
	 * IModuleTank moduleTank; public static IModuleGenerator
	 * moduleBurningGenerator; public static IModuleProducerRecipe
	 * moduleAlloySmelter; public static IModuleProducerRecipe moduleAssembler;
	 * public static IModuleProducerRecipe moduleModuleAssembler; public static
	 * IModuleProducerRecipe moduleLathe; public static IModuleProducerRecipe
	 * moduleCentrifuge; public static IModuleProducerRecipe modulePulverizer;
	 * public static IModuleProducerRecipe moduleSawMill; public static
	 * IModuleTankManager moduleTankManager;
	 */

	public static void registerModuels() {
		IModuleRegistry moduleRegistry = de.nedelosk.forestmods.library.modules.ModuleManager.moduleRegistry;
		moduleCasing = new ModuleCasing("default", 500, 500, 500);
		moduleRegistry.registerModule(EnumMaterials.STONE, "casings", moduleCasing);
		moduleRegistry.registerItemForModule(new ItemStack(BlockManager.blockCasings), new ModuleStack("casings", EnumMaterials.STONE, moduleCasing));
		moduleEngine = new ModuleEngine("default", 150);
		moduleRegistry.registerModule(EnumMaterials.STONE, "engins", moduleEngine);
		moduleRegistry.registerItemForModule(new ItemStack(ItemManager.itemEngine), new ModuleStack("engins", EnumMaterials.STONE, moduleEngine));
		moduleAlloySmelter = new ModuleAlloySmelter("default");
		moduleRegistry.registerModule(EnumMaterials.STONE, "alloysmelters", moduleAlloySmelter);
		ModuleStack stoneAlloySmelter = new ModuleStack("alloysmelters", EnumMaterials.STONE, moduleAlloySmelter);
		moduleRegistry.registerItemForModule(ForestModsApi.handler.addModuleToModuelItem(stoneAlloySmelter), stoneAlloySmelter);
		/*
		 * registerCasings(); registerMachines(); registerManagers();
		 * registerEnergy(); registerStorage(); registerMachine();
		 */
	}
	/*
	 * private static void registerMachine() {
	 * registerModular(ModularMachine.class, "modular.machine"); } private
	 * static void registerStorage() { // registerProducer(new
	 * ItemStack(Blocks.chest), Modules.CHEST, new // ModuleSimpleChest("Chest",
	 * 27), Materials.WOOD); } private static void registerEnergy() {
	 * moduleBurningGenerator = ModuleRegistry.registerModule(new
	 * ModuleHeatGenerator("HeatGenartor")); registerItemForModule(new
	 * ItemStack(Blocks.furnace), moduleBurningGenerator, new
	 * ModuleGeneratorType(100), Materials.STONE); registerItemForModule(new
	 * ItemStack(Blocks.furnace), moduleBurningGenerator, new
	 * ModuleGeneratorType(175), Materials.IRON); registerItemForModule(new
	 * ItemStack(Blocks.furnace), moduleBurningGenerator, new
	 * ModuleGeneratorType(300), Materials.BRONZE); moduleBurningHeater =
	 * ModuleRegistry.registerModule(new ModuleHeaterBurning("Burning"));
	 * registerItemForModule(new ItemStack(ItemManager.itemHeater, 1, 0),
	 * moduleBurningHeater, new ModuleHeaterType(), Materials.STONE);
	 * moduleEngine = ModuleRegistry.registerModule(new
	 * ModuleEngine("Default")); registerItemForModule(new
	 * ItemStack(ItemManager.itemEngine, 1, 0), moduleEngine, new
	 * ModuleEngineType(75), Materials.STONE); registerItemForModule(new
	 * ItemStack(ItemManager.itemEngine, 1, 1), moduleEngine, new
	 * ModuleEngineType(60), Materials.IRON); registerItemForModule(new
	 * ItemStack(ItemManager.itemEngine, 1, 2), moduleEngine, new
	 * ModuleEngineType(50), Materials.BRONZE); registerItemForModule(new
	 * ItemStack(ItemManager.itemEngine, 1, 3), moduleEngine, new
	 * ModuleEngineType(45), Materials.STEEL); registerItemForModule(new
	 * ItemStack(ItemManager.itemEngine, 1, 4), moduleEngine, new
	 * ModuleEngineType(40), Materials.MAGMARIUM); // addModuleToItem(new
	 * ItemStack(ItemManager.itemCapacitors, 1, 0), new //
	 * ModuleCapacitor("metal_paper_capacitor", 10, 20), Materials.IRON); //
	 * addModuleToItem(new ItemStack(ItemManager.itemCapacitors, 1, 1), new //
	 * ModuleCapacitor("electrolyte_niobium_capacitor", 20, 30), //
	 * Materials.IRON); // addModuleToItem(new
	 * ItemStack(ItemManager.itemCapacitors, 1, 2), new //
	 * ModuleCapacitor("electrolyte_tantalum_capacitor", 25, 40), //
	 * Materials.IRON); // addModuleToItem(new
	 * ItemStack(ItemManager.itemCapacitors, 1, 3), new //
	 * ModuleCapacitor("double_layer_capacitor", 40, 60), Materials.BRONZE); }
	 * private static void registerManagers() { // addModule(new
	 * ModuleTankManager(2), Materials.STONE); // addModule(new
	 * ModuleTankManager(4), Materials.BRONZE); // addModule(new
	 * ModuleTankManager(6), Materials.IRON); addModule(new
	 * ModuleStorageManager(1), Materials.STONE); addModule(new
	 * ModuleStorageManager(1), Materials.BRONZE); addModule(new
	 * ModuleStorageManager(2), Materials.IRON); } private static void
	 * registerCasings() { moduleCasing = ModuleRegistry.registerModule(new
	 * ModuleCasing("Default")); registerItemForModule(new ItemStack(Blocks.log,
	 * 1, 0), moduleCasing, new ModuleCasingType(), Materials.WOOD);
	 * registerItemForModule(new ItemStack(Blocks.log, 1, 1), moduleCasing, new
	 * ModuleCasingType(), Materials.WOOD); registerItemForModule(new
	 * ItemStack(Blocks.log, 1, 2), moduleCasing, new ModuleCasingType(),
	 * Materials.WOOD); registerItemForModule(new ItemStack(Blocks.log, 1, 3),
	 * moduleCasing, new ModuleCasingType(), Materials.WOOD);
	 * registerItemForModule(new ItemStack(Blocks.log2, 1, 0), moduleCasing, new
	 * ModuleCasingType(), Materials.WOOD); registerItemForModule(new
	 * ItemStack(Blocks.log2, 1, 1), moduleCasing, new ModuleCasingType(),
	 * Materials.WOOD); registerItemForModule(new ItemStack(Blocks.stone),
	 * moduleCasing, new ModuleCasingType(), Materials.STONE);
	 * registerItemForModule(new ItemStack(BlockManager.blockCasings, 1, 0),
	 * moduleCasing, new ModuleCasingType(), Materials.STONE);
	 * registerItemForModule(new ItemStack(BlockManager.blockCasings, 1, 1),
	 * moduleCasing, new ModuleCasingType(), Materials.STONE);
	 * registerItemForModule(new ItemStack(BlockManager.blockCasings, 1, 2),
	 * moduleCasing, new ModuleCasingType(), Materials.IRON);
	 * registerItemForModule(new ItemStack(BlockManager.blockCasings, 1, 3),
	 * moduleCasing, new ModuleCasingType(), Materials.BRONZE); } private static
	 * void registerMachines() { moduleAlloySmelter =
	 * ModuleRegistry.registerModule(new ModuleAlloySmelter());
	 * addModule(moduleAlloySmelter, new ModuleProducerRecipeType(350),
	 * Materials.STONE); addModule(moduleAlloySmelter, new
	 * ModuleProducerRecipeType(300), Materials.IRON);
	 * addModule(moduleAlloySmelter, new ModuleProducerRecipeType(250),
	 * Materials.BRONZE); moduleAssembler = ModuleRegistry.registerModule(new
	 * ModuleAssembler()); addModule(moduleAssembler, new
	 * ModuleProducerRecipeType(300), Materials.STONE);
	 * addModule(moduleAssembler, new ModuleProducerRecipeType(250),
	 * Materials.IRON); addModule(moduleAssembler, new
	 * ModuleProducerRecipeType(200), Materials.BRONZE); moduleModuleAssembler =
	 * ModuleRegistry.registerModule(new ModuleModuleAssembler());
	 * addModule(moduleModuleAssembler, new ModuleProducerRecipeType(300),
	 * Materials.STONE); addModule(moduleModuleAssembler, new
	 * ModuleProducerRecipeType(250), Materials.IRON);
	 * addModule(moduleModuleAssembler, new ModuleProducerRecipeType(200),
	 * Materials.BRONZE); moduleLathe = ModuleRegistry.registerModule(new
	 * ModuleLathe()); addModule(moduleLathe, new ModuleProducerRecipeType(275),
	 * Materials.IRON); addModule(moduleLathe, new
	 * ModuleProducerRecipeType(225), Materials.BRONZE); moduleSawMill =
	 * ModuleRegistry.registerModule(new ModuleSawMill());
	 * addModule(moduleSawMill, new ModuleProducerRecipeType(350),
	 * Materials.STONE); addModule(moduleSawMill, new
	 * ModuleProducerRecipeType(300), Materials.IRON); addModule(moduleSawMill,
	 * new ModuleProducerRecipeType(250), Materials.BRONZE); modulePulverizer =
	 * ModuleRegistry.registerModule(new ModulePulverizer());
	 * addModule(modulePulverizer, new ModuleProducerRecipeType(350),
	 * Materials.STONE); addModule(modulePulverizer, new
	 * ModuleProducerRecipeType(300), Materials.IRON);
	 * addModule(modulePulverizer, new ModuleProducerRecipeType(250),
	 * Materials.BRONZE); moduleCentrifuge = ModuleRegistry.registerModule(new
	 * ModuleCentrifuge()); addModule(moduleCentrifuge, new
	 * ModuleProducerRecipeType(350), Materials.STONE);
	 * addModule(moduleCentrifuge, new ModuleProducerRecipeType(300),
	 * Materials.IRON); addModule(moduleCentrifuge, new
	 * ModuleProducerRecipeType(250), Materials.BRONZE); addModule(new
	 * ModuleBurningBoiler(15, 100, 1000), Materials.STONE); addModule(new
	 * ModuleBurningBoiler(13, 250, 1500), Materials.IRON); addModule(new
	 * ModuleBurningBoiler(10, 500, 2000), Materials.BRONZE); }
	 */
}
