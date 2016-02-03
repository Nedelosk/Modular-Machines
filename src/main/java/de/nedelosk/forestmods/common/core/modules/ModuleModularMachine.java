package de.nedelosk.forestmods.common.core.modules;

import static de.nedelosk.forestmods.api.recipes.RecipeRegistry.registerRecipe;
import static de.nedelosk.forestmods.api.utils.ModuleRegistry.addModuleToItem;
import static de.nedelosk.forestmods.api.utils.ModuleRegistry.registerCategory;
import static de.nedelosk.forestmods.api.utils.ModuleRegistry.registerModular;
import static de.nedelosk.forestmods.common.core.modules.ModuleCore.ItemManager.Alloy_Ingots;
import static de.nedelosk.forestmods.common.core.modules.ModuleCore.ItemManager.Alloy_Nuggets;
import static de.nedelosk.forestmods.common.core.modules.ModuleCore.ItemManager.Component_Gears;
import static de.nedelosk.forestmods.common.core.modules.ModuleCore.ItemManager.Component_Rods;
import static de.nedelosk.forestmods.common.core.modules.ModuleCore.ItemManager.Component_Saw_Blades;
import static de.nedelosk.forestmods.common.core.modules.ModuleCore.ItemManager.Dusts;
import static de.nedelosk.forestmods.common.items.ItemModule.addModule;

