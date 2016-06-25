package de.nedelosk.modularmachines.common.core;

import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.common.items.ItemComponent;
import de.nedelosk.modularmachines.common.recipse.ShapedModuleRecipe;
import de.nedelosk.modularmachines.common.utils.OreStack;
import de.nedelosk.modularmachines.common.utils.RecipeUtil;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeManager {

	public static void registerRecipes() {
		registerSawMillRecipes();
		registerPulverizerRecipes();
		registerAlloySmelterRecipes();
		//registerAssemblerRecipes();
		//registerLatheRecipes();
		//registerCentrifugeRecipes();
		registerRecipe();
		registerComponentRecipes();
		registerMetalRecipes();
		addMachineRecipes();
		addNormalRecipes();
	}

	public static void registerPostRecipes() {
		/*
		 * for(ItemStack oreStack : OreDictionary.getOres("logWood")){ if
		 * (Block.getBlockFromItem(oreStack.getItem()) != null &&
		 * Block.getBlockFromItem(oreStack.getItem()) != Blocks.air) { if
		 * (oreStack.getItemDamage() == OreDictionary.WILDCARD_VALUE) { int
		 * metas = 4; if (Block.getBlockFromItem(oreStack.getItem()) instanceof
		 * BlockNewLog) { metas = 2; } for(int meta = 0; meta < metas; meta++) {
		 * ItemStack logStack = new ItemStack(oreStack.getItem(), 1, meta);
		 * ForestDayCrafting.woodManager.add(logStack.getUnlocalizedName(),
		 * logStack, new ItemStack(Items.coal, 1, 1)); } } else {
		 * ForestDayCrafting.woodManager.add(oreStack.getUnlocalizedName(),
		 * oreStack, new ItemStack(Items.coal, 1, 1)); } } }
		 */
		/*
		 * for(Entry<ItemStack, ItemStack> recipe : (Set<Entry<ItemStack,
		 * ItemStack>>) FurnaceRecipes.smelting().getSmeltingList().entrySet())
		 * { if (recipe.getValue().getItem() == Items.coal &&
		 * recipe.getValue().getItemDamage() == 1 &&
		 * Block.getBlockFromItem(recipe.getKey().getItem()) != null &&
		 * Block.getBlockFromItem(recipe.getKey().getItem()) != Blocks.air) {
		 * ItemStack oreStack = recipe.getKey(); for(int oreID :
		 * OreDictionary.getOreIDs(recipe.getKey())) { String oreDict =
		 * OreDictionary.getOreName(oreID); if (oreDict.equals("logWood")) { if
		 * (oreStack.getItemDamage() == OreDictionary.WILDCARD_VALUE) { int
		 * metas = 4; if (Block.getBlockFromItem(oreStack.getItem()) instanceof
		 * BlockNewLog) { metas = 2; } for(int meta = 0; meta < metas; meta++) {
		 * ItemStack logStack = new ItemStack(oreStack.getItem(), 1, meta);
		 * ForestDayCrafting.woodManager.add(logStack.getUnlocalizedName(),
		 * logStack, recipe.getValue()); } } else {
		 * ForestDayCrafting.woodManager.add(oreStack.getUnlocalizedName(),
		 * recipe.getKey(), oreStack); } } } } }
		 */
		// registerCharcoalKilnRecipes();
		RecipeUtil.removeFurnaceRecipe(Items.COAL, 1);
	}

	private static void addNormalRecipes() {
		addShapelessRecipe(new ItemStack(ItemManager.itemToolParts), Items.STICK, Items.LEATHER);
		addShapelessRecipe(new ItemStack(ItemManager.itemToolParts, 1, 1), new ItemStack(Blocks.STONE_SLAB, 1, 3), new ItemStack(Blocks.STONE_SLAB, 1, 3),
				Items.FLINT, Items.FLINT);
		addShapelessRecipe(new ItemStack(ItemManager.itemToolParts, 1, 2), Items.IRON_INGOT, Items.IRON_INGOT, Items.FLINT, Items.FLINT);
		addShapelessRecipe(new ItemStack(ItemManager.itemToolParts, 1, 3), Items.DIAMOND, Items.DIAMOND);
		addShapelessRecipe(new ItemStack(ItemManager.itemToolParts, 1, 4), Items.STICK, Items.STICK, Items.STICK, Items.LEATHER);
		addShapelessRecipe(new ItemStack(ItemManager.itemToolParts, 1, 5), new ItemStack(Blocks.STONE_SLAB, 1, 3), new ItemStack(Blocks.STONE_SLAB, 1, 3),
				new ItemStack(Blocks.STONE_SLAB, 1, 3), Items.FLINT);
		addShapelessRecipe(new ItemStack(ItemManager.itemToolParts, 1, 6), Items.IRON_INGOT, Items.IRON_INGOT, Items.FLINT);
		addShapelessRecipe(new ItemStack(ItemManager.itemToolParts, 1, 7), Items.STICK, Items.STICK);
		addShapedRecipe(new ItemStack(ItemManager.itemToolParts, 1, 8), "   ", "+++", "  +", '+', Blocks.COBBLESTONE);
		addShapedRecipe(new ItemStack(ItemManager.itemToolParts, 1, 9), "+++", "  +", "  +", '+', Blocks.COBBLESTONE);
		addShapedRecipe(new ItemStack(ItemManager.itemToolParts, 1, 11), "  +", " + ", "+  ", '+', "plankWood");
		addShapedRecipe(new ItemStack(ItemManager.itemToolParts, 1, 10), "  +", " + ", "   ", '+', "plankWood");
		addShapedRecipe(new ItemStack(ItemManager.itemToolParts, 1, 10), "   ", " + ", "+  ", '+', "plankWood");
		addShapelessRecipe(new ItemStack(ItemManager.itemFileStone), new ItemStack(ItemManager.itemToolParts, 1, 0),
				new ItemStack(ItemManager.itemToolParts, 1, 1));
		addShapelessRecipe(new ItemStack(ItemManager.itemFileIron), new ItemStack(ItemManager.itemToolParts, 1, 0),
				new ItemStack(ItemManager.itemToolParts, 1, 2));
		addShapelessRecipe(new ItemStack(ItemManager.itemFileDiamond), new ItemStack(ItemManager.itemToolParts, 1, 0),
				new ItemStack(ItemManager.itemToolParts, 1, 3));
		addShapelessRecipe(new ItemStack(ItemManager.itemKnifeStone), new ItemStack(ItemManager.itemToolParts, 1, 4),
				new ItemStack(ItemManager.itemToolParts, 1, 5));
		addShapelessRecipe(new ItemStack(ItemManager.itemCutter), new ItemStack(ItemManager.itemToolParts, 1, 6),
				new ItemStack(ItemManager.itemToolParts, 1, 7));
		addShapelessRecipe(new ItemStack(ItemManager.itemAdze), new ItemStack(ItemManager.itemToolParts, 1, 8),
				new ItemStack(ItemManager.itemToolParts, 1, 10));
		addShapelessRecipe(new ItemStack(ItemManager.itemAdzeLong), new ItemStack(ItemManager.itemToolParts, 1, 9),
				new ItemStack(ItemManager.itemToolParts, 1, 11));
		addShapedRecipe(new ItemStack(ItemManager.itemHammer), "+++", "+++", " - ", '+', "ingotIron", '-', "stickWood");
		addShapelessRecipe(new ItemStack(ItemManager.itemNature, 1, 3), Blocks.SAND, Blocks.SAND, Blocks.GRAVEL, Blocks.GRAVEL, Items.WATER_BUCKET);
	}

	private static void addMachineRecipes() {
		//Casings
		addShapedRecipe(new ItemStack(BlockManager.blockCasings), "+++", "+ +", "---", '+', Blocks.STONE, '-', Blocks.BRICK_BLOCK);
		addShapedRecipe(new ItemStack(BlockManager.blockCasings, 1, 1), "+++", "+ +", "---", '+', "plateIron", '-', Blocks.BRICK_BLOCK);
		addShapedRecipe(new ItemStack(BlockManager.blockCasings, 1, 2), "+++", "+ +", "---", '+', "plateBronze", '-', Blocks.BRICK_BLOCK);
	}

	private static void registerMetalRecipes() {
		for(int m = 0; m < ItemManager.metals.length; m++) {
			String[] metal = ItemManager.metals[m];
			for(int i = 0; i < metal.length; ++i) {
				addShapedRecipe(new ItemStack(ItemManager.itemIngots, 1, m * 10 + i), "+++", "+++", "+++", '+', "nugget" + metal[i]);
				addShapelessRecipe(new ItemStack(ItemManager.itemNuggets, 9, m * 10 + i), "ingot" + metal[i]);
			}
		}
		GameRegistry.addSmelting(new ItemStack(ItemManager.itemDusts, 1, 2), new ItemStack(Items.IRON_INGOT), 0.5F);
		GameRegistry.addSmelting(new ItemStack(ItemManager.itemDusts, 1, 3), new ItemStack(Items.GOLD_INGOT), 0.5F);
		GameRegistry.addSmelting(new ItemStack(ItemManager.itemDusts, 1, 20), new ItemStack(ItemManager.itemIngots, 1, 0), 0.5F);
		GameRegistry.addSmelting(new ItemStack(ItemManager.itemDusts, 1, 21), new ItemStack(ItemManager.itemIngots, 1, 1), 0.5F);
		GameRegistry.addSmelting(new ItemStack(ItemManager.itemDusts, 1, 22), new ItemStack(ItemManager.itemIngots, 1, 2), 0.5F);
		GameRegistry.addSmelting(new ItemStack(ItemManager.itemDusts, 1, 23), new ItemStack(ItemManager.itemIngots, 1, 3), 0.5F);
		GameRegistry.addSmelting(new ItemStack(ItemManager.itemDusts, 1, 24), new ItemStack(ItemManager.itemIngots, 1, 4), 0.5F);
		GameRegistry.addSmelting(new ItemStack(ItemManager.itemDusts, 1, 25), new ItemStack(ItemManager.itemIngots, 1, 5), 0.5F);
		GameRegistry.addSmelting(new ItemStack(ItemManager.itemDusts, 1, 26), new ItemStack(ItemManager.itemIngots, 1, 6), 0.5F);
		GameRegistry.addSmelting(new ItemStack(ItemManager.itemDusts, 1, 27), new ItemStack(ItemManager.itemIngots, 1, 7), 0.5F);
	}

	private static void registerComponentRecipes() {
		addShapelessRecipe(new ItemStack(ItemManager.itemCompPlates), "cobblestone", "cobblestone", "toolHammer");
		for(int i = 0; i < ItemManager.itemCompPlates.metas.size(); i++) {
			ItemStack stack = new ItemStack(ItemManager.itemCompPlates, 1, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			if (component.metas.get(i).get(2) != null) {
				for(String oreDict : (String[]) component.metas.get(i).get(2)) {
					addShapelessRecipe(stack, "ingot" + oreDict, "ingot" + oreDict, "toolHammer");
				}
			}
		}
		/*for(int i = 0; i < ItemManager.itemCompWires.metas.size(); i++) {
			ItemStack stack = new ItemStack(ItemManager.itemCompWires, 4, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			if (component.metas.get(i).get(2) != null) {
				for(String oreDict : (String[]) component.metas.get(i).get(2)) {
					if (!oreDict.equals("Bronze") && !oreDict.equals("Steel")) {
						addShapelessRecipe(stack, "plate" + oreDict, "toolCutter");
					} else {
						RecipeUtils.addLathe("plate" + oreDict + "ToWire", new RecipeItem(new OreStack("plate" + oreDict)),
								new RecipeItem(new ItemStack(ItemManager.itemCompWires, 8, i)), 1, 250, LatheModes.WIRE);
					}
				}
			}
		}
		for(int i = 0; i < ItemManager.itemCompScrews.metas.size(); i++) {
			ItemStack stack = new ItemStack(ItemManager.itemCompScrews, 1, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			if (component.metas.get(i).get(2) != null) {
				for(String oreDict : (String[]) component.metas.get(i).get(2)) {
					RecipeUtils.addLathe("wire" + oreDict + "ToScrew", new RecipeItem(new OreStack("wire" + oreDict, 2)), new RecipeItem(stack), 1, 250,
							LatheModes.SCREW);
				}
			}
		}*/
		for(int i = 0; i < BlockManager.blockMetalBlocks.metas.size(); i++) {
			ItemStack stack = new ItemStack(BlockManager.blockMetalBlocks, 1, i);
			if (BlockManager.blockMetalBlocks.metas.get(i).get(2) != null) {
				for(String oreDict : (String[]) BlockManager.blockMetalBlocks.metas.get(i).get(2)) {
					addShapedRecipe(stack, "+++", "+++", "+++", '+', "ingot" + oreDict);
				}
			}
		}
		for(int i = 0; i < BlockManager.blockMetalBlocks.metas.size(); i++) {
			ItemStack stack = new ItemStack(BlockManager.blockMetalBlocks, 1, i);
			if (BlockManager.blockMetalBlocks.metas.get(i).get(2) != null) {
				for(String oreDict : (String[]) BlockManager.blockMetalBlocks.metas.get(i).get(2)) {
					if (OreDictionary.getOres("ingot" + oreDict) != null && !OreDictionary.getOres("ingot" + oreDict).isEmpty()) {
						ItemStack ore = OreDictionary.getOres("ingot" + oreDict).get(0);
						ore.stackSize = 9;
						addShapelessRecipe(ore, stack);
					}
				}
			}
		}
		addShapelessRecipe(new ItemStack(ItemManager.itemCompRods), "cobblestone", "cobblestone", "cobblestone");
		addShapedRecipe(new ItemStack(ItemManager.itemCompSawBlades), " + ", "+-+", " + ", '+', new ItemStack(ItemManager.itemCompRods), '-', "cobblestone");
		addShapedRecipe(new ItemStack(ItemManager.itemCompGears), " + ", "+-+", " + ", '+', "plateStone", '-', "cobblestone");
	}

	private static void registerRecipe() {
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

	/*private static void registerLatheRecipes() {
		RecipeUtils.addLathe("IronRod", new RecipeItem(new OreStack("ingotIron")), new RecipeItem(new ItemStack(ItemManager.itemCompRods, 2, 1)), 1, 375,
				LatheModes.ROD);
		RecipeUtils.addLathe("TinRod", new RecipeItem(new OreStack("ingotTin")), new RecipeItem(new ItemStack(ItemManager.itemCompRods, 2, 2)), 2, 350,
				LatheModes.ROD);
		RecipeUtils.addLathe("CopperRod", new RecipeItem(new OreStack("ingotCopper")), new RecipeItem(new ItemStack(ItemManager.itemCompRods, 2, 3)), 1, 325,
				LatheModes.ROD);
		RecipeUtils.addLathe("BronzeRod", new RecipeItem(new OreStack("ingotBronze")), new RecipeItem(new ItemStack(ItemManager.itemCompRods, 2, 4)), 2, 450,
				LatheModes.ROD);
		RecipeUtils.addLathe("SteelRod", new RecipeItem(new OreStack("ingotSteel")), new RecipeItem(new ItemStack(ItemManager.itemCompRods, 2, 5)), 3, 475,
				LatheModes.ROD);
	}

	private static void registerAssemblerRecipes() {
		RecipeUtils.addAssembler("IronSaw", new RecipeItem(new ItemStack(ItemManager.itemCompRods, 4, 1)), new RecipeItem(new OreStack("blockIron", 1)),
				new RecipeItem(new ItemStack(ItemManager.itemCompSawBlades, 1, 1)), 1, 250);
		RecipeUtils.addAssembler("BronzeSaw", new RecipeItem(new ItemStack(ItemManager.itemCompRods, 4, 4)), new RecipeItem(new OreStack("blockBronze", 1)),
				new RecipeItem(new ItemStack(ItemManager.itemCompSawBlades, 1, 2)), 1, 275);
		RecipeUtils.addAssembler("SteelSaw", new RecipeItem(new ItemStack(ItemManager.itemCompRods, 4, 5)), new RecipeItem(new OreStack("blockSteel", 1)),
				new RecipeItem(new ItemStack(ItemManager.itemCompSawBlades, 1, 3)), 1, 300);
		RecipeUtils.addAssembler("IronGear", new RecipeItem(new OreStack("plateIron", 4)), new RecipeItem(new OreStack("screwIron", 1)),
				new RecipeItem(new ItemStack(ItemManager.itemCompGears, 1, 1)), 1, 250);
		RecipeUtils.addAssembler("TinGear", new RecipeItem(new OreStack("plateTin", 4)), new RecipeItem(new OreStack("screwTin", 1)),
				new RecipeItem(new ItemStack(ItemManager.itemCompGears, 1, 2)), 1, 250);
		RecipeUtils.addAssembler("CopperGear", new RecipeItem(new OreStack("plateCopper", 4)), new RecipeItem(new OreStack("screwCopper", 1)),
				new RecipeItem(new ItemStack(ItemManager.itemCompGears, 1, 3)), 1, 250);
		RecipeUtils.addAssembler("BronzeGear", new RecipeItem(new OreStack("plateBronze", 4)), new RecipeItem(new OreStack("screwBronze", 1)),
				new RecipeItem(new ItemStack(ItemManager.itemCompGears, 1, 4)), 1, 250);
		RecipeUtils.addAssembler("SteelGear", new RecipeItem(new OreStack("plateSteel", 4)), new RecipeItem(new OreStack("screwSteel", 1)),
				new RecipeItem(new ItemStack(ItemManager.itemCompGears, 1, 5)), 1, 250);
	}*/

	private static void registerSawMillRecipes() {
		RecipeUtil.addSawMill("OakPlanks", new ItemStack(Blocks.LOG, 1, 0), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.PLANKS, 6, 0)), new RecipeItem(new ItemStack(ItemManager.itemNature), 50) }, 10, 300);
		RecipeUtil.addSawMill("SprucePlanks", new ItemStack(Blocks.LOG, 1, 1), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.PLANKS, 6, 1)), new RecipeItem(new ItemStack(ItemManager.itemNature), 50) }, 10,
				300);
		RecipeUtil.addSawMill("BirchPlanks", new ItemStack(Blocks.LOG, 1, 2), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.PLANKS, 6, 2)), new RecipeItem(new ItemStack(ItemManager.itemNature), 50) }, 10,
				300);
		RecipeUtil.addSawMill("JunglePlanks", new ItemStack(Blocks.LOG, 1, 3), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.PLANKS, 6, 3)), new RecipeItem(new ItemStack(ItemManager.itemNature), 50) }, 10,
				300);
		RecipeUtil.addSawMill("AcaciaPlanks", new ItemStack(Blocks.LOG2, 1, 0), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.PLANKS, 6, 4)), new RecipeItem(new ItemStack(ItemManager.itemNature), 50) }, 10,
				300);
		RecipeUtil.addSawMill("DarkOakPlanks", new ItemStack(Blocks.LOG2, 1, 1), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.PLANKS, 6, 5)), new RecipeItem(new ItemStack(ItemManager.itemNature), 50) }, 10,
				300);
	}

	/*private static void registerCentrifugeRecipes() {
		RecipeUtils.addCentrifuge("ColumbiteToDust", new RecipeItem(new OreStack("dustColumbite", 6)),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 3, 26)), new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 27)) },
				9, 560);
	}*/

	private static void registerPulverizerRecipes() {
		/* ORES */
		RecipeUtil.addPulverizer("CoalOreToDust", new OreStack("oreCoal"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 0)), new RecipeItem(new ItemStack(Items.COAL), 15) }, 15,
				250);
		RecipeUtil.addPulverizer("ObsidianBlockToDust", new OreStack("blockObsidian"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 1)) }, 15, 250);
		RecipeUtil.addPulverizer("IronOreToDust", new OreStack("oreIron"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 2)) }, 15,
				250);
		RecipeUtil.addPulverizer("GoldOreToDust", new OreStack("oreGold"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 3)), new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 20), 20) }, 15,
				250);
		RecipeUtil.addPulverizer("DiamondOreToDust", new OreStack("oreDiamond"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 4)) }, 15, 250);
		RecipeUtil.addPulverizer("CopperOreToDust", new OreStack("oreCopper"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 20)), new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 3), 15) }, 12, 250);
		RecipeUtil.addPulverizer("TinOreToDust", new OreStack("oreTin"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 21)) }, 15,
				250);
		RecipeUtil.addPulverizer("SilverOreToDust", new OreStack("oreSilver"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 22)) }, 15, 250);
		RecipeUtil.addPulverizer("LeadOreToDust", new OreStack("oreLead"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 23)) },
				15, 250);
		RecipeUtil.addPulverizer("NickelOreToDust", new OreStack("oreNickel"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 24)) }, 15, 250);
		RecipeUtil.addPulverizer("ColumbiteOreToDust", new OreStack("oreColumbite"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 10)) }, 15, 250);
		RecipeUtil.addPulverizer("RubyOreToDust", new OreStack("oreRuby"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 11)) },
				15, 250);
		RecipeUtil.addPulverizer("AluminumOreToDust", new OreStack("oreAluminum"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 25)) }, 15, 25);
		RecipeUtil.addPulverizer("AluminiumOreToDust", new OreStack("oreAluminium"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 25)) }, 15, 250);
		RecipeUtil.addPulverizer("RedstoneOreToDust", new OreStack("oreRedstone"), new RecipeItem[] { new RecipeItem(new ItemStack(Items.REDSTONE, 8)) }, 15,
				250);
		RecipeUtil.addPulverizer("CoalToDust", new OreStack("itemCoal"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 0)) }, 7,
				125);
		/* INGOTS */
		RecipeUtil.addPulverizer("IronIngotToDust", new OreStack("ingotIron"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 2)) },
				7, 125);
		RecipeUtil.addPulverizer("GoldIngotToDust", new OreStack("ingotGold"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 3)) },
				7, 125);
		RecipeUtil.addPulverizer("DiamondGemToDust", new OreStack("gemDiamond"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 4)) }, 7, 125);
		RecipeUtil.addPulverizer("CopperIngotToDust", new OreStack("ingotCopper"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 20)) }, 7, 125);
		RecipeUtil.addPulverizer("TinIngotToDust", new OreStack("ingotTin"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 21)) },
				7, 125);
		RecipeUtil.addPulverizer("SilverIngotToDust", new OreStack("ingotSilver"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 22)) }, 7, 125);
		RecipeUtil.addPulverizer("LeadIngotToDust", new OreStack("ingotLead"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 23)) }, 7, 125);
		RecipeUtil.addPulverizer("NickelIngotToDust", new OreStack("ingotNickel"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 24)) }, 7, 125);
		RecipeUtil.addPulverizer("AluminumIngotToDust", new OreStack("ingotAluminum"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 25)) }, 7, 125);
		RecipeUtil.addPulverizer("AluminiumIngotToDust", new OreStack("ingotAluminium"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 25)) }, 7, 125);
		RecipeUtil.addPulverizer("NiobiumIngotToDust", new OreStack("ingotNiobium"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 26)) }, 7, 125);
		RecipeUtil.addPulverizer("TantalumIngotToDust", new OreStack("ingotTantalum"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 27)) }, 7, 125);
		RecipeUtil.addPulverizer("BronzeIngotToDust", new OreStack("ingotBronze"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 30)) }, 7, 125);
		RecipeUtil.addPulverizer("InvarIngotToDust", new OreStack("ingotInvar"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 31)) }, 7, 125);
		/* GEMS */
		RecipeUtil.addPulverizer("RubyGemToDust", new OreStack("gemRuby"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 11)) }, 7,
				125);
	}

	private static void registerAlloySmelterRecipes() {
		/* BRONZE */
		RecipeUtil.addAlloySmelter("DustDustToBronze", new RecipeItem(new OreStack("dustTin", 1)), new RecipeItem(new OreStack("dustCopper", 3)),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemIngots, 4, 10)) }, 9, 250);
		RecipeUtil.addAlloySmelter("DustIngotToBronze", new RecipeItem(new OreStack("ingotTin", 1)), new RecipeItem(new OreStack("ingotCopper", 3)),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemIngots, 4, 10)) }, 9, 275);
		/* INVAR */
		RecipeUtil.addAlloySmelter("DustDustToInvar", new RecipeItem(new OreStack("dustIron", 2)), new RecipeItem(new OreStack("dustNickel", 1)),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemIngots, 3, 11)) }, 9, 375);
		RecipeUtil.addAlloySmelter("DustIngotToInvar", new RecipeItem(new OreStack("ingotIron", 2)), new RecipeItem(new OreStack("ingotNickel", 1)),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemIngots, 3, 11)) }, 9, 400);
	}

	private static void addShapedModuleRecipe(ItemStack stack, Object... obj) {
		GameRegistry.addRecipe(new ShapedModuleRecipe(stack, obj));
	}

	private static void addShapedRecipe(ItemStack stack, Object... obj) {
		GameRegistry.addRecipe(new ShapedOreRecipe(stack, obj));
	}

	private static void addShapelessRecipe(ItemStack stack, Object... obj) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(stack, obj));
	}
}
