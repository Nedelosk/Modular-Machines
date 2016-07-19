package de.nedelosk.modularmachines.common.core;

import de.nedelosk.modularmachines.api.energy.EnergyRegistry;
import de.nedelosk.modularmachines.api.material.EnumMetalMaterials;
import de.nedelosk.modularmachines.api.recipes.OreStack;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.api.recipes.RecipeRegistry;
import de.nedelosk.modularmachines.api.recipes.RecipeUtil;
import de.nedelosk.modularmachines.common.blocks.BlockMetalBlock.ComponentTypes;
import de.nedelosk.modularmachines.common.items.ItemComponent;
import de.nedelosk.modularmachines.common.items.ItemModule;
import de.nedelosk.modularmachines.common.modules.storaged.tools.ModuleLathe.LatheModes;
import de.nedelosk.modularmachines.common.modules.storaged.tools.recipe.RecipeHandlerDefault;
import de.nedelosk.modularmachines.common.modules.storaged.tools.recipe.RecipeHandlerHeat;
import de.nedelosk.modularmachines.common.modules.storaged.tools.recipe.RecipeHandlerToolMode;
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
		RecipeRegistry.registerRecipeHandler(new RecipeHandlerToolMode("Lathe", RecipeUtil.LATHEMODE));

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
		addShapedModuleRecipe(new ItemStack(ItemManager.itemEngineSteam), 
				"GHP",
				"BII",
				"GHP", 'I', "ingotIron", 'H', "blockIron", 'G', "gearIron", 'P', "plateIron", 'B', Blocks.PISTON);

		//Turbines
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
		addShapedRecipe(new ItemStack(ItemManager.itemHammer), "+S+", "+S+", " - ", '+', "ingotIron", 'S', "stone", '-', "stickWood");
		addShapedRecipe(new ItemStack(ItemManager.itemFileIron), true, "  I", "FIF", "S  ", 'I', "ingotIron", 'F', Items.FLINT, 'S', "stickWood");
		addShapedRecipe(new ItemStack(ItemManager.itemFileDiamond), true, "  D", "FDF", "S  ", 'D', "gemDiamond", 'F', Items.FLINT, 'S', "stickWood");
		addShapedRecipe(new ItemStack(ItemManager.itemCutter), "  S", " S ", "FIF", 'I', "ingotIron", 'F', Items.FLINT, 'S', "stickWood");
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
		for(int i = 0; i < ItemManager.itemCompPlates.materials.size(); i++) {
			ItemStack stack = new ItemStack(ItemManager.itemCompPlates, 1, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			if (component.materials.get(i).getOreDicts() != null) {
				for(String oreDict : component.materials.get(i).getOreDicts()) {
					addShapelessRecipe(stack, "ingot" + oreDict, "ingot" + oreDict, "toolHammer");
				}
			}
		}
		for(int i = 0; i < ItemManager.itemCompWires.materials.size(); i++) {
			ItemStack stack = new ItemStack(ItemManager.itemCompWires, 4, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			if (component.materials.get(i).getOreDicts() != null) {
				for(String oreDict : component.materials.get(i).getOreDicts()) {
					if (!oreDict.equals("Bronze") && !oreDict.equals("Steel")) {
						addShapelessRecipe(stack, "plate" + oreDict, "toolCutter");
					} else {
						RecipeUtil.addLathe("plate" + oreDict + "ToWire", new RecipeItem(new OreStack("plate" + oreDict)),
								new RecipeItem(new ItemStack(ItemManager.itemCompWires, 8, i)), 3, LatheModes.WIRE);
					}
				}
			}
		}
		for(int i = 0; i < ItemManager.itemCompScrews.materials.size(); i++) {
			ItemStack stack = new ItemStack(ItemManager.itemCompScrews, 1, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			if (component.materials.get(i).getOreDicts() != null) {
				for(String oreDict : component.materials.get(i).getOreDicts()) {
					RecipeUtil.addLathe("wire" + oreDict + "ToScrew", new RecipeItem(new OreStack("wire" + oreDict, 2)), new RecipeItem(stack), 3,
							LatheModes.SCREW);
				}
			}
		}
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
		addShapelessRecipe(new ItemStack(ItemManager.itemCompRods, 1, 0), "ingotIron", "ingotIron", "toolFile");
		addShapelessRecipe(new ItemStack(ItemManager.itemCompRods, 1, 1), "ingotTin", "ingotTin", "toolFile");
		addShapelessRecipe(new ItemStack(ItemManager.itemCompRods, 1, 2), "ingotCopper", "ingotCopper", "toolFile");
		addShapelessRecipe(new ItemStack(ItemManager.itemCompRods, 1, 3), "ingotBronze", "ingotBronze", "toolFile");
		addShapelessRecipe(new ItemStack(ItemManager.itemCompRods, 1, 4), "ingotSteel", "ingotSteel", "toolFile");
		addShapedRecipe(new ItemStack(ItemManager.itemCompSawBlades), " + ", "+-+", " + ", '+', new ItemStack(ItemManager.itemCompRods), '-', "cobblestone");
		addShapedRecipe(new ItemStack(ItemManager.itemCompGears), " + ", "+-+", " + ", '+', "plateStone", '-', "cobblestone");

		addShapedRecipe(new ItemStack(BlockManager.blockAssembler), "IPI", "PRP", "III", 'I', "ingotIron", 'P', "plateIron", 'R', "blockRedstone");
	}

	private static void registerLatheRecipes() {
		RecipeUtil.addLathe("IronRod", new RecipeItem(new OreStack("ingotIron")), new RecipeItem(new ItemStack(ItemManager.itemCompRods, 2, 0)), 1, LatheModes.ROD);
		RecipeUtil.addLathe("TinRod", new RecipeItem(new OreStack("ingotTin")), new RecipeItem(new ItemStack(ItemManager.itemCompRods, 2, 1)), 2, LatheModes.ROD);
		RecipeUtil.addLathe("CopperRod", new RecipeItem(new OreStack("ingotCopper")), new RecipeItem(new ItemStack(ItemManager.itemCompRods, 2, 2)), 1, LatheModes.ROD);
		RecipeUtil.addLathe("BronzeRod", new RecipeItem(new OreStack("ingotBronze")), new RecipeItem(new ItemStack(ItemManager.itemCompRods, 2, 3)), 2, LatheModes.ROD);
		RecipeUtil.addLathe("SteelRod", new RecipeItem(new OreStack("ingotSteel")), new RecipeItem(new ItemStack(ItemManager.itemCompRods, 2, 4)), 3, LatheModes.ROD);
	}

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
		RecipeUtil.addAlloySmelter("DustDustToBronze", new RecipeItem(new OreStack("dustTin", 1)), new RecipeItem(new OreStack("dustCopper", 3)), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemIngots, 4, 10)) }, 11, 125, 0.25);
		RecipeUtil.addAlloySmelter("DustIngotToBronze", new RecipeItem(new OreStack("ingotTin", 1)), new RecipeItem(new OreStack("ingotCopper", 3)), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemIngots, 4, 10)) }, 11, 125, 0.25);
		/* INVAR */
		RecipeUtil.addAlloySmelter("DustDustToInvar", new RecipeItem(new OreStack("dustIron", 2)), new RecipeItem(new OreStack("dustNickel", 1)), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemIngots, 3, 11)) }, 9, 175, 0.5);
		RecipeUtil.addAlloySmelter("DustIngotToInvar", new RecipeItem(new OreStack("ingotIron", 2)), new RecipeItem(new OreStack("ingotNickel", 1)), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemIngots, 3, 11)) }, 9, 175, 0.5);
	}

	private static void registerBoilerRecipes(){
		//ONLY FOR JEI
		RecipeUtil.addBoilerRecipe("WaterToSteam", new RecipeItem(new FluidStack(FluidRegistry.WATER, 1)), new RecipeItem(new FluidStack(FluidRegistry.getFluid("steam"), EnergyRegistry.STEAM_PER_UNIT_WATER)), 1, 100, 0D);
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
