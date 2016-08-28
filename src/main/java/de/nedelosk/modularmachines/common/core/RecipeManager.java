package de.nedelosk.modularmachines.common.core;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.material.EnumMetalMaterials;
import de.nedelosk.modularmachines.api.material.EnumVanillaMaterials;
import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.material.IMetalMaterial;
import de.nedelosk.modularmachines.api.material.MaterialList;
import de.nedelosk.modularmachines.api.material.MaterialRegistry;
import de.nedelosk.modularmachines.api.recipes.OreStack;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.api.recipes.RecipeRegistry;
import de.nedelosk.modularmachines.api.recipes.RecipeUtil;
import de.nedelosk.modularmachines.api.recipes.RecipeUtil.LatheModes;
import de.nedelosk.modularmachines.common.blocks.BlockMetalBlock.ComponentTypes;
import de.nedelosk.modularmachines.common.modules.tools.recipe.RecipeHandlerDefault;
import de.nedelosk.modularmachines.common.modules.tools.recipe.RecipeHandlerHeat;
import de.nedelosk.modularmachines.common.modules.tools.recipe.RecipeHandlerToolMode;
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
		addComponentRecipes();
		addMetalRecipes();
		addMachineRecipes();
		addNormalRecipes();
		addModuleRecipes();
	}

	private static void addModuleRecipes(){
		addShapelessRecipe(ItemManager.itemDusts.getStack(EnumMetalMaterials.TIN), "oreTin", "toolHammer");
		addShapelessRecipe(ItemManager.itemDusts.getStack(EnumMetalMaterials.COPPER), "oreCopper", "toolHammer");
		addShapelessRecipe(ItemManager.itemDusts.getStack(EnumMetalMaterials.LEAD), "oreLead", "toolHammer");
		addShapelessRecipe(ItemManager.itemDusts.getStack(EnumMetalMaterials.NICKEL), "oreNickel", "toolHammer");
		addShapelessRecipe(ItemManager.itemDusts.getStack(EnumMetalMaterials.SILVER), "oreSilver", "toolHammer");
		addShapelessRecipe(ItemManager.itemDusts.getStack(EnumMetalMaterials.ALUMINIUM), "oreAluminum", "toolHammer");
		addShapelessRecipe(ItemManager.itemDusts.getStack(EnumMetalMaterials.ALUMINIUM), "oreAluminium", "toolHammer");

		ItemStack cupperDust = ItemManager.itemDusts.getStack(EnumMetalMaterials.COPPER);
		addShapelessRecipe(ItemManager.itemDusts.getStack(EnumMetalMaterials.BRONZE, 3), cupperDust, cupperDust, cupperDust, ItemManager.itemDusts.getStack(EnumMetalMaterials.TIN));

		//Casings
		addShapedRecipe(new ItemStack(ItemManager.itemCasings), 
				"+++", 
				"+ +",
				"---", '+', "plateBronze", '-', Blocks.BRICK_BLOCK);

		addShapedRecipe(new ItemStack(ItemManager.itemCasings, 1, 1), 
				"+++", 
				"+ +", 
				"---", '+', "plateIron", '-', Blocks.BRICK_BLOCK);

		//Drawers
		addShapedRecipe(new ItemStack(ItemManager.itemDrawer, 1, 0), 
				"BIB",
				"BIB",
				"BIB", 'I', "ingotBrick", 'B', new ItemStack(Blocks.BRICK_BLOCK));

		addShapedRecipe(new ItemStack(ItemManager.itemDrawer, 1, 1), 
				"III",
				"I I",
				"III", 'I', "ingotBrick");

		//Engines
		addShapedRecipe(new ItemStack(ItemManager.itemEngineSteam, 1, 0), 
				"GHP",
				"BII",
				"GHP", 'I', "rodBronze", 'H', "ingotBronze", 'G', "gearBronze", 'P', "plateBronze", 'B', Blocks.PISTON);

		addShapedRecipe(new ItemStack(ItemManager.itemEngineSteam, 1, 1), 
				"GHP",
				"BII",
				"GHP", 'I', "rodIron", 'H', "ingotIron", 'G', "gearIron", 'P', "plateIron", 'B', new ItemStack(ItemManager.itemEngineSteam, 1, 0));

		addShapedRecipe(new ItemStack(ItemManager.itemEngineSteam, 1, 2), 
				"GHP",
				"BII",
				"GHP", 'I', "rodSteel", 'H', "ingotSteel", 'G', "gearSteel", 'P', "plateSteel", 'B', new ItemStack(ItemManager.itemEngineSteam, 1, 1));

		addShapedRecipe(new ItemStack(ItemManager.itemEngineSteam, 1, 3), 
				"GHP",
				"BII",
				"GHP", 'I', "rodMagmarium", 'H', "ingotMagmarium", 'G', "gearMagmarium", 'P', "plateMagmarium", 'B', new ItemStack(ItemManager.itemEngineSteam, 1, 2));

		//Turbines
		addShapedRecipe(new ItemStack(ItemManager.itemTurbineSteam, 1, 0), 
				"SPI",
				"PBP",
				"IPS", 'I', "ingotBronze", 'B', "blockBronze", 'P', "plateBronze", 'S', "screwBronze");

		addShapedRecipe(new ItemStack(ItemManager.itemTurbineSteam, 1, 1), 
				"SPR",
				"PBP",
				"RPS", 'R', "rodIron", 'B', "blockIron", 'P', "plateIron", 'S', "screwIron");

		addShapedRecipe(new ItemStack(ItemManager.itemTurbineSteam, 1, 2), 
				"SPR",
				"PBP",
				"RPS", 'R', "rodSteel", 'B', "blockSteel", 'P', "plateSteel", 'S', "screwSteel");

		addShapedRecipe(new ItemStack(ItemManager.itemTurbineSteam, 1, 3), 
				"SPR",
				"PBP",
				"RPS", 'R', "rodMagmarium", 'B', "blockMagmarium", 'P', "plateMagmarium", 'S', "screwMagmarium");

		//Module Cores
		addShapedRecipe(new ItemStack(ItemManager.itemModuleCore), 
				"PWP",
				"WRW",
				"PWP", 'P', "plateBronze", 'W', "wireBronze", 'R', "dustRedstone");
		addShapedRecipe(new ItemStack(ItemManager.itemModuleCore, 1, 1), 
				"BWB",
				"PRP",
				"BWB", 'P', "plateIron", 'W', "wireIron", 'R', "blockRedstone", 'B', new ItemStack(ItemManager.itemModuleCore));

		addShapedRecipe(new ItemStack(ItemManager.itemModuleCore, 1, 2),
				"BWB",
				"PSP",
				"BWB", 'P', "plateSteel", 'W', "wireSteel", 'S', "blockSteel", 'B', new ItemStack(ItemManager.itemModuleCore, 1, 1));

		//Boilers
		addShapedModuleRecipe(ModularMachinesApi.createDefaultStack(ModuleManager.moduleBoilerContainers[0]), 
				"PPP",
				"GCG",
				"PPP", 'G', "blockGlass", 'P', "plateBronze", 'C', new ItemStack(ItemManager.itemModuleCore));

		addShapedModuleRecipe(ModularMachinesApi.createDefaultStack(ModuleManager.moduleBoilerContainers[1]), 
				"PGP",
				"COC",
				"PGP", 'G', "blockGlass", 'P', "plateIron", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 1), 'O', ModularMachinesApi.createDefaultStack(ModuleManager.moduleBoilerContainers[0]));

		addShapedModuleRecipe(ModularMachinesApi.createDefaultStack(ModuleManager.moduleBoilerContainers[2]), 
				"PGP",
				"COC",
				"PGP", 'G', "blockGlass", 'P', "plateSteel", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 2), 'O', ModularMachinesApi.createDefaultStack(ModuleManager.moduleBoilerContainers[1]));

		addShapedModuleRecipe(ModularMachinesApi.createDefaultStack(ModuleManager.moduleBoilerContainers[3]), 
				"PGP",
				"COC",
				"PGP", 'G', "blockGlass", 'P', "plateMagmarium", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 3), 'O', ModularMachinesApi.createDefaultStack(ModuleManager.moduleBoilerContainers[2]));

		//Heaters
		addShapedModuleRecipe(ModularMachinesApi.createDefaultStack(ModuleManager.moduleHeaterSteamContainer), 
				"GPR",
				"FCF",
				"RPG", 'R', "rodBronze", 'G', "gearBronze", 'P', "plateBronze", 'F', "blockGlass", 'C', new ItemStack(ItemManager.itemModuleCore));

		addShapedModuleRecipe(ModularMachinesApi.createDefaultStack(ModuleManager.moduleHeaterIronContainers[0]), 
				"GPR",
				"FCF",
				"RPG", 'R', "rodBronze", 'G', "gearBronze", 'P', "plateBronze", 'F', Blocks.FURNACE, 'C', new ItemStack(ItemManager.itemModuleCore));

		addShapedModuleRecipe(ModularMachinesApi.createDefaultStack(ModuleManager.moduleHeaterBronzeContainer), 
				"GPR",
				"COC",
				"RPG", 'R', "rodIron", 'G', "gearIron", 'P', "plateIron", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 1), 'O', ModularMachinesApi.createDefaultStack(ModuleManager.moduleHeaterIronContainers[0]));

		addShapedModuleRecipe(ModularMachinesApi.createDefaultStack(ModuleManager.moduleHeaterSteelContainers[0]), 
				"GPR",
				"COC",
				"RPG", 'R', "rodSteel", 'G', "gearSteel", 'P', "plateSteel", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 2), 'O', ModularMachinesApi.createDefaultStack(ModuleManager.moduleHeaterBronzeContainer));

		addShapedModuleRecipe(ModularMachinesApi.createDefaultStack(ModuleManager.moduleHeaterMagmariumContainers[0]), 
				"GPR",
				"COC",
				"RPG", 'R', "rodMagmarium", 'G', "gearMagmarium", 'P', "plateMagmarium", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 3), 'O', ModularMachinesApi.createDefaultStack(ModuleManager.moduleHeaterSteelContainers[0]));

		//Alloy Smleters
		addShapedModuleRecipe(ModularMachinesApi.createDefaultStack(ModuleManager.moduleAlloySmelterContainers[0]), 
				"PWP",
				"FCF",
				"PWP", 'W', "wireBronze", 'F', Blocks.FURNACE, 'P', "plateBronze", 'C', new ItemStack(ItemManager.itemModuleCore));
		addShapedModuleRecipe(ModularMachinesApi.createDefaultStack(ModuleManager.moduleAlloySmelterContainers[1]), 
				"PPP",
				"COC",
				"PPP", 'O', ModularMachinesApi.createDefaultStack(ModuleManager.moduleAlloySmelterContainers[0]), 'F', Blocks.FURNACE, 'P', "plateIron", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 1));

		//Pulverizers
		addShapedModuleRecipe(ModularMachinesApi.createDefaultStack(ModuleManager.modulePulverizerContainers[0]), 
				"RWF",
				"PCP",
				"FWR", 'W', "wireBronze", 'R', "rodBronze", 'F', Items.FLINT, 'P', "plateBronze", 'C', new ItemStack(ItemManager.itemModuleCore));
		addShapedModuleRecipe(ModularMachinesApi.createDefaultStack(ModuleManager.modulePulverizerContainers[1]), 
				"PRP",
				"COC",
				"PRP", 'O', ModularMachinesApi.createDefaultStack(ModuleManager.modulePulverizerContainers[0]), 'F', Blocks.FURNACE, 'P', "plateIron", 'R', "rodIron", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 1));
		addShapedRecipe(new ItemStack(ItemManager.itemChassis), "BIB", "I I", "BIB", 'B', Blocks.IRON_BARS, 'I', "ingotIron");

		//Lathes
		addShapedModuleRecipe(ModularMachinesApi.createDefaultStack(ModuleManager.moduleLatheContainers[0]), 
				"IGP",
				"RCR",
				"PGI", 'R', "rodBronze", 'I', "ingotBronze", 'G', "gearBronze", 'P', "plateBronze", 'C', new ItemStack(ItemManager.itemModuleCore));

		addShapedModuleRecipe(ModularMachinesApi.createDefaultStack(ModuleManager.moduleLatheContainers[1]), 
				"SGP",
				"COC",
				"PGS", 'R', "rodIron", 'S', "screwIron", 'G', "gearIron", 'P', "plateIron", 'O', ModularMachinesApi.createDefaultStack(ModuleManager.moduleLatheContainers[0]), 'C', new ItemStack(ItemManager.itemModuleCore, 1, 1));

	}

	private static void addNormalRecipes() {
		addShapedRecipe(new ItemStack(ItemManager.itemHammer), "+S+", "+S+", " - ", '+', "ingotIron", 'S', "stone", '-', "stickWood");
		addShapedRecipe(new ItemStack(ItemManager.itemFileIron), true, "  I", "FIF", "S  ", 'I', "ingotIron", 'F', Items.FLINT, 'S', "stickWood");
		addShapedRecipe(new ItemStack(ItemManager.itemFileDiamond), true, "  D", "FDF", "S  ", 'D', "gemDiamond", 'F', Items.FLINT, 'S', "stickWood");
		addShapedRecipe(new ItemStack(ItemManager.itemCutter), "  S", "FS ", "IF ", 'I', "ingotIron", 'F', Items.FLINT, 'S', "stickWood");
	}

	private static void addMachineRecipes() {
	}

	private static void addMetalRecipes() {
		for(MaterialList<IMetalMaterial> list : ItemManager.metals) {
			for(IMetalMaterial material : list) {
				for(String oreDict : material.getOreDicts()){
					addShapedRecipe(ItemManager.itemIngots.getStack(material), "+++", "+++", "+++", '+', "nugget" + oreDict);
					addShapelessRecipe(ItemManager.itemNuggets.getStack(material), "ingot" + oreDict);
				}
			}
		}
		for(MaterialList<IMetalMaterial> list : ItemManager.dusts){
			for(IMaterial material : list){
				if(ItemManager.itemDusts.getStack(material) != null && ItemManager.itemIngots.getStack(material) != null){
					GameRegistry.addSmelting(ItemManager.itemDusts.getStack(material), ItemManager.itemIngots.getStack(material), 0.5F);
				}
			}
		}
	}

	private static void addComponentRecipes() {
		for(IMetalMaterial material : ItemManager.itemCompPlates.materials) {
			ItemStack stack = ItemManager.itemCompPlates.getStack(material);
			String[] oreDicts = material.getOreDicts();
			if (oreDicts != null) {
				for(String oreDict : oreDicts) {
					addShapelessRecipe(stack, "ingot" + oreDict, "ingot" + oreDict, "toolHammer");
				}
			}
		}
		for(IMetalMaterial material : ItemManager.itemCompGears.materials) {
			ItemStack stack = ItemManager.itemCompGears.getStack(material);
			String[] oreDicts = material.getOreDicts();
			if (oreDicts != null) {
				for(String oreDict : oreDicts) {
					addShapedRecipe(stack, " + ", "+H+", " + ", '+', "plate" + oreDict, 'H', "toolHammer");
				}
			}
		}
		for(IMetalMaterial material : ItemManager.itemCompRods.materials) {
			ItemStack stack = ItemManager.itemCompRods.getStack(material);
			String[] oreDicts = material.getOreDicts();
			if (oreDicts != null) {
				for(String oreDict : oreDicts) {
					String ingot = "ingot" + oreDict;
					addShapelessRecipe(stack, ingot, ingot, "toolFile");
					ItemStack latheStack = new ItemStack(ItemManager.itemCompRods, 2, stack.getItemDamage());
					RecipeUtil.addLathe(ingot + "ToRod", new RecipeItem(new OreStack(ingot)), new RecipeItem(latheStack), 4, LatheModes.ROD);
				}
			}
		}
		for(IMetalMaterial material : ItemManager.itemCompWires.materials) {
			String[] oreDicts = material.getOreDicts();
			if(oreDicts != null){
				for(String oreDict : oreDicts) {
					addShapelessRecipe(ItemManager.itemCompWires.getStack(material, 3), "plate" + oreDict, "toolCutter");
					RecipeUtil.addLathe("plate" + oreDict + "ToWire", new RecipeItem(new OreStack("plate" + oreDict)),
							new RecipeItem(ItemManager.itemCompWires.getStack(material, 9)), 3, LatheModes.WIRE);
					if(ItemManager.itemNuggets.getStack(material) != null){
						GameRegistry.addSmelting(ItemManager.itemCompWires.getStack(material), ItemManager.itemNuggets.getStack(material), 0.08F);
					}else if(material == EnumMetalMaterials.GOLD){
						GameRegistry.addSmelting(ItemManager.itemCompWires.getStack(material), new ItemStack(Items.GOLD_NUGGET), 0.08F);
					}
				}
			}
		}
		for(IMetalMaterial material : ItemManager.itemCompScrews.materials) {
			String[] oreDicts = material.getOreDicts();
			if (oreDicts != null) {
				for(String oreDict : oreDicts) {
					RecipeUtil.addLathe("rod" + oreDict + "ToScrew", new RecipeItem(new OreStack("rod" + oreDict, 1)), new RecipeItem(ItemManager.itemCompScrews.getStack(material, 2)), 3,
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
		RecipeUtil.addPulverizer("CoalOreToDust", new OreStack("oreCoal"), new RecipeItem[] { new RecipeItem(ItemManager.itemDusts.getStack(EnumVanillaMaterials.COAL, 2)), new RecipeItem(new ItemStack(Items.COAL), 15) }, 15);
		RecipeUtil.addPulverizer("ObsidianBlockToDust", new OreStack("blockObsidian"), new RecipeItem[] { new RecipeItem(ItemManager.itemDusts.getStack(EnumVanillaMaterials.OBSIDIAN, 2)) }, 15);
		RecipeUtil.addPulverizer("IronOreToDust", new OreStack("oreIron"), new RecipeItem[] { new RecipeItem(ItemManager.itemDusts.getStack(EnumMetalMaterials.IRON, 2)) }, 15);
		RecipeUtil.addPulverizer("GoldOreToDust", new OreStack("oreGold"), new RecipeItem[] { new RecipeItem(ItemManager.itemDusts.getStack(EnumMetalMaterials.GOLD, 2)), new RecipeItem(ItemManager.itemDusts.getStack(EnumMetalMaterials.COPPER, 1), 20) }, 15);
		RecipeUtil.addPulverizer("DiamondOreToDust", new OreStack("oreDiamond"), new RecipeItem[] { new RecipeItem(ItemManager.itemDusts.getStack(EnumVanillaMaterials.DIAMOND, 2)) }, 15);
		RecipeUtil.addPulverizer("CopperOreToDust", new OreStack("oreCopper"), new RecipeItem[] { new RecipeItem(ItemManager.itemDusts.getStack(EnumMetalMaterials.COPPER, 2)), new RecipeItem(ItemManager.itemDusts.getStack(EnumMetalMaterials.GOLD, 1), 15) }, 12);
		RecipeUtil.addPulverizer("TinOreToDust", new OreStack("oreTin"), new RecipeItem[] { new RecipeItem(ItemManager.itemDusts.getStack(EnumMetalMaterials.TIN, 2)) }, 15);
		RecipeUtil.addPulverizer("SilverOreToDust", new OreStack("oreSilver"), new RecipeItem[] { new RecipeItem(ItemManager.itemDusts.getStack(EnumMetalMaterials.SILVER, 2)) }, 15);
		RecipeUtil.addPulverizer("LeadOreToDust", new OreStack("oreLead"), new RecipeItem[] { new RecipeItem(ItemManager.itemDusts.getStack(EnumMetalMaterials.LEAD, 2)) }, 15);
		RecipeUtil.addPulverizer("NickelOreToDust", new OreStack("oreNickel"), new RecipeItem[] { new RecipeItem(ItemManager.itemDusts.getStack(EnumMetalMaterials.NICKEL, 2)) }, 15);
		RecipeUtil.addPulverizer("AluminumOreToDust", new OreStack("oreAluminum"), new RecipeItem[] { new RecipeItem(ItemManager.itemDusts.getStack(EnumMetalMaterials.ALUMINIUM, 2)) }, 15);
		RecipeUtil.addPulverizer("AluminiumOreToDust", new OreStack("oreAluminium"), new RecipeItem[] { new RecipeItem(ItemManager.itemDusts.getStack(EnumMetalMaterials.ALUMINIUM, 2)) }, 15);
		RecipeUtil.addPulverizer("RedstoneOreToDust", new OreStack("oreRedstone"), new RecipeItem[] { new RecipeItem(new ItemStack(Items.REDSTONE, 8)) }, 15);
		RecipeUtil.addPulverizer("CoalToDust", new OreStack("itemCoal"), new RecipeItem[] { new RecipeItem(ItemManager.itemDusts.getStack(EnumVanillaMaterials.COAL, 2)) }, 7);
		/* INGOTS */
		for(IMaterial material : MaterialRegistry.getMaterials()){
			if(material != null && ItemManager.itemDusts.getStack(material) != null && material instanceof IMetalMaterial){
				String[] oreDicts = ((IMetalMaterial)material).getOreDicts();
				for(String oreDict : oreDicts){
					String ingot = "ingot" + oreDict;
					RecipeUtil.addPulverizer(ingot + "ToDust", new OreStack(ingot), new RecipeItem[] { new RecipeItem(ItemManager.itemDusts.getStack(material)) }, 7);
				}
			}
		}
		/*RecipeUtil.addPulverizer("IronIngotToDust", new OreStack("ingotIron"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 2)) }, 7);
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
		RecipeUtil.addPulverizer("InvarIngotToDust", new OreStack("ingotInvar"), new RecipeItem[] { new RecipeItem(new ItemStack(ItemManager.itemDusts, 1, 21)) }, 7);*/
	}

	private static void registerAlloySmelterRecipes() {
		/* BRONZE */
		RecipeUtil.addAlloySmelter("DustDustToBronze", new RecipeItem(new OreStack("dustTin", 1)), new RecipeItem(new OreStack("dustCopper", 3)), new RecipeItem[] { new RecipeItem(ItemManager.itemIngots.getStack(EnumMetalMaterials.BRONZE, 4)) }, 11, 125, 0.25);
		RecipeUtil.addAlloySmelter("DustIngotToBronze", new RecipeItem(new OreStack("ingotTin", 1)), new RecipeItem(new OreStack("ingotCopper", 3)), new RecipeItem[] { new RecipeItem(ItemManager.itemIngots.getStack(EnumMetalMaterials.BRONZE, 4)) }, 11, 125, 0.25);
		/* INVAR */
		RecipeUtil.addAlloySmelter("DustDustToInvar", new RecipeItem(new OreStack("dustIron", 2)), new RecipeItem(new OreStack("dustNickel", 1)), new RecipeItem[] { new RecipeItem(ItemManager.itemIngots.getStack(EnumMetalMaterials.INVAR, 4)) }, 9, 175, 0.5);
		RecipeUtil.addAlloySmelter("DustIngotToInvar", new RecipeItem(new OreStack("ingotIron", 2)), new RecipeItem(new OreStack("ingotNickel", 1)), new RecipeItem[] { new RecipeItem(ItemManager.itemIngots.getStack(EnumMetalMaterials.INVAR, 4)) }, 9, 175, 0.5);
	}

	private static void registerBoilerRecipes(){
		//ONLY FOR JEI
		RecipeUtil.addBoilerRecipe("WaterToSteam", new RecipeItem(new FluidStack(FluidRegistry.WATER, 15)), new RecipeItem(new FluidStack(FluidRegistry.getFluid("steam"), ModularMachinesApi.STEAM_PER_UNIT_WATER / 2)), 1, 100, 0D);
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
