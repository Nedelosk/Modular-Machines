package de.nedelosk.forestmods.common.core;

import java.util.Map.Entry;
import java.util.Set;

import cpw.mods.fml.common.registry.GameRegistry;
import de.nedelosk.forestcore.utils.CraftingUtil;
import de.nedelosk.forestcore.utils.OreStack;
import de.nedelosk.forestmods.api.crafting.ForestDayCrafting;
import de.nedelosk.forestmods.api.crafting.IWorkbenchRecipe;
import de.nedelosk.forestmods.api.crafting.WoodType;
import de.nedelosk.forestmods.api.modules.machines.recipes.RecipeLathe.LatheModes;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.common.blocks.BlockCharcoalKiln;
import de.nedelosk.forestmods.common.config.Config;
import de.nedelosk.forestmods.common.crafting.ShapedModuleRecipe;
import de.nedelosk.forestmods.common.items.ItemComponent;
import de.nedelosk.forestmods.common.utils.RecipeUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeManager {

	public static void registerRecipes() {
		registerSawMillRecipes();
		registerPulverizerRecipes();
		registerAlloySmelterRecipes();
		registerAssemblerRecipes();
		registerLatheRecipes();
		registerCentrifugeRecipes();
		registerRecipe();
		registerComponentRecipes();
		registerMetalRecipes();
		addMachineRecipes();
		addCampfireRecipes();
		addWorkbenchRecipes();
		addNormalRecipes();
	}

	public static void registerPostRecipes() {
		for ( Entry<ItemStack, ItemStack> recipes : (Set<Entry<ItemStack, ItemStack>>) FurnaceRecipes.smelting().getSmeltingList().entrySet() ) {
			if (recipes.getValue().getItem() == Items.coal && recipes.getValue().getItemDamage() == 1
					&& Block.getBlockFromItem(recipes.getKey().getItem()) != null && Block.getBlockFromItem(recipes.getKey().getItem()) != Blocks.air) {
				ForestDayCrafting.woodManager.add(recipes.getKey().getUnlocalizedName(), recipes.getKey(), recipes.getValue());
			}
		}
		registerCharcoalKilnRecipes();
		CraftingUtil.removeFurnaceRecipe(Items.coal, 1);
	}

	private static void addNormalRecipes() {
		addShapelessRecipe(new ItemStack(BlockManager.blockGravel, 4), Blocks.dirt, Blocks.dirt, Blocks.dirt, Blocks.dirt, Blocks.gravel, Blocks.gravel,
				Blocks.gravel, Blocks.gravel, Blocks.sand);
		addShapelessRecipe(new ItemStack(ItemManager.itemAxeFlint), Items.stick, Items.stick, Items.flint, Items.flint);
		addShapelessRecipe(new ItemStack(ItemManager.itemToolParts), Items.stick, Items.leather);
		addShapelessRecipe(new ItemStack(ItemManager.itemToolParts, 1, 1), new ItemStack(Blocks.stone_slab, 1, 3), new ItemStack(Blocks.stone_slab, 1, 3),
				Items.flint, Items.flint);
		addShapelessRecipe(new ItemStack(ItemManager.itemToolParts, 1, 2), Items.iron_ingot, Items.iron_ingot, Items.flint, Items.flint);
		addShapelessRecipe(new ItemStack(ItemManager.itemToolParts, 1, 3), Items.diamond, Items.diamond);
		addShapelessRecipe(new ItemStack(ItemManager.itemToolParts, 1, 4), Items.stick, Items.stick, Items.stick, Items.leather);
		addShapelessRecipe(new ItemStack(ItemManager.itemToolParts, 1, 5), new ItemStack(Blocks.stone_slab, 1, 3), new ItemStack(Blocks.stone_slab, 1, 3),
				new ItemStack(Blocks.stone_slab, 1, 3), Items.flint);
		addShapelessRecipe(new ItemStack(ItemManager.itemToolParts, 1, 6), Items.iron_ingot, Items.iron_ingot, Items.flint);
		addShapelessRecipe(new ItemStack(ItemManager.itemToolParts, 1, 7), Items.stick, Items.stick);
		addShapedRecipe(new ItemStack(ItemManager.itemToolParts, 1, 8), "   ", "+++", "  +", '+', Blocks.cobblestone);
		addShapedRecipe(new ItemStack(ItemManager.itemToolParts, 1, 9), "+++", "  +", "  +", '+', Blocks.cobblestone);
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
		addShapelessRecipe(new ItemStack(ItemManager.itemNature, 1, 3), Blocks.sand, Blocks.sand, Blocks.gravel, Blocks.gravel, Items.water_bucket);
		addShapelessRecipe(new ItemStack(ItemManager.itemNature, 1, 3), Blocks.sand, Blocks.sand, Blocks.gravel, Blocks.gravel,
				ItemManager.itemWoodBucketWater);
	}

	private static void addWorkbenchRecipes() {
		ForestDayCrafting.workbenchRecipe.addRecipe(new OreStack("plankWood"), new OreStack("toolFile"), new ItemStack(ItemManager.itemWoodGears, 1, 5),
				Config.worktableBurnTime);
		ForestDayCrafting.workbenchRecipe.addRecipe(new ItemStack(ItemManager.itemWoodGears, 1, 5), new OreStack("toolFile"),
				new ItemStack(ItemManager.itemWoodGears, 1, 4), Config.worktableBurnTime);
		ForestDayCrafting.workbenchRecipe.addRecipe(new ItemStack(ItemManager.itemWoodGears, 1, 4), new OreStack("toolFile"),
				new ItemStack(ItemManager.itemWoodGears, 1, 3), Config.worktableBurnTime);
		ForestDayCrafting.workbenchRecipe.addRecipe(new ItemStack(ItemManager.itemWoodGears, 1, 3), new OreStack("toolFile"),
				new ItemStack(ItemManager.itemWoodGears, 1, 2), Config.worktableBurnTime);
		ForestDayCrafting.workbenchRecipe.addRecipe(new ItemStack(ItemManager.itemWoodGears, 1, 2), new OreStack("toolFile"),
				new ItemStack(ItemManager.itemWoodGears, 1, 1), Config.worktableBurnTime);
		ForestDayCrafting.workbenchRecipe.addRecipe(new OreStack("plankWood"), new OreStack("toolKnife"), new ItemStack(ItemManager.itemWoodBucket),
				Config.worktableBurnTime);
		ForestDayCrafting.workbenchRecipe.addOutput(new ItemStack(ItemManager.itemWoodBucket));
		ForestDayCrafting.workbenchRecipe.addOutput(new ItemStack(ItemManager.itemWoodGears, 1, 1));
	}

	private static void addMachineRecipes() {
		// Furenace
		CraftingUtil.removeAnyRecipe(new ItemStack(Blocks.furnace));
		addShapedRecipe(new ItemStack(Blocks.furnace), "SSS", "BHB", "BBB", 'S', "stone", 'B', Blocks.stonebrick);
		// Campfire
		addShapedRecipe(new ItemStack(ItemManager.itemCampfireCurb, 1, 0), "+++", "+ +", "+++", '+', "cobblestone");
		addShapedRecipe(new ItemStack(ItemManager.itemCampfireCurb, 1, 1), "+++", "+ +", "+++", '+', "blockObsidian");
		addShapedRecipe(new ItemStack(ItemManager.itemCampfirePot, 1, 0), "   ", "+ +", "+++", '+', "stone");
		addShapedRecipe(new ItemStack(ItemManager.itemCampfirePot, 1, 1), "   ", "+ +", "+++", '+', "ingotBronze");
		addShapedRecipe(new ItemStack(ItemManager.itemCampfirePot, 1, 2), "   ", "+ +", "+++", '+', "ingotIron");
		addShapedRecipe(new ItemStack(ItemManager.itemCampfirePot, 1, 3), "   ", "+ +", "+++", '+', "ingotSteel");
		addShapedRecipe(new ItemStack(ItemManager.itemCampfirePotHolder), "++ ", "+  ", "   ", '+', "logWood");
		addShapedRecipe(new ItemStack(ItemManager.itemCampfirePotHolder, 1, 1), "++ ", "+  ", "+  ", '+', "stone");
		addShapedRecipe(new ItemStack(ItemManager.itemCampfirePotHolder, 1, 2), "++ ", "+  ", "+  ", '+', "ingotBronze");
		addShapedRecipe(new ItemStack(ItemManager.itemCampfirePotHolder, 1, 3), "++ ", "+  ", "+  ", '+', "ingotIron");
		addShapedRecipe(new ItemStack(BlockManager.blockMachines, 1, 1), "---", "+++", "W W", '-', Blocks.crafting_table, '+', "slabWood", 'W', "logWood");
		addShapedRecipe(new ItemStack(BlockManager.blockMachines, 1, 2), "---", "+++", "WCW", '-', Blocks.crafting_table, '+', "slabWood", 'W', "logWood", 'C',
				Blocks.chest);
		addShapelessRecipe(new ItemStack(BlockManager.blockMachines, 1, 2), new ItemStack(BlockManager.blockMachines, 1, 1), Blocks.chest);
		addShapedRecipe(new ItemStack(BlockManager.blockMachines, 1, 3), "ILI", "ICI", "ILI", 'I', "ingotIron", 'C', Blocks.chest, 'L',
				BlockManager.blockGravel);
	}

	private static void addCampfireRecipes() {
		ForestDayCrafting.campfireRecipe.addRecipe(new ItemStack(Blocks.red_mushroom), new ItemStack(Items.bowl), new ItemStack(Items.mushroom_stew), 0, 25);
		ForestDayCrafting.campfireRecipe.addRecipe(new ItemStack(Blocks.brown_mushroom), new ItemStack(Items.bowl), new ItemStack(Items.mushroom_stew), 0, 25);
		ForestDayCrafting.campfireRecipe.addRecipe(new ItemStack(Items.beef), new ItemStack(Items.cooked_beef), 0, 25);
		ForestDayCrafting.campfireRecipe.addRecipe(new ItemStack(Items.chicken), new ItemStack(Items.cooked_chicken), 0, 25);
		ForestDayCrafting.campfireRecipe.addRecipe(new ItemStack(Items.fish), new ItemStack(Items.cooked_fished), 0, 25);
		ForestDayCrafting.campfireRecipe.addRecipe(new ItemStack(Items.porkchop), new ItemStack(Items.cooked_porkchop), 0, 25);
		ForestDayCrafting.campfireRecipe.addRecipe(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.stone), 0, 10);
	}

	private static void registerCharcoalKilnRecipes() {
		for ( WoodType type : ForestDayCrafting.woodManager.getWoodTypes().values() ) {
			if (type != null && type.getWood() != null) {
				addShapedRecipe(writeWoodType(type), "+++", "+-+", "+++", '+', type.getWood(), '-', BlockManager.blockGravel);
			}
		}
	}

	public static ItemStack writeWoodType(WoodType type) {
		if (type != null) {
			ItemStack stack = new ItemStack(BlockManager.blockCharcoalKiln);
			NBTTagCompound nbtTag = new NBTTagCompound();
			nbtTag.setString("WoodType", type.getName());
			stack.setTagCompound(nbtTag);
			return stack;
		}
		return null;
	}

	public static WoodType readFromStack(ItemStack stack) {
		if (stack != null && stack.getItem() != null && stack.hasTagCompound() && stack.getTagCompound().hasKey("WoodType")
				&& Block.getBlockFromItem(stack.getItem()) != null && Block.getBlockFromItem(stack.getItem()) instanceof BlockCharcoalKiln) {
			return ForestDayCrafting.woodManager.get(stack.getTagCompound().getString("WoodType"));
		}
		return null;
	}

	private static void registerMetalRecipes() {
		for ( int m = 0; m < ItemManager.metals.length; m++ ) {
			String[] metal = ItemManager.metals[m];
			for ( int i = 0; i < metal.length; ++i ) {
				addShapedRecipe(new ItemStack(ItemManager.itemIngots, 1, m * 10 + i), "+++", "+++", "+++", '+', "nugget" + metal[i]);
				addShapelessRecipe(new ItemStack(ItemManager.itemNuggets, 9, m * 10 + i), "ingot" + metal[i]);
			}
		}
		GameRegistry.addSmelting(new ItemStack(ItemManager.itemDusts, 1, 2), new ItemStack(Items.iron_ingot), 0.5F);
		GameRegistry.addSmelting(new ItemStack(ItemManager.itemDusts, 1, 3), new ItemStack(Items.gold_ingot), 0.5F);
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
		addShapedRecipe(new ItemStack(ItemManager.itemWoodBucket), "   ", "W W", " W ", 'W', "logWood");
		IWorkbenchRecipe manager = ForestDayCrafting.workbenchRecipe;
		manager.addRecipe(new OreStack("cobblestone", 2), new OreStack("toolHammer"), new ItemStack(ItemManager.itemCompPlates), 100);
		for ( int i = 0; i < ItemManager.itemCompPlates.metas.size(); i++ ) {
			ItemStack stack = new ItemStack(ItemManager.itemCompPlates, 1, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			if (component.metas.get(i).get(2) != null) {
				for ( String oreDict : (String[]) component.metas.get(i).get(2) ) {
					manager.addRecipe(new OreStack("ingot" + oreDict, 2), new OreStack("toolHammer"), stack, 100);
				}
			}
		}
		for ( int i = 0; i < ItemManager.itemCompWires.metas.size(); i++ ) {
			ItemStack stack = new ItemStack(ItemManager.itemCompWires, 4, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			if (component.metas.get(i).get(2) != null) {
				for ( String oreDict : (String[]) component.metas.get(i).get(2) ) {
					if (!oreDict.equals("Bronze") && !oreDict.equals("Steel")) {
						manager.addRecipe(new OreStack("plate" + oreDict, 1), new OreStack("toolCutter"), stack, 100);
					} else {
						RecipeUtils.addLathe("plate" + oreDict + "ToWire", new RecipeItem(new OreStack("plate" + oreDict)),
								new RecipeItem(new ItemStack(ItemManager.itemCompWires, 8, i)), 1, 250, LatheModes.WIRE);
					}
				}
			}
		}
		for ( int i = 0; i < ItemManager.itemCompScrews.metas.size(); i++ ) {
			ItemStack stack = new ItemStack(ItemManager.itemCompScrews, 1, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			if (component.metas.get(i).get(2) != null) {
				for ( String oreDict : (String[]) component.metas.get(i).get(2) ) {
					RecipeUtils.addLathe("wire" + oreDict + "ToScrew", new RecipeItem(new OreStack("wire" + oreDict, 2)), new RecipeItem(stack), 1, 250,
							LatheModes.SCREW);
				}
			}
		}
		for ( int i = 0; i < BlockManager.blockMetalBlocks.metas.size(); i++ ) {
			ItemStack stack = new ItemStack(BlockManager.blockMetalBlocks, 1, i);
			if (BlockManager.blockMetalBlocks.metas.get(i).get(2) != null) {
				for ( String oreDict : (String[]) BlockManager.blockMetalBlocks.metas.get(i).get(2) ) {
					addShapedRecipe(stack, "+++", "+++", "+++", '+', "ingot" + oreDict);
				}
			}
		}
		for ( int i = 0; i < BlockManager.blockMetalBlocks.metas.size(); i++ ) {
			ItemStack stack = new ItemStack(BlockManager.blockMetalBlocks, 1, i);
			if (BlockManager.blockMetalBlocks.metas.get(i).get(2) != null) {
				for ( String oreDict : (String[]) BlockManager.blockMetalBlocks.metas.get(i).get(2) ) {
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

	private static void registerLatheRecipes() {
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
	}

	private static void registerSawMillRecipes() {
		RecipeUtils.addSawMill("OakPlanks", new ItemStack(Blocks.log, 1, 0), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.planks, 6, 0)) }, 10, 300);
		RecipeUtils.addSawMill("SprucePlanks", new ItemStack(Blocks.log, 1, 1), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.planks, 6, 1)) }, 10,
				300);
		RecipeUtils.addSawMill("BirchPlanks", new ItemStack(Blocks.log, 1, 2), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.planks, 6, 2)) }, 10,
				300);
		RecipeUtils.addSawMill("JunglePlanks", new ItemStack(Blocks.log, 1, 3), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.planks, 6, 3)) }, 10,
				300);
		RecipeUtils.addSawMill("AcaciaPlanks", new ItemStack(Blocks.log2, 1, 0), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.planks, 6, 4)) }, 10,
				300);
		RecipeUtils.addSawMill("DarkOakPlanks", new ItemStack(Blocks.log2, 1, 1), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.planks, 6, 5)) }, 10,
				300);
	}

	private static void registerCentrifugeRecipes() {
		RecipeUtils.addCentrifuge("ColumbiteToDust", new RecipeItem(new OreStack("dustColumbite", 6)),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 3, 26)), new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 27)) },
				9, 560);
	}

	private static void registerPulverizerRecipes() {
		/* ORES */
		RecipeUtils.addPulverizer("CoalOreToDust", new OreStack("oreCoal"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 0)) }, 15,
				250);
		RecipeUtils.addPulverizer("ObsidianBlockToDust", new OreStack("blockObsidian"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 1)) }, 15, 250);
		RecipeUtils.addPulverizer("IronOreToDust", new OreStack("oreIron"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 2)) }, 15,
				250);
		RecipeUtils.addPulverizer("GoldOreToDust", new OreStack("oreGold"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 3)) }, 15,
				250);
		RecipeUtils.addPulverizer("DiamondOreToDust", new OreStack("oreDiamond"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 4)) }, 15, 250);
		RecipeUtils.addPulverizer("CopperOreToDust", new OreStack("oreCopper"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 20)) }, 15, 250);
		RecipeUtils.addPulverizer("TinOreToDust", new OreStack("oreTin"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 21)) }, 15,
				250);
		RecipeUtils.addPulverizer("SilverOreToDust", new OreStack("oreSilver"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 22)) }, 15, 250);
		RecipeUtils.addPulverizer("LeadOreToDust", new OreStack("oreLead"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 23)) },
				15, 250);
		RecipeUtils.addPulverizer("NickelOreToDust", new OreStack("oreNickel"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 24)) }, 15, 250);
		RecipeUtils.addPulverizer("ColumbiteOreToDust", new OreStack("oreColumbite"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 10)) }, 15, 250);
		RecipeUtils.addPulverizer("RubyOreToDust", new OreStack("oreRuby"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 11)) },
				15, 250);
		RecipeUtils.addPulverizer("AluminumOreToDust", new OreStack("oreAluminum"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 25)) }, 15, 25);
		RecipeUtils.addPulverizer("AluminiumOreToDust", new OreStack("oreAluminium"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 25)) }, 15, 250);
		RecipeUtils.addPulverizer("RedstoneOreToDust", new OreStack("oreRedstone"), new RecipeItem[] { new RecipeItem(new ItemStack(Items.redstone, 8)) }, 15,
				250);
		RecipeUtils.addPulverizer("CoalToDust", new OreStack("itemCoal"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 0)) }, 7,
				125);
		/* INGOTS */
		RecipeUtils.addPulverizer("IronIngotToDust", new OreStack("ingotIron"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 2)) },
				7, 125);
		RecipeUtils.addPulverizer("GoldIngotToDust", new OreStack("ingotGold"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 3)) },
				7, 125);
		RecipeUtils.addPulverizer("DiamondGemToDust", new OreStack("gemDiamond"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 4)) }, 7, 125);
		RecipeUtils.addPulverizer("CopperIngotToDust", new OreStack("ingotCopper"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 20)) }, 7, 125);
		RecipeUtils.addPulverizer("TinIngotToDust", new OreStack("ingotTin"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 21)) },
				7, 125);
		RecipeUtils.addPulverizer("SilverIngotToDust", new OreStack("ingotSilver"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 22)) }, 7, 125);
		RecipeUtils.addPulverizer("LeadIngotToDust", new OreStack("ingotLead"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 23)) }, 7, 125);
		RecipeUtils.addPulverizer("NickelIngotToDust", new OreStack("ingotNickel"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 24)) }, 7, 125);
		RecipeUtils.addPulverizer("AluminumIngotToDust", new OreStack("ingotAluminum"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 25)) }, 7, 125);
		RecipeUtils.addPulverizer("AluminiumIngotToDust", new OreStack("ingotAluminium"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 25)) }, 7, 125);
		RecipeUtils.addPulverizer("NiobiumIngotToDust", new OreStack("ingotNiobium"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 26)) }, 7, 125);
		RecipeUtils.addPulverizer("TantalumIngotToDust", new OreStack("ingotTantalum"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 27)) }, 7, 125);
		RecipeUtils.addPulverizer("BronzeIngotToDust", new OreStack("ingotBronze"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 30)) }, 7, 125);
		RecipeUtils.addPulverizer("InvarIngotToDust", new OreStack("ingotInvar"),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 31)) }, 7, 125);
		/* GEMS */
		RecipeUtils.addPulverizer("RubyGemToDust", new OreStack("gemRuby"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 11)) }, 7,
				125);
	}

	private static void registerAlloySmelterRecipes() {
		/* BRONZE */
		RecipeUtils.addAlloySmelter("DustDustToBronze", new RecipeItem(new OreStack("dustTin", 1)), new RecipeItem(new OreStack("dustCopper", 3)),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemIngots, 4, 10)) }, 9, 250);
		RecipeUtils.addAlloySmelter("DustIngotToBronze", new RecipeItem(new OreStack("ingotTin", 1)), new RecipeItem(new OreStack("ingotCopper", 3)),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemIngots, 4, 10)) }, 9, 275);
		/* INVAR */
		RecipeUtils.addAlloySmelter("DustDustToInvar", new RecipeItem(new OreStack("dustIron", 2)), new RecipeItem(new OreStack("dustNickel", 1)),
				new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemIngots, 3, 11)) }, 9, 375);
		RecipeUtils.addAlloySmelter("DustIngotToInvar", new RecipeItem(new OreStack("ingotIron", 2)), new RecipeItem(new OreStack("ingotNickel", 1)),
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
