package de.nedelosk.modularmachines.common.core;

import java.util.concurrent.Callable;

import de.nedelosk.modularmachines.api.material.EnumMaterials;
import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.engine.IModuleEngine;
import de.nedelosk.modularmachines.api.modules.heater.IModuleHeater;
import de.nedelosk.modularmachines.api.recipes.RecipeRegistry;
import de.nedelosk.modularmachines.common.items.ItemModule;
import de.nedelosk.modularmachines.common.modular.handlers.ModularHandler;
import de.nedelosk.modularmachines.common.modules.ModuleCasing;
import de.nedelosk.modularmachines.common.modules.ModuleContainer;
import de.nedelosk.modularmachines.common.modules.engine.ModuleEngine;
import de.nedelosk.modularmachines.common.modules.heater.ModuleHeaterBurning;
import de.nedelosk.modularmachines.common.modules.tools.ModuleAlloySmelter;
import de.nedelosk.modularmachines.common.modules.tools.ModuleBoiler;
import de.nedelosk.modularmachines.common.modules.tools.ModuleFurnace;
import de.nedelosk.modularmachines.common.modules.tools.ModuleLathe;
import de.nedelosk.modularmachines.common.modules.tools.ModulePulverizer;
import de.nedelosk.modularmachines.common.modules.tools.ModuleSawMill;
import de.nedelosk.modularmachines.common.modules.tools.recipe.RecipeHandlerBoiler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModuleManager {

	public static IModuleCasing moduleCasingStone;
	public static IModuleCasing moduleCasingIron;
	public static IModuleCasing moduleCasingBronze;

	public static IModuleEngine moduleEngineStone;
	public static IModuleEngine moduleEngineIron;
	public static IModuleEngine moduleEngineBronze;
	public static IModuleEngine moduleEngineSteel;
	public static IModuleEngine moduleEngineMagmarium;

	public static IModuleHeater moduleHeaterStone;
	public static IModuleHeater moduleHeaterIronLarge;
	public static IModuleHeater moduleHeaterIronMiddle;
	public static IModuleHeater moduleHeaterBronzeLarge;
	public static IModuleHeater moduleHeaterBronzeMiddle;
	public static IModuleHeater moduleHeaterSteelLarge;
	public static IModuleHeater moduleHeaterSteelMiddle;
	public static IModuleHeater moduleHeaterSteelSmall;
	public static IModuleHeater moduleHeaterMagmariumLarge;
	public static IModuleHeater moduleHeaterMagmariumMiddle;
	public static IModuleHeater moduleHeaterMagmariumSmall;

	public static ModuleBoiler moduleBoilerStone;
	public static ModuleBoiler moduleBoilerIron;
	public static ModuleBoiler moduleBoilerBronze;

	public static ModuleFurnace moduleFurnaceStone;
	public static ModuleFurnace moduleFurnaceIron;
	public static ModuleFurnace moduleFurnaceBronze;

	public static ModuleAlloySmelter moduleAlloySmelterStone;
	public static ModuleAlloySmelter moduleAlloySmelterIron;
	public static ModuleAlloySmelter moduleAlloySmelterBronze;

	public static ModuleSawMill moduleSawMillStone;
	public static ModuleSawMill moduleSawMillIron;
	public static ModuleSawMill moduleSawMillBronze;

	public static ModulePulverizer modulePulverizerStone;
	public static ModulePulverizer modulePulverizerIron;
	public static ModulePulverizer modulePulverizerBronze;

	public static ModuleLathe moduleLatheStone;
	public static ModuleLathe moduleLatheIron;
	public static ModuleLathe moduleLatheBronze;
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
		/* CASINGS */
		moduleCasingStone = new ModuleCasing(250, 10.0F, 1.5F, 4, "pickaxe", 0);
		moduleCasingStone.setRegistryName(new ResourceLocation("modularmachines:casing.stone"));
		GameRegistry.register(moduleCasingStone);

		moduleCasingIron = new ModuleCasing(400, 10.0F, 5.0F, 5, "pickaxe", 1);
		moduleCasingIron.setRegistryName(new ResourceLocation("modularmachines:casing.iron"));
		GameRegistry.register(moduleCasingIron);

		moduleCasingBronze = new ModuleCasing(550, 10.0F, 1.5F, 4, "pickaxe", 1);
		moduleCasingBronze.setRegistryName(new ResourceLocation("modularmachines:casing.bronze"));
		GameRegistry.register(moduleCasingBronze);

		/* ENGINES */
		moduleEngineStone = new ModuleEngine(1);
		moduleEngineStone.setRegistryName(new ResourceLocation("modularmachines:engine.stone"));
		GameRegistry.register(moduleEngineStone);

		moduleEngineIron = new ModuleEngine(2);
		moduleEngineIron.setRegistryName(new ResourceLocation("modularmachines:engine.iron"));
		GameRegistry.register(moduleEngineIron);

		moduleEngineBronze = new ModuleEngine(3);
		moduleEngineBronze.setRegistryName(new ResourceLocation("modularmachines:engine.bronze"));
		GameRegistry.register(moduleEngineBronze);

		moduleEngineSteel = new ModuleEngine(3);
		moduleEngineSteel.setRegistryName(new ResourceLocation("modularmachines:engine.steel"));
		GameRegistry.register(moduleEngineSteel);

		moduleEngineMagmarium = new ModuleEngine(4);
		moduleEngineMagmarium.setRegistryName(new ResourceLocation("modularmachines:engine.magmarium"));
		GameRegistry.register(moduleEngineMagmarium);

		/* HEATERS */
		moduleHeaterStone = new ModuleHeaterBurning(250, 3);
		moduleHeaterStone.setRegistryName(new ResourceLocation("modularmachines:heater.stone"));
		GameRegistry.register(moduleHeaterStone);

		moduleHeaterIronLarge = new ModuleHeaterBurning(350, 3);
		moduleHeaterIronLarge.setRegistryName(new ResourceLocation("modularmachines:heater.iron.large"));
		GameRegistry.register(moduleHeaterIronLarge);

		moduleHeaterIronMiddle = new ModuleHeaterBurning(250, 2);
		moduleHeaterIronMiddle.setRegistryName(new ResourceLocation("modularmachines:heater.iron.middle"));
		GameRegistry.register(moduleHeaterIronMiddle);

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
		moduleBoilerStone = new ModuleBoiler(15, 3);
		moduleBoilerStone.setRegistryName(new ResourceLocation("modularmachines:boiler.stone"));
		GameRegistry.register(moduleBoilerStone);

		moduleBoilerIron = new ModuleBoiler(12, 3);
		moduleBoilerIron.setRegistryName(new ResourceLocation("modularmachines:boiler.iron"));
		GameRegistry.register(moduleBoilerIron);

		moduleBoilerBronze = new ModuleBoiler(10, 3);
		moduleBoilerBronze.setRegistryName(new ResourceLocation("modularmachines:boiler.bronze"));
		GameRegistry.register(moduleBoilerBronze);

		RecipeRegistry.registerRecipeHandler(new RecipeHandlerBoiler());

		/*IModuleRegistry moduleRegistry = de.nedelosk.modularmachines.api.modules.ModuleManager.moduleRegistry;
		moduleCasing = new ModuleCasing("default", 500, 500, 500);
		moduleRegistry.registerModule(EnumMaterials.STONE, "casings", moduleCasing);
		moduleRegistry.registerModuleContainer(new ItemStack(BlockManager.blockCasings), new ModuleStack("casings", EnumMaterials.STONE, moduleCasing));
		moduleEngine = new ModuleEngine("default", 150);
		moduleRegistry.registerModule(EnumMaterials.STONE, "engins", moduleEngine);
		moduleRegistry.registerModuleContainer(new ItemStack(ItemManager.itemEngine), new ModuleStack("engins", EnumMaterials.STONE, moduleEngine));
		moduleAlloySmelter = new ModuleAlloySmelter("default");
		moduleRegistry.registerModule(EnumMaterials.STONE, "alloysmelters", moduleAlloySmelter);
		ModuleStack stoneAlloySmelter = new ModuleStack("alloysmelters", EnumMaterials.STONE, moduleAlloySmelter);
		moduleRegistry.registerModuleContainer(ModularMachinesApi.handler.addModuleToModuelItem(stoneAlloySmelter), stoneAlloySmelter);*/
		/*
		 * registerCasings(); registerMachines(); registerManagers();
		 * registerEnergy(); registerStorage(); registerMachine();
		 */
	}

	public static void registerModuleContainers(){
		//Casings
		GameRegistry.register(new ModuleContainer(moduleCasingStone, new ItemStack(BlockManager.blockCasings, 1, 0), EnumMaterials.STONE));
		GameRegistry.register(new ModuleContainer(moduleCasingIron, new ItemStack(BlockManager.blockCasings, 1, 1), EnumMaterials.IRON));
		GameRegistry.register(new ModuleContainer(moduleCasingBronze, new ItemStack(BlockManager.blockCasings, 1, 2), EnumMaterials.BRONZE));
		//Boilers
		addDefaultModuleItem(moduleBoilerStone, EnumMaterials.STONE);
		addDefaultModuleItem(moduleBoilerIron, EnumMaterials.IRON);
		addDefaultModuleItem(moduleBoilerBronze, EnumMaterials.BRONZE);

		//Heaters
		GameRegistry.register(new ModuleContainer(moduleHeaterStone, new ItemStack(ItemManager.itemHeater, 1, 0), EnumMaterials.STONE));
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
