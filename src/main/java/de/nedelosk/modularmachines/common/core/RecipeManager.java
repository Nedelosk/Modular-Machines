package de.nedelosk.modularmachines.common.core;

import de.nedelosk.modularmachines.api.material.EnumMetalMaterials;
import de.nedelosk.modularmachines.api.recipes.OreStack;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.api.recipes.RecipeRegistry;
import de.nedelosk.modularmachines.api.recipes.RecipeUtil;
import de.nedelosk.modularmachines.common.blocks.BlockMetalBlock.ComponentTypes;
import de.nedelosk.modularmachines.common.items.ItemComponent;
import de.nedelosk.modularmachines.common.items.ItemModule;
import de.nedelosk.modularmachines.common.modules.storaged.tools.recipe.RecipeHandlerDefault;
import de.nedelosk.modularmachines.common.modules.storaged.tools.recipe.RecipeHandlerHeat;
import de.nedelosk.modularmachines.common.recipse.ShapedModuleRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeManager {

	public static void registerRecipes() {
		RecipeRegistry.registerRecipeHandler(new RecipeHandlerHeat("Boiler"));
		RecipeRegistry.registerRecipeHandler(new RecipeHandlerHeat("AlloySmelter"));
		RecipeRegistry.registerRecipeHandler(new RecipeHandlerDefault("Pulverizer"));

		registerSawMillRecipes();
		registerPulverizerRecipes();
		registerAlloySmelterRecipes();
		registerBoilerRecipes();
		registerLatheRecipes();
		addComponentRecipes();
		addMetalRecipes();
		addMachineRecipes();
		addNormalRecipes();
		addModuleRecipes();
	}

	private static void addModuleRecipes(){
		addShapedRecipe(new ItemStack(ItemManager.itemModuleCore), 
				"PWP",
				"WRW",
				"PWP", 'P', "plateIron", 'W', "wireIron", 'R', "dustRedstone");
		addShapedRecipe(new ItemStack(ItemManager.itemModuleCore, 1, 1), 
				"BWB",
				"PRP",
				"BWB", 'P', "plateBronze", 'W', "wireBronze", 'R', "blockRedstone", 'B', new ItemStack(ItemManager.itemModuleCore));

		addShapedRecipe(new ItemStack(ItemManager.itemModuleCore, 1, 2), 
				"BWB",
				"PSP",
				"BWB", 'P', "plateSteel", 'W', "wireSteel", 'S', "blockSteel", 'B', new ItemStack(ItemManager.itemModuleCore, 1, 1));

		//Drawers
		addShapedRecipe(new ItemStack(ItemManager.itemDrawer, 1, 0), 
				"BIB",
				"BIB",
				"BIB", 'I', "ingotBrick", 'B', new ItemStack(Blocks.BRICK_BLOCK));

		//Engines
		/*
		addShapedModuleRecipe(new ItemStack(ItemManager.itemEngineRF), 
				"GHP",
				"BII",
				"GHP", 'I', "ingotIron", 'H', "blockIron", 'G', "gearIron", 'P', "plateIron", 'B', Blocks.PISTON);*/
		
		addShapedModuleRecipe(new ItemStack(ItemManager.itemTurbineSteam), 
				"SPI",
				"PBP",
				"IPS", 'I', "ingotIron", 'B', "blockIron", 'P', "plateIron", 'S', "screwIron");

		//Heaters
		addShapedModuleRecipe(ItemModule.getItem(ModuleManager.moduleHeaterBurningIron.getRegistryName(), EnumMetalMaterials.IRON), 
				"GPR",
				"FCF",
				"RPG", 'R', "rodIron", 'G', "gearIron", 'P', "plateIron", 'F', Blocks.FURNACE, 'C', new ItemStack(ItemManager.itemModuleCore));

		addShapedModuleRecipe(ItemModule.getItem(ModuleManager.moduleHeaterBronzeLarge.getRegistryName(), EnumMetalMaterials.BRONZE), 
				"GPR",
				"COC",
				"RPG", 'R', "rodBronze", 'G', "gearBronze", 'P', "plateBronze", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 1), 'O', ItemModule.getItem(ModuleManager.moduleHeaterBurningIron.getRegistryName(), EnumMetalMaterials.IRON));

		//Alloy Smleters
		addShapedModuleRecipe(ItemModule.getItem(ModuleManager.moduleAlloySmelterIron.getRegistryName(), EnumMetalMaterials.IRON), 
				"PWP",
				"FCF",
				"PWP", 'W', "wireIron", 'F', Blocks.FURNACE, 'P', "plateIron", 'C', new ItemStack(ItemManager.itemModuleCore));
		addShapedModuleRecipe(ItemModule.getItem(ModuleManager.moduleAlloySmelterBronze.getRegistryName(), EnumMetalMaterials.BRONZE), 
				"PPP",
				"COC",
				"PPP", 'O', ItemModule.getItem(ModuleManager.moduleAlloySmelterIron.getRegistryName(), EnumMetalMaterials.IRON), 'F', Blocks.FURNACE, 'P', "plateBronze", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 1));

		//Pulverizer
		addShapedModuleRecipe(ItemModule.getItem(ModuleManager.modulePulverizerIron.getRegistryName(), EnumMetalMaterials.IRON), 
				"RWF",
				"PCP",
				"FWR", 'W', "wireIron", 'R', "rodIron", 'F', Items.FLINT, 'P', "plateIron", 'C', new ItemStack(ItemManager.itemModuleCore));
		addShapedModuleRecipe(ItemModule.getItem(ModuleManager.modulePulverizerBronze.getRegistryName(), EnumMetalMaterials.BRONZE), 
				"PRP",
				"COC",
				"PRP", 'O', ItemModule.getItem(ModuleManager.modulePulverizerIron.getRegistryName(), EnumMetalMaterials.IRON), 'F', Blocks.FURNACE, 'P', "plateBronze", 'R', "rodBronze", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 1));
	}

	private static void addNormalRecipes() {
		addShapedRecipe(new ItemStack(ItemManager.itemHammer), "+++", "+++", " - ", '+', "ingotIron", '-', "stickWood");
	}

	private static void addMachineRecipes() {
		//Casings
		addShapedRecipe(new ItemStack(BlockManager.blockCasings), "+++", "+ +", "---", '+', "plateIron", '-', Blocks.BRICK_BLOCK);
		addShapedRecipe(new ItemStack(BlockManager.blockCasings, 1, 1), "+++", "+ +", "---", '+', "plateBronze", '-', Blocks.BRICK_BLOCK);
	}

	private static void addMetalRecipes() {
		for(int m = 0; m < ItemManager.metals.length; m++) {
			Object[][] metal = ItemManager.metals[m];
			for(int i = 0; i < metal.length; ++i) {
				addShapedRecipe(new ItemStack(ItemManager.itemIngots, 1, m * 10 + i), "+++", "+++", "+++", '+', "nugget" + metal[i][0]);
				addShapelessRecipe(new ItemStack(ItemManager.itemNuggets, 9, m * 10 + i), "ingot" + metal[i][0]);
			}
		}
		GameRegistry.addSmelting(new ItemStack(ItemManager.itemDusts, 1, 2), new ItemStack(Items.IRON_INGOT), 0.5F);
		GameRegistry.addSmelting(new ItemStack(ItemManager.itemDusts, 1, 3), new ItemStack(Items.GOLD_INGOT), 0.5F);
		GameRegistry.addSmelting(new ItemStack(ItemManager.itemDusts, 1, 10), new ItemStack(ItemManager.itemIngots, 1, 0), 0.5F);
		GameRegistry.addSmelting(new ItemStack(ItemManager.itemDusts, 1, 11), new ItemStack(ItemManager.itemIngots, 1, 1), 0.5F);
		GameRegistry.addSmelting(new ItemStack(ItemManager.itemDusts, 1, 12), new ItemStack(ItemManager.itemIngots, 1, 2), 0.5F);
		GameRegistry.addSmelting(new ItemStack(ItemManager.itemDusts, 1, 13), new ItemStack(ItemManager.itemIngots, 1, 3), 0.5F);
		GameRegistry.addSmelting(new ItemStack(ItemManager.itemDusts, 1, 14), new ItemStack(ItemManager.itemIngots, 1, 4), 0.5F);
		GameRegistry.addSmelting(new ItemStack(ItemManager.itemDusts, 1, 15), new ItemStack(ItemManager.itemIngots, 1, 5), 0.5F);
		GameRegistry.addSmelting(new ItemStack(ItemManager.itemDusts, 1, 16), new ItemStack(ItemManager.itemIngots, 1, 6), 0.5F);
	}

	private static void addComponentRecipes() {
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
		for(ComponentTypes type : ComponentTypes.values()) {
			ItemStack stack = new ItemStack(BlockManager.blockMetalBlocks, 1, type.ordinal());
			for(String oreDict : type.oreDict) {
				addShapedRecipe(stack, "+++", "+++", "+++", '+', "ingot" + oreDict);
			}
		}
		for(ComponentTypes type : ComponentTypes.values()) {
			ItemStack stack = new ItemStack(BlockManager.blockMetalBlocks, 1, type.ordinal());
			for(String oreDict : type.oreDict) {
				if (OreDictionary.getOres("ingot" + oreDict) != null && !OreDictionary.getOres("ingot" + oreDict).isEmpty()) {
					ItemStack ore = OreDictionary.getOres("ingot" + oreDict).get(0).copy();
					ore.stackSize = 9;
					addShapelessRecipe(ore, stack);
				}
			}
		}
		addShapelessRecipe(new ItemStack(ItemManager.itemCompRods), "cobblestone", "cobblestone", "cobblestone");
		addShapedRecipe(new ItemStack(ItemManager.itemCompSawBlades), " + ", "+-+", " + ", '+', new ItemStack(ItemManager.itemCompRods), '-', "cobblestone");
		addShapedRecipe(new ItemStack(ItemManager.itemCompGears), " + ", "+-+", " + ", '+', "plateStone", '-', "cobblestone");
	}

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

	private static void registerLatheRecipes() {
		/*RecipeUtil.addLathe("IronRod", new RecipeItem(new OreStack("ingotIron")), new RecipeItem(new ItemStack(ItemManager.itemCompRods, 2, 1)), 1, 375,
				LatheModes.ROD);
		RecipeUtil.addLathe("TinRod", new RecipeItem(new OreStack("ingotTin")), new RecipeItem(new ItemStack(ItemManager.itemCompRods, 2, 2)), 2, 350,
				LatheModes.ROD);
		RecipeUtil.addLathe("CopperRod", new RecipeItem(new OreStack("ingotCopper")), new RecipeItem(new ItemStack(ItemManager.itemCompRods, 2, 3)), 1, 325,
				LatheModes.ROD);
		RecipeUtil.addLathe("BronzeRod", new RecipeItem(new OreStack("ingotBronze")), new RecipeItem(new ItemStack(ItemManager.itemCompRods, 2, 4)), 2, 450,
				LatheModes.ROD);
		RecipeUtil.addLathe("SteelRod", new RecipeItem(new OreStack("ingotSteel")), new RecipeItem(new ItemStack(ItemManager.itemCompRods, 2, 5)), 3, 475,
				LatheModes.ROD);*/
	}

	/*private static void registerAssemblerRecipes() {
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
		//RecipeUtil.addSawMill("OakPlanks", new ItemStack(Blocks.LOG, 1, 0), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.PLANKS, 6, 0))/*, new RecipeItem(new ItemStack(ItemManager.itemNature), 50)*/ }, 10, 300);
		//RecipeUtil.addSawMill("SprucePlanks", new ItemStack(Blocks.LOG, 1, 1), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.PLANKS, 6, 1))/*, new RecipeItem(new ItemStack(ItemManager.itemNature), 50)*/ }, 10,
		//		300);
		//RecipeUtil.addSawMill("BirchPlanks", new ItemStack(Blocks.LOG, 1, 2), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.PLANKS, 6, 2))/*, new RecipeItem(new ItemStack(ItemManager.itemNature), 50)*/ }, 10,
		//		300);
		//RecipeUtil.addSawMill("JunglePlanks", new ItemStack(Blocks.LOG, 1, 3), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.PLANKS, 6, 3))/*, new RecipeItem(new ItemStack(ItemManager.itemNature), 50)*/ }, 10,
		//		300);
		//RecipeUtil.addSawMill("AcaciaPlanks", new ItemStack(Blocks.LOG2, 1, 0), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.PLANKS, 6, 4))/*, new RecipeItem(new ItemStack(ItemManager.itemNature), 50)*/ }, 10,
		//		300);
		//RecipeUtil.addSawMill("DarkOakPlanks", new ItemStack(Blocks.LOG2, 1, 1), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.PLANKS, 6, 5))/*, new RecipeItem(new ItemStack(ItemManager.itemNature), 50)*/ }, 10,
		//		300);
	}

	private static void registerPulverizerRecipes() {
		/* ORES */
		RecipeUtil.addPulverizer("CoalOreToDust", new OreStack("oreCoal"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 0)), new RecipeItem(new ItemStack(Items.COAL), 15) }, 15);
		RecipeUtil.addPulverizer("ObsidianBlockToDust", new OreStack("blockObsidian"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 1)) }, 15);
		RecipeUtil.addPulverizer("IronOreToDust", new OreStack("oreIron"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 2)) }, 15);
		RecipeUtil.addPulverizer("GoldOreToDust", new OreStack("oreGold"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 3)), new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 10), 20) }, 15);
		RecipeUtil.addPulverizer("DiamondOreToDust", new OreStack("oreDiamond"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 4)) }, 15);
		RecipeUtil.addPulverizer("CopperOreToDust", new OreStack("oreCopper"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 10)), new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 3), 15) }, 12);
		RecipeUtil.addPulverizer("TinOreToDust", new OreStack("oreTin"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 11)) }, 15);
		RecipeUtil.addPulverizer("SilverOreToDust", new OreStack("oreSilver"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 12)) }, 15);
		RecipeUtil.addPulverizer("LeadOreToDust", new OreStack("oreLead"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 13)) }, 15);
		RecipeUtil.addPulverizer("NickelOreToDust", new OreStack("oreNickel"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 14)) }, 15);
		RecipeUtil.addPulverizer("AluminumOreToDust", new OreStack("oreAluminum"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 15)) }, 15);
		RecipeUtil.addPulverizer("AluminiumOreToDust", new OreStack("oreAluminium"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 2, 15)) }, 15);
		RecipeUtil.addPulverizer("RedstoneOreToDust", new OreStack("oreRedstone"), new RecipeItem[] { new RecipeItem(new ItemStack(Items.REDSTONE, 8)) }, 15);
		RecipeUtil.addPulverizer("CoalToDust", new OreStack("itemCoal"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 0)) }, 7);
		/* INGOTS */
		RecipeUtil.addPulverizer("IronIngotToDust", new OreStack("ingotIron"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 2)) }, 7);
		RecipeUtil.addPulverizer("GoldIngotToDust", new OreStack("ingotGold"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 3)) }, 7);
		RecipeUtil.addPulverizer("DiamondGemToDust", new OreStack("gemDiamond"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 4)) }, 7);
		RecipeUtil.addPulverizer("CopperIngotToDust", new OreStack("ingotCopper"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 20)) }, 7);
		RecipeUtil.addPulverizer("TinIngotToDust", new OreStack("ingotTin"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 11)) }, 7);
		RecipeUtil.addPulverizer("SilverIngotToDust", new OreStack("ingotSilver"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 12)) }, 7);
		RecipeUtil.addPulverizer("LeadIngotToDust", new OreStack("ingotLead"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 13)) }, 7);
		RecipeUtil.addPulverizer("NickelIngotToDust", new OreStack("ingotNickel"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 14)) }, 7);
		RecipeUtil.addPulverizer("AluminumIngotToDust", new OreStack("ingotAluminum"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 15)) }, 7);
		RecipeUtil.addPulverizer("AluminiumIngotToDust", new OreStack("ingotAluminium"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 15)) }, 7);
		RecipeUtil.addPulverizer("BronzeIngotToDust", new OreStack("ingotBronze"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 20)) }, 7);
		RecipeUtil.addPulverizer("InvarIngotToDust", new OreStack("ingotInvar"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 21)) }, 7);
	}

	private static void registerAlloySmelterRecipes() {
		/* BRONZE */
		RecipeUtil.addAlloySmelter("DustDustToBronze", new RecipeItem(new OreStack("dustTin", 1)), new RecipeItem(new OreStack("dustCopper", 3)), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemIngots, 4, 10)) }, 9, 125, 35);
		RecipeUtil.addAlloySmelter("DustIngotToBronze", new RecipeItem(new OreStack("ingotTin", 1)), new RecipeItem(new OreStack("ingotCopper", 3)), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemIngots, 4, 10)) }, 9, 125, 35);
		/* INVAR */
		RecipeUtil.addAlloySmelter("DustDustToInvar", new RecipeItem(new OreStack("dustIron", 2)), new RecipeItem(new OreStack("dustNickel", 1)), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemIngots, 3, 11)) }, 9, 175, 35);
		RecipeUtil.addAlloySmelter("DustIngotToInvar", new RecipeItem(new OreStack("ingotIron", 2)), new RecipeItem(new OreStack("ingotNickel", 1)), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemIngots, 3, 11)) }, 9, 175, 35);
	}

	private static void registerBoilerRecipes(){
		RecipeUtil.addBoilerRecipe("WaterToSteam", new RecipeItem(new FluidStack(FluidRegistry.WATER, 50)), new RecipeItem(new FluidStack(FluidRegistry.getFluid("steam"), 80)), 1, 150, 25);
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