import cpw.mods.fml.common.registry.GameRegistry;
import de.nedelosk.forestcore.core.Registry;
import de.nedelosk.forestcore.items.ItemBlockForest;
import de.nedelosk.forestcore.modules.AModule;
import de.nedelosk.forestcore.modules.IModuleManager;
import de.nedelosk.forestcore.modules.manager.IBlockManager;
import de.nedelosk.forestcore.modules.manager.IItemManager;
import de.nedelosk.forestcore.utils.OreStack;
import de.nedelosk.forestmods.api.modular.material.Materials;
import de.nedelosk.forestmods.api.modules.basic.ModuleCategory;
import de.nedelosk.forestmods.api.modules.machines.recipes.RecipeAlloySmelter;
import de.nedelosk.forestmods.api.modules.machines.recipes.RecipeAssembler;
import de.nedelosk.forestmods.api.modules.machines.recipes.RecipeCentrifuge;
import de.nedelosk.forestmods.api.modules.machines.recipes.RecipeLathe;
import de.nedelosk.forestmods.api.modules.machines.recipes.RecipeLathe.LatheModes;
import de.nedelosk.forestmods.api.modules.machines.recipes.RecipePulverizer;
import de.nedelosk.forestmods.api.modules.machines.recipes.RecipeSawMill;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.utils.ModuleCategoryUIDs;
import de.nedelosk.forestmods.common.blocks.BlockCasing;
import de.nedelosk.forestmods.common.blocks.BlockModularMachine;
import de.nedelosk.forestmods.common.blocks.tile.TileBlastFurnaceAccessPort;
import de.nedelosk.forestmods.common.blocks.tile.TileBlastFurnaceBase;
import de.nedelosk.forestmods.common.blocks.tile.TileBlastFurnaceFluidPort;
import de.nedelosk.forestmods.common.blocks.tile.TileModularMachine;
import de.nedelosk.forestmods.common.config.Config;
import de.nedelosk.forestmods.common.crafting.ShapedModuleRecipe;
import de.nedelosk.forestmods.common.items.ItemModule;
import de.nedelosk.forestmods.common.items.ItemModuleItem;
import de.nedelosk.forestmods.common.items.block.ItemBlockModularMachine;
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
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ModuleModularMachine extends AModule {

	public ModuleModularMachine() {
		super("ModularMachines");
	}

	@Override
	public void preInit(IModuleManager manager) {
		registerCategorys();
		manager.register(BlockManager.Casings, new BlockCasing(new String[] { "stone", "stone_brick", "iron", "bronze" }), ItemBlockForest.class);
		manager.register(BlockManager.Modular_Machine, new BlockModularMachine(), ItemBlockModularMachine.class);
		GameRegistry.registerTileEntity(TileModularMachine.class, "tile.modular");
		GameRegistry.registerTileEntity(TileBlastFurnaceAccessPort.class, "tile.blastfurnace.accessport");
		GameRegistry.registerTileEntity(TileBlastFurnaceFluidPort.class, "tile.blastfurnace.fluidport");
		GameRegistry.registerTileEntity(TileBlastFurnaceBase.class, "tile.blastfurnace.base");
		manager.register(ItemManager.Module_Item_Capacitor, new ItemModuleItem("capacitor", "capacitor",
				new String[] { "metal_paper_capacitor", "electrolyte_niobium_capacitor", "electrolyte_tantalum_capacitor", "double_layer_capacitor" }));
		manager.register(ItemManager.Module_Item_Engine,
				new ItemModuleItem("engine", "engine", new String[] { "iron_engine", "bronze_engine", "steel_engine", "magmarium_engine" }));
		manager.register(ItemManager.Producers, new ItemModule());
	}

	@Override
	public void init(IModuleManager manager) {
		registerCasings();
		registerMachines();
		registerManagers();
		registerEnergy();
		registerStorage();
		registerMachine();
		registerSawMillRecipes();
		registerPulverizerRecipes();
		registerAlloySmelterRecipes();
		registerAssemblerRecipes();
		registerLatheRecipes();
		registerCentrifugeRecipes();
		registerRecipes();
	}

	@Override
	public void postInit(IModuleManager manager) {
	}

	@Override
	public boolean isActive() {
		return Config.isModularMachinesActive;
	}

	public static enum BlockManager implements IBlockManager {
		Casings, Modular_Machine;

		private Block block;

		@Override
		public void register(Block block, Class<? extends ItemBlock> item, Object... objects) {
			this.block = block;
			Registry.registerBlock(block, item, block.getUnlocalizedName().replace("tile.", "").replace("forest.mm.", ""), objects);
		}

		@Override
		public boolean isItemEqual(ItemStack stack) {
			return stack != null && isBlockEqual(Block.getBlockFromItem(stack.getItem()));
		}

		@Override
		public boolean isBlockEqual(Block i) {
			return i != null && Block.isEqualTo(block, i);
		}

		@Override
		public boolean isBlockEqual(World world, int x, int y, int z) {
			return isBlockEqual(world.getBlock(x, y, z));
		}

		@Override
		public Item item() {
			return Item.getItemFromBlock(block);
		}

		@Override
		public Block block() {
			return block;
		}

		@Override
		public Block getObject() {
			return block;
		}
	}

	public static enum ItemManager implements IItemManager {
		// Module
		Module_Item_Capacitor, Module_Item_Engine, Producers;

		private Item item;

		@Override
		public void register(Item item, Object... objects) {
			this.item = item;
			Registry.registerItem(item, item.getUnlocalizedName().replace("forest.mm.item.", "").replace("item.", ""));
		}

		@Override
		public boolean isItemEqual(ItemStack stack) {
			return stack != null && this.item == stack.getItem();
		}

		@Override
		public boolean isItemEqual(Item i) {
			return i != null && this.item == i;
		}

		@Override
		public Item getObject() {
			return item;
		}

		@Override
		public Item item() {
			return item;
		}
	}

	public static void registerMachine() {
		registerModular(ModularMachine.class, "modular.machine");
	}

	public static void registerStorage() {
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

	public static void registerEnergy() {
		/*
		 * registerProducer(addModule(new ModuleStack(Modules.GENERATOR, new
		 * ProducerBurningGenerator(15, 5), Materials.STONE, true)));
		 * registerProducer(addModule(new ModuleStack(Modules.GENERATOR, new
		 * ProducerBurningGenerator(13, 15), Materials.IRON, true)));
		 * registerProducer(addModule(new ModuleStack(Modules.GENERATOR, new
		 * ProducerBurningGenerator(10, 45), Materials.BRONZE, true)));
		 */
		addModuleToItem(new ItemStack(ItemManager.Module_Item_Engine.item(), 1, 0), new ModuleEngineEnergy("EngineIron", 60), Materials.IRON);
		addModuleToItem(new ItemStack(ItemManager.Module_Item_Engine.item(), 1, 1), new ModuleEngineEnergy("EngineBronze", 50), Materials.BRONZE);
		addModuleToItem(new ItemStack(ItemManager.Module_Item_Engine.item(), 1, 2), new ModuleEngineEnergy("EngineSteel", 45), Materials.STEEL);
		addModuleToItem(new ItemStack(ItemManager.Module_Item_Engine.item(), 1, 3), new ModuleEngineEnergy("EngineMagmarium", 40), Materials.MAGMARIUM);
		addModuleToItem(new ItemStack(ItemManager.Module_Item_Capacitor.item(), 1, 0), new ModuleCapacitor("metal_paper_capacitor", 10, 20), Materials.IRON);
		addModuleToItem(new ItemStack(ItemManager.Module_Item_Capacitor.item(), 1, 1), new ModuleCapacitor("electrolyte_niobium_capacitor", 20, 30),
				Materials.IRON);
		addModuleToItem(new ItemStack(ItemManager.Module_Item_Capacitor.item(), 1, 2), new ModuleCapacitor("electrolyte_tantalum_capacitor", 25, 40),
				Materials.IRON);
		addModuleToItem(new ItemStack(ItemManager.Module_Item_Capacitor.item(), 1, 3), new ModuleCapacitor("double_layer_capacitor", 40, 60), Materials.BRONZE);
	}

	public static void registerManagers() {
		addModule(new ModuleTankManager(2), Materials.STONE);
		addModule(new ModuleTankManager(4), Materials.BRONZE);
		addModule(new ModuleTankManager(6), Materials.IRON);
		/*
		 * addModule(new ModuleStorageManager(1), Materials.STONE);
		 * addModule(new ModuleStorageManager(1), Materials.BRONZE);
		 * addModule(new ModuleStorageManager(2), Materials.IRON);
		 */
	}

	public static void registerCasings() {
		addModuleToItem(new ItemStack(Blocks.log, 1, 0), new ModuleCasing("wood_oak"), Materials.WOOD);
		addModuleToItem(new ItemStack(Blocks.log, 1, 1), new ModuleCasing("wood_bspruce"), Materials.WOOD);
		addModuleToItem(new ItemStack(Blocks.log, 1, 2), new ModuleCasing("wood_birch"), Materials.WOOD);
		addModuleToItem(new ItemStack(Blocks.log, 1, 3), new ModuleCasing("wood_jungle"), Materials.WOOD);
		addModuleToItem(new ItemStack(Blocks.log2, 1, 0), new ModuleCasing("wood_acacia"), Materials.WOOD);
		addModuleToItem(new ItemStack(Blocks.log2, 1, 1), new ModuleCasing("wood_dark_oak"), Materials.WOOD);
		addModuleToItem(new ItemStack(Blocks.stone), new ModuleCasing("stone"), Materials.STONE);
		addModuleToItem(new ItemStack(BlockManager.Casings.block(), 1, 0), new ModuleCasing("casing_stone"), Materials.STONE);
		addModuleToItem(new ItemStack(BlockManager.Casings.block(), 1, 1), new ModuleCasing("casing_stone_brick"), Materials.STONE);
		addModuleToItem(new ItemStack(BlockManager.Casings.block(), 1, 2), new ModuleCasing("casing_iron"), Materials.IRON);
		addModuleToItem(new ItemStack(BlockManager.Casings.block(), 1, 3), new ModuleCasing("casing_bronze"), Materials.BRONZE);
	}

	public static void registerRecipes() {
		/*
		 * addShapedRecipe(new ItemStack(BlockManager.Modular_Assembler.item()),
		 * "+++", "+W+", "+++", '+', "plateStone", 'W', Blocks.crafting_table);
		 * addShapedRecipe(new ItemStack(BlockManager.Modular_Assembler.item(),
		 * 1, 1), "+++", "+W+", "+++", '+', "plateIron", 'W', new
		 * ItemStack(BlockManager.Modular_Assembler.item()));
		 * addShapedRecipe(new ItemStack(BlockManager.Modular_Assembler.item(),
		 * 1, 2), "+++", "+W+", "+++", '+', "plateBronze", 'W', new
		 * ItemStack(BlockManager.Modular_Assembler.item(), 1, 1)); // Saw Mill
		 * addShapedModuleRecipe(ItemModule.getItem(Materials.STONE,
		 * Modules.SAWMILL), "-s-", "+-+", "-s-", '+', new
		 * ItemStack(Component_Saw_Blades.item()), '-', new
		 * ItemStack(Component_Plates.item(), 1, 0), 's', Items.string); //
		 * Alloy Smelter
		 * addShapedModuleRecipe(ItemModule.getItem(Materials.STONE,
		 * Modules.ALLOYSMELTER), "-s-", "+-+", "-s-", '+', Blocks.furnace, '-',
		 * new ItemStack(Component_Plates.item(), 1, 0), 's', Items.string); //
		 * Assembler addShapedModuleRecipe(ItemModule.getItem(Materials.STONE,
		 * Modules.ALLOYSMELTER), "-s-", "+-*", "-s-", '+', new
		 * ItemStack(Component_Saw_Blades.item()), '*', new
		 * ItemStack(Component_Gears.item()), '-', new
		 * ItemStack(Component_Plates.item(), 1, 0), 's', Items.string); //
		 * Lathe addShapedModuleRecipe(ItemModule.getItem(Materials.IRON,
		 * Modules.LATHE), "psp", "+-+", "psp", '+', "blockIron", '-',
		 * "blockRedstone", 's', "wireIron", 'p', "plateIron"); // Pulverizer
		 * registerRecipe(new RecipeModuleAssembler(new
		 * RecipeItem(ItemModule.getItem(Materials.STONE, Modules.PULVERIZER)),
		 * 1, 150, "-s-", "+-+", "-s-", '+', Items.flint, '-', new
		 * ItemStack(Component_Plates.item(), 1, 0), 's', Items.string)); //
		 * Generator addShapedModuleRecipe(ItemModule.getItem(Materials.STONE,
		 * Modules.GENERATOR), "-s-", "-+-", "-s-", '+', Blocks.furnace, '-',
		 * new ItemStack(Component_Plates.item(), 1, 0), 's', Items.string); //
		 * Tank Manager
		 * addShapedModuleRecipe(ItemModule.getItem(Materials.STONE,
		 * Modules.MANAGERTANK), "-s-", "+++", "-s-", '+', "glass", '-', new
		 * ItemStack(Component_Plates.item(), 1, 0), 's', Items.string);
		 * addShapedRecipe(new ItemStack(BlockManager.Casings.item()), "+++",
		 * "+ +", "---", '+', new ItemStack(Component_Plates.item()), '-',
		 * Blocks.brick_block); addShapedRecipe(new
		 * ItemStack(BlockManager.Casings.item(), 1, 1), "+++", "+ +", "---",
		 * '+', new ItemStack(Component_Plates.item()), '-', Blocks.stonebrick);
		 * addShapedRecipe(new ItemStack(BlockManager.Casings.item(), 1, 2),
		 * "+++", "+ +", "---", '+', new ItemStack(Component_Plates.item(), 1,
		 * 1), '-', Blocks.brick_block); addShapedRecipe(new
		 * ItemStack(BlockManager.Casings.item(), 1, 3), "+++", "+ +", "---",
		 * '+', new ItemStack(Component_Plates.item(), 1, 4), '-',
		 * Blocks.brick_block);
		 */
	}

	public static void registerMachines() {
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

	public static void registerLatheRecipes() {
		registerRecipe(
				new RecipeLathe(new RecipeItem(new OreStack("ingotIron")), new RecipeItem(new ItemStack(Component_Rods.item(), 2, 1)), 1, 375, LatheModes.ROD));
		registerRecipe(
				new RecipeLathe(new RecipeItem(new OreStack("ingotTin")), new RecipeItem(new ItemStack(Component_Rods.item(), 2, 2)), 2, 350, LatheModes.ROD));
		registerRecipe(new RecipeLathe(new RecipeItem(new OreStack("ingotCopper")), new RecipeItem(new ItemStack(Component_Rods.item(), 2, 3)), 1, 325,
				LatheModes.ROD));
		registerRecipe(new RecipeLathe(new RecipeItem(new OreStack("ingotBronze")), new RecipeItem(new ItemStack(Component_Rods.item(), 2, 4)), 2, 450,
				LatheModes.ROD));
		registerRecipe(new RecipeLathe(new RecipeItem(new OreStack("ingotSteel")), new RecipeItem(new ItemStack(Component_Rods.item(), 2, 5)), 3, 475,
				LatheModes.ROD));
		registerRecipe(new RecipeLathe(new RecipeItem(new OreStack("ingotPlastic")), new RecipeItem(new ItemStack(Component_Rods.item(), 2, 6)), 1, 250,
				LatheModes.ROD));
	}

	public static void registerAssemblerRecipes() {
		registerRecipe(new RecipeAssembler(new RecipeItem(new ItemStack(Component_Rods.item(), 4, 1)), new RecipeItem(new OreStack("blockIron", 1)),
				new RecipeItem(new ItemStack(Component_Saw_Blades.item(), 1, 1)), 1, 250));
		registerRecipe(new RecipeAssembler(new RecipeItem(new ItemStack(Component_Rods.item(), 4, 4)), new RecipeItem(new OreStack("blockBronze", 1)),
				new RecipeItem(new ItemStack(Component_Saw_Blades.item(), 1, 2)), 1, 275));
		registerRecipe(new RecipeAssembler(new RecipeItem(new ItemStack(Component_Rods.item(), 4, 5)), new RecipeItem(new OreStack("blockSteel", 1)),
				new RecipeItem(new ItemStack(Component_Saw_Blades.item(), 1, 3)), 1, 300));
		registerRecipe(new RecipeAssembler(new RecipeItem(new OreStack("plateIron", 4)), new RecipeItem(new OreStack("screwIron", 1)),
				new RecipeItem(new ItemStack(Component_Gears.item(), 1, 1)), 1, 250));
		registerRecipe(new RecipeAssembler(new RecipeItem(new OreStack("plateTin", 4)), new RecipeItem(new OreStack("screwTin", 1)),
				new RecipeItem(new ItemStack(Component_Gears.item(), 1, 2)), 1, 250));
		registerRecipe(new RecipeAssembler(new RecipeItem(new OreStack("plateCopper", 4)), new RecipeItem(new OreStack("screwCopper", 1)),
				new RecipeItem(new ItemStack(Component_Gears.item(), 1, 3)), 1, 250));
		registerRecipe(new RecipeAssembler(new RecipeItem(new OreStack("plateBronze", 4)), new RecipeItem(new OreStack("screwBronze", 1)),
				new RecipeItem(new ItemStack(Component_Gears.item(), 1, 4)), 1, 250));
		registerRecipe(new RecipeAssembler(new RecipeItem(new OreStack("plateSteel", 4)), new RecipeItem(new OreStack("screwSteel", 1)),
				new RecipeItem(new ItemStack(Component_Gears.item(), 1, 5)), 1, 250));
	}

	public static void registerSawMillRecipes() {
		registerRecipe(new RecipeSawMill(new ItemStack(Blocks.log, 1, 0), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.planks, 6, 0)) }, 10, 300));
		registerRecipe(new RecipeSawMill(new ItemStack(Blocks.log, 1, 1), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.planks, 6, 1)) }, 10, 300));
		registerRecipe(new RecipeSawMill(new ItemStack(Blocks.log, 1, 2), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.planks, 6, 2)) }, 10, 300));
		registerRecipe(new RecipeSawMill(new ItemStack(Blocks.log, 1, 3), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.planks, 6, 3)) }, 10, 300));
		registerRecipe(new RecipeSawMill(new ItemStack(Blocks.log2, 1, 0), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.planks, 6, 4)) }, 10, 300));
		registerRecipe(new RecipeSawMill(new ItemStack(Blocks.log2, 1, 1), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.planks, 6, 5)) }, 10, 300));
	}

	public static void registerCentrifugeRecipes() {
		registerRecipe(new RecipeCentrifuge(new RecipeItem(new OreStack("dustColumbite", 6)),
				new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 2, 14)), new RecipeItem(new ItemStack(Dusts.item(), 1, 15)) }, 9,
				560));
	}

	public static void registerPulverizerRecipes() {
		registerRecipe(new RecipePulverizer(new OreStack("oreCoal"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 2, 0)) }, 15, 250));
		registerRecipe(new RecipePulverizer(new OreStack("blockObsidian"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 2, 1)) }, 15, 250));
		registerRecipe(new RecipePulverizer(new OreStack("oreIron"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 2, 2)) }, 15, 250));
		registerRecipe(new RecipePulverizer(new OreStack("oreGold"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 2, 3)) }, 15, 250));
		registerRecipe(new RecipePulverizer(new OreStack("oreDiamond"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 2, 4)) }, 15, 250));
		registerRecipe(new RecipePulverizer(new OreStack("oreCopper"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 2, 5)) }, 15, 250));
		registerRecipe(new RecipePulverizer(new OreStack("oreTin"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 2, 6)) }, 15, 250));
		registerRecipe(new RecipePulverizer(new OreStack("oreSilver"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 2, 7)) }, 15, 250));
		registerRecipe(new RecipePulverizer(new OreStack("oreLead"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 2, 8)) }, 15, 250));
		registerRecipe(new RecipePulverizer(new OreStack("oreNickel"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 2, 9)) }, 15, 250));
		registerRecipe(new RecipePulverizer(new OreStack("oreRuby"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 2, 12)) }, 15, 250));
		registerRecipe(
				new RecipePulverizer(new OreStack("oreColumbite"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 2, 13)) }, 15, 250));
		registerRecipe(
				new RecipePulverizer(new OreStack("oreAluminum"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 2, 16)) }, 15, 250));
		registerRecipe(new RecipePulverizer(new OreStack("oreRedstone"), new RecipeItem[] { new RecipeItem(new ItemStack(Items.redstone, 8)) }, 15, 250));
		registerRecipe(new RecipePulverizer(new ItemStack(Items.coal), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 1, 0)) }, 7, 125));
		registerRecipe(new RecipePulverizer(new OreStack("ingotIron"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 1, 2)) }, 7, 125));
		registerRecipe(new RecipePulverizer(new OreStack("ingotGold"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 1, 3)) }, 7, 125));
		registerRecipe(new RecipePulverizer(new OreStack("gemDiamond"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 1, 4)) }, 7, 125));
		registerRecipe(new RecipePulverizer(new OreStack("ingotCopper"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 1, 5)) }, 7, 125));
		registerRecipe(new RecipePulverizer(new OreStack("ingotTin"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 1, 6)) }, 7, 125));
		registerRecipe(new RecipePulverizer(new OreStack("ingotSilver"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 1, 7)) }, 7, 125));
		registerRecipe(new RecipePulverizer(new OreStack("ingotLead"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 1, 8)) }, 7, 125));
		registerRecipe(new RecipePulverizer(new OreStack("ingotNickel"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 1, 9)) }, 7, 125));
		registerRecipe(new RecipePulverizer(new OreStack("ingotBronze"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 1, 10)) }, 7, 125));
		registerRecipe(new RecipePulverizer(new OreStack("ingotInvar"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 1, 11)) }, 7, 125));
		registerRecipe(
				new RecipePulverizer(new OreStack("ingotNiobium"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 1, 14)) }, 7, 125));
		registerRecipe(
				new RecipePulverizer(new OreStack("ingotTantalum"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 1, 15)) }, 7, 125));
		registerRecipe(new RecipePulverizer(new OreStack("gemRuby"), new RecipeItem[] { new RecipeItem(new ItemStack(Dusts.item(), 1, 12)) }, 7, 125));
	}

	public static void registerAlloySmelterRecipes() {
		registerRecipe(new RecipeAlloySmelter(new RecipeItem(new OreStack("dustTin", 1)), new RecipeItem(new OreStack("dustCopper", 3)),
				new RecipeItem[] { new RecipeItem(new ItemStack(Alloy_Ingots.item(), 4, 0)) }, 9, 250));
		registerRecipe(new RecipeAlloySmelter(new RecipeItem(new OreStack("ingotTin", 1)), new RecipeItem(new OreStack("ingotCopper", 3)),
				new RecipeItem[] { new RecipeItem(new ItemStack(Alloy_Ingots.item(), 4, 0)) }, 9, 275));
		registerRecipe(new RecipeAlloySmelter(new RecipeItem(new OreStack("dustIron", 2)), new RecipeItem(new OreStack("dustNickel", 1)),
				new RecipeItem[] { new RecipeItem(new ItemStack(Alloy_Ingots.item(), 3, 1)) }, 9, 375));
		registerRecipe(new RecipeAlloySmelter(new RecipeItem(new OreStack("ingotIron", 2)), new RecipeItem(new OreStack("ingotNickel", 1)),
				new RecipeItem[] { new RecipeItem(new ItemStack(Alloy_Ingots.item(), 3, 1)) }, 9, 400));
	}

	public static void addShapedModuleRecipe(ItemStack stack, Object... obj) {
		GameRegistry.addRecipe(new ShapedModuleRecipe(stack, obj));
	}
}
