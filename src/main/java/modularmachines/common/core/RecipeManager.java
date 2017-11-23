package modularmachines.common.core;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import net.minecraftforge.fml.common.registry.ForgeRegistries;

import modularmachines.common.core.managers.ModItems;

public class RecipeManager {
	
	public static void registerRecipes() {
		//RecipeRegistry.registerRecipeHandler(new RecipeHandlerHeat("Boiler"));
		//RecipeRegistry.registerRecipeHandler(new RecipeHandlerHeat("AlloySmelter"));
		//RecipeRegistry.registerRecipeHandler(new RecipeHandlerDefault("Pulverizer"));
		//RecipeRegistry.registerRecipeHandler(new RecipeHandlerToolMode("Lathe", RecipeUtil.LATHEMODE));
		registerSawMillRecipes();
		registerPulverizerRecipes();
		//registerAlloySmelterRecipes();
		//registerBoilerRecipes();
		addMachineRecipes();
		addNormalRecipes();
		addModuleRecipes();
	}
	
	private static void addModuleRecipes() {
		// Casings
		addShapedRecipe(new ItemStack(ModItems.itemCasings, 1), "+++", "+ +", "---", '+', "plateBronze", '-', Blocks.BRICK_BLOCK);
		addShapedRecipe(new ItemStack(ModItems.itemCasings, 1, 1), "+++", "+ +", "---", '+', "plateIron", '-', Blocks.BRICK_BLOCK);
		addShapedRecipe(new ItemStack(ModItems.itemCasings, 1, 2), "+++", "+ +", "---", '+', "plateSteel", '-', Blocks.BRICK_BLOCK);
		addShapedRecipe(new ItemStack(ModItems.itemCasings, 1, 3), "+++", "+ +", "---", '+', "plateMagmarium", '-', Blocks.BRICK_BLOCK);
		// Module Storages
		addShapedRecipe(new ItemStack(ModItems.itemModuleRack), "BIB", "BIB", "BIB", 'I', "stickWood", 'B', "logWood");
		addShapedRecipe(new ItemStack(ModItems.itemModuleRack, 1, 1), "BIB", "BIB", "BIB", 'I', "ingotBrick", 'B', new ItemStack(Blocks.BRICK_BLOCK));
		addShapedRecipe(new ItemStack(ModItems.itemModuleRack, 1, 2), "IPI", "IPI", "IPI", 'I', "ingotBronze", 'P', "plateBronze");
		addShapedRecipe(new ItemStack(ModItems.itemModuleRack, 1, 3), "IPI", "IPI", "IPI", 'I', "ingotIron", 'P', "plateIron");
		addShapedRecipe(new ItemStack(ModItems.itemModuleRack, 1, 4), "IPI", "IPI", "IPI", 'I', "ingotSteel", 'P', "plateSteel");
		addShapedRecipe(new ItemStack(ModItems.itemModuleRack, 1, 5), "IPI", "IPI", "IPI", 'I', "ingotMagmarium", 'P', "plateMagmarium");
		// Engines
		addShapedRecipe(new ItemStack(ModItems.itemEngineSteam, 1, 0), "GHP", "BII", "GHP", 'I', "rodBronze", 'H', "ingotBronze", 'G', "gearBronze", 'P', "plateBronze", 'B', Blocks.PISTON);
		addShapedRecipe(new ItemStack(ModItems.itemEngineSteam, 1, 1), "GHP", "BII", "GHP", 'I', "rodIron", 'H', "ingotIron", 'G', "gearIron", 'P', "plateIron", 'B', new ItemStack(ModItems.itemEngineSteam, 1, 0));
		addShapedRecipe(new ItemStack(ModItems.itemEngineSteam, 1, 2), "GHP", "BII", "GHP", 'I', "rodSteel", 'H', "ingotSteel", 'G', "gearSteel", 'P', "plateSteel", 'B', new ItemStack(ModItems.itemEngineSteam, 1, 1));
		addShapedRecipe(new ItemStack(ModItems.itemEngineSteam, 1, 3), "GHP", "BII", "GHP", 'I', "rodMagmarium", 'H', "ingotMagmarium", 'G', "gearMagmarium", 'P', "plateMagmarium", 'B', new ItemStack(ModItems.itemEngineSteam, 1, 2));
		// Turbines
		addShapedRecipe(new ItemStack(ModItems.itemTurbineSteam, 1, 0), "SPI", "PBP", "IPS", 'I', "ingotBronze", 'B', "blockBronze", 'P', "plateBronze", 'S', "screwBronze");
		addShapedRecipe(new ItemStack(ModItems.itemTurbineSteam, 1, 1), "SPR", "PBP", "RPS", 'R', "rodIron", 'B', "blockIron", 'P', "plateIron", 'S', "screwIron");
		addShapedRecipe(new ItemStack(ModItems.itemTurbineSteam, 1, 2), "SPR", "PBP", "RPS", 'R', "rodSteel", 'B', "blockSteel", 'P', "plateSteel", 'S', "screwSteel");
		addShapedRecipe(new ItemStack(ModItems.itemTurbineSteam, 1, 3), "SPR", "PBP", "RPS", 'R', "rodMagmarium", 'B', "blockMagmarium", 'P', "plateMagmarium", 'S', "screwMagmarium");
		// Boilers
		/*addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleBoilerContainers[0]), "PPP", "GCG", "PPP", 'G', "blockGlass", 'P', "plateBronze", 'C', new ItemStack(ItemManager.itemModuleCore));
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleBoilerContainers[1]), "PGP", "COC", "PGP", 'G', "blockGlass", 'P', "plateIron", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 1), 'O',
				createDefaultStack(ModuleManager.moduleBoilerContainers[0]));
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleBoilerContainers[2]), "PGP", "COC", "PGP", 'G', "blockGlass", 'P', "plateSteel", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 2), 'O',
				createDefaultStack(ModuleManager.moduleBoilerContainers[1]));
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleBoilerContainers[3]), "PGP", "COC", "PGP", 'G', "blockGlass", 'P', "plateMagmarium", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 3), 'O',
				createDefaultStack(ModuleManager.moduleBoilerContainers[2]));
		// Heaters
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleHeaterSteamContainer), "GPR", "FCF", "RPG", 'R', "rodBronze", 'G', "gearBronze", 'P', "plateBronze", 'F', "blockGlass", 'C', new ItemStack(ItemManager.itemModuleCore));
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleHeaterBronzeContainer), "GPR", "FCF", "RPG", 'R', "rodBronze", 'G', "gearBronze", 'P', "plateBronze", 'F', Blocks.FURNACE, 'C', new ItemStack(ItemManager.itemModuleCore));
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleHeaterIronContainers[0]), "GPR", "COC", "RPG", 'R', "rodIron", 'G', "gearIron", 'P', "plateIron", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 1), 'O',
				createDefaultStack(ModuleManager.moduleHeaterBronzeContainer));
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleHeaterIronContainers[1]), "GPR", "COC", "RPG", 'R', "rodIron", 'G', "gearIron", 'P', "plateIron", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 1), 'O',
				createDefaultStack(ModuleManager.moduleHeaterBronzeContainer));
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleHeaterSteelContainers[0]), "GPR", "COC", "RPG", 'R', "rodSteel", 'G', "gearSteel", 'P', "plateSteel", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 2), 'O',
				createDefaultStack(ModuleManager.moduleHeaterIronContainers[0]));
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleHeaterSteelContainers[1]), "GPR", "COC", "RPG", 'R', "rodSteel", 'G', "gearSteel", 'P', "plateSteel", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 2), 'O',
				createDefaultStack(ModuleManager.moduleHeaterIronContainers[1]));
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleHeaterSteelContainers[2]), "GPR", "COC", "RPG", 'R', "rodSteel", 'G', "gearSteel", 'P', "plateSteel", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 2), 'O',
				createDefaultStack(ModuleManager.moduleHeaterIronContainers[1]));
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleHeaterMagmariumContainers[0]), "GPR", "COC", "RPG", 'R', "rodMagmarium", 'G', "gearMagmarium", 'P', "plateMagmarium", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 3), 'O',
				createDefaultStack(ModuleManager.moduleHeaterSteelContainers[0]));
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleHeaterMagmariumContainers[1]), "GPR", "COC", "RPG", 'R', "rodMagmarium", 'G', "gearMagmarium", 'P', "plateMagmarium", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 3), 'O',
				createDefaultStack(ModuleManager.moduleHeaterSteelContainers[1]));
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleHeaterMagmariumContainers[2]), "GPR", "COC", "RPG", 'R', "rodMagmarium", 'G', "gearMagmarium", 'P', "plateMagmarium", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 3), 'O',
				createDefaultStack(ModuleManager.moduleHeaterSteelContainers[2]));
		// Alloy Smleters
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleAlloySmelterContainers[0]), "PWP", "FCF", "PWP", 'W', "wireBronze", 'F', Blocks.FURNACE, 'P', "plateBronze", 'C', new ItemStack(ItemManager.itemModuleCore));
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleAlloySmelterContainers[1]), "PPP", "COC", "PPP", 'O', createDefaultStack(ModuleManager.moduleAlloySmelterContainers[0]), 'F', Blocks.FURNACE, 'P', "plateIron", 'C',
				new ItemStack(ItemManager.itemModuleCore, 1, 1));
		// Pulverizers
		addShapedModuleRecipe(createDefaultStack(ModuleManager.modulePulverizerContainers[0]), "RWF", "PCP", "FWR", 'W', "wireBronze", 'R', "rodBronze", 'F', Items.FLINT, 'P', "plateBronze", 'C', new ItemStack(ItemManager.itemModuleCore));
		addShapedModuleRecipe(createDefaultStack(ModuleManager.modulePulverizerContainers[1]), "PRP", "COC", "PRP", 'O', createDefaultStack(ModuleManager.modulePulverizerContainers[0]), 'F', Blocks.FURNACE, 'P', "plateIron", 'R', "rodIron", 'C',
				new ItemStack(ItemManager.itemModuleCore, 1, 1));
		addShapedRecipe(new ItemStack(ItemManager.itemChassis), "BIB", "I I", "BIB", 'B', Blocks.IRON_BARS, 'I', "ingotIron");
		// Lathes
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleLatheContainers[0]), "IGP", "RCR", "PGI", 'R', "rodBronze", 'I', "ingotBronze", 'G', "gearBronze", 'P', "plateBronze", 'C', new ItemStack(ItemManager.itemModuleCore));
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleLatheContainers[1]), "SGP", "COC", "PGS", 'R', "rodIron", 'S', "screwIron", 'G', "gearIron", 'P', "plateIron", 'O', createDefaultStack(ModuleManager.moduleLatheContainers[0]), 'C',
				new ItemStack(ItemManager.itemModuleCore, 1, 1));
		// Cleaner
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleModuleCleanerContainer), "GPG", "CBC", "GUG", 'B', "blockRedstone", 'G', "blockGlass", 'U', "dustRedstone", 'P', "plateIron", 'C', new ItemStack(ItemManager.itemModuleCore, 1, 1));
		// Controllers
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleControllerContainers[0]), "PUL", "RBC", "LUP", 'L', Blocks.LEVER, 'U', "dustRedstone", 'R', Items.REPEATER, 'C', Items.COMPARATOR, 'P', "plateBronze", 'B',
				new ItemStack(ItemManager.itemModuleCore));
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleControllerContainers[1]), "PUL", "BOB", "LUP", 'L', Blocks.LEVER, 'U', "dustRedstone", 'O', createDefaultStack(ModuleManager.moduleControllerContainers[0]), 'P', "plateIron", 'B',
				new ItemStack(ItemManager.itemModuleCore, 1, 1));
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleControllerContainers[2]), "PUL", "BOB", "LUP", 'L', Blocks.LEVER, 'U', "dustRedstone", 'O', createDefaultStack(ModuleManager.moduleControllerContainers[1]), 'P', "plateSteel", 'B',
				new ItemStack(ItemManager.itemModuleCore, 1, 2));
		addShapedModuleRecipe(createDefaultStack(ModuleManager.moduleControllerContainers[3]), "PUL", "BOB", "LUP", 'L', Blocks.LEVER, 'U', "dustRedstone", 'O', createDefaultStack(ModuleManager.moduleControllerContainers[2]), 'P', "plateMagmarium", 'B',
				new ItemStack(ItemManager.itemModuleCore, 1, 2));*/
	}
	
	private static void addNormalRecipes() {
	}
	
	private static void addMachineRecipes() {
	}
	
	private static void registerSawMillRecipes() {
		// RecipeUtil.addSawMill("OakPlanks", new ItemStack(Blocks.LOG, 1, 0),
		// new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.PLANKS, 6,
		// 0))/*, new RecipeItem(new ItemStack(ItemManager.itemNature), 50)*/ },
		// 10, 300);
		// RecipeUtil.addSawMill("SprucePlanks", new ItemStack(Blocks.LOG, 1,
		// 1), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.PLANKS, 6,
		// 1))/*, new RecipeItem(new ItemStack(ItemManager.itemNature), 50)*/ },
		// 10,
		// 300);
		// RecipeUtil.addSawMill("BirchPlanks", new ItemStack(Blocks.LOG, 1, 2),
		// new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.PLANKS, 6,
		// 2))/*, new RecipeItem(new ItemStack(ItemManager.itemNature), 50)*/ },
		// 10,
		// 300);
		// RecipeUtil.addSawMill("JunglePlanks", new ItemStack(Blocks.LOG, 1,
		// 3), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.PLANKS, 6,
		// 3))/*, new RecipeItem(new ItemStack(ItemManager.itemNature), 50)*/ },
		// 10,
		// 300);
		// RecipeUtil.addSawMill("AcaciaPlanks", new ItemStack(Blocks.LOG2, 1,
		// 0), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.PLANKS, 6,
		// 4))/*, new RecipeItem(new ItemStack(ItemManager.itemNature), 50)*/ },
		// 10,
		// 300);
		// RecipeUtil.addSawMill("DarkOakPlanks", new ItemStack(Blocks.LOG2, 1,
		// 1), new RecipeItem[] { new RecipeItem(new ItemStack(Blocks.PLANKS, 6,
		// 5))/*, new RecipeItem(new ItemStack(ItemManager.itemNature), 50)*/ },
		// 10,
		// 300);
	}
	
	private static void registerPulverizerRecipes() {
		// ORES TO DUST 
		//IRecipeHandler handler = RecipeRegistry.registerRecipeHandler(new RecipeHandler(MachineCategorys.PULVERIZER));
		//handler.addRecipe(new RecipePulverizer(new OreStack("oreCoal"), ItemManager.itemDusts.getStack(EnumVanillaMaterials.COAL, 2), new ItemStack(Items.COAL), 0.15F , 15));
		//RecipeUtil.addPulverizer("ObsidianBlockToDust", new OreStack("blockObsidian"), new RecipeItem[] { new RecipeItem(ItemManager.itemDusts.getStack(EnumVanillaMaterials.OBSIDIAN, 2)) }, 15);
		/*handler.addRecipe(new RecipePulverizer(new OreStack("oreIron"), ItemManager.itemDusts.getStack(EnumMaterial.IRON, 2), 15));
		handler.addRecipe(new RecipePulverizer(new OreStack("oreGold"), ItemManager.itemDusts.getStack(EnumMaterial.GOLD, 2), ItemManager.itemDusts.getStack(EnumMaterial.COPPER, 1), 0.20F, 15));
		//handler.addRecipe(new RecipePulverizer(new OreStack("oreDiamond"), ItemManager.itemDusts.getStack(EnumVanillaMaterials.DIAMOND, 2), 15);
		handler.addRecipe(new RecipePulverizer(new OreStack("oreCopper"), ItemManager.itemDusts.getStack(EnumMaterial.COPPER, 2), ItemManager.itemDusts.getStack(EnumMaterial.GOLD, 1), 0.15F, 12));
		handler.addRecipe(new RecipePulverizer(new OreStack("oreTin"), ItemManager.itemDusts.getStack(EnumMaterial.TIN, 2), 15));
		handler.addRecipe(new RecipePulverizer(new OreStack("oreSilver"), ItemManager.itemDusts.getStack(EnumMaterial.SILVER, 2), 15));
		handler.addRecipe(new RecipePulverizer(new OreStack("oreLead"), ItemManager.itemDusts.getStack(EnumMaterial.LEAD, 2), 15));
		handler.addRecipe(new RecipePulverizer(new OreStack("oreNickel"), ItemManager.itemDusts.getStack(EnumMaterial.NICKEL, 2), 15));
		handler.addRecipe(new RecipePulverizer(new OreStack("oreAluminum"), ItemManager.itemDusts.getStack(EnumMaterial.ALUMINIUM, 2), 15));
		handler.addRecipe(new RecipePulverizer(new OreStack("oreAluminium"), ItemManager.itemDusts.getStack(EnumMaterial.ALUMINIUM, 2), 15));
		handler.addRecipe(new RecipePulverizer(new OreStack("oreRedstone"), new ItemStack(Items.REDSTONE, 8), 15));
		//	handler.addRecipe(new RecipePulverizer(new OreStack("itemCoal"), ItemManager.itemDusts.getStack(EnumVanillaMaterials.COAL, 2), 7));*/
		// INGOTS TO DUST 
		/*for (IMaterial material : MaterialRegistry.getMaterials()) {
			if (!(material instanceof EnumVanillaMaterials)) {
				if (material != null && ItemManager.itemDusts.getStack(material) != null && material instanceof IMetalMaterial) {
					String[] oreDicts = ((IMetalMaterial) material).getOreDicts();
					for (String oreDict : oreDicts) {
						String ingot = "ingot" + oreDict;
						RecipeUtil.addPulverizer(ingot + "ToDust", new OreStack(ingot), new RecipeItem[] { new RecipeItem(ItemManager.itemDusts.getStack(material)) }, 7);
					}
				}
			}
		}*/
	}

	/*private static void registerAlloySmelterRecipes() {
		// BRONZE 
		RecipeUtil.addAlloySmelter("DustDustToBronze", new RecipeItem(new OreStack("dustTin", 1)), new RecipeItem(new OreStack("dustCopper", 3)), new RecipeItem[] { new RecipeItem(ItemManager.itemIngots.getStack(EnumMaterial.BRONZE, 4)) }, 11, 125,
				0.25);
		RecipeUtil.addAlloySmelter("DustIngotToBronze", new RecipeItem(new OreStack("ingotTin", 1)), new RecipeItem(new OreStack("ingotCopper", 3)), new RecipeItem[] { new RecipeItem(ItemManager.itemIngots.getStack(EnumMaterial.BRONZE, 4)) }, 11,
				125, 0.25);
		// INVAR 
		RecipeUtil.addAlloySmelter("DustDustToInvar", new RecipeItem(new OreStack("dustIron", 2)), new RecipeItem(new OreStack("dustNickel", 1)), new RecipeItem[] { new RecipeItem(ItemManager.itemIngots.getStack(EnumMaterial.INVAR, 4)) }, 9, 175,
				0.5);
		RecipeUtil.addAlloySmelter("DustIngotToInvar", new RecipeItem(new OreStack("ingotIron", 2)), new RecipeItem(new OreStack("ingotNickel", 1)), new RecipeItem[] { new RecipeItem(ItemManager.itemIngots.getStack(EnumMaterial.INVAR, 4)) }, 9, 175,
				0.5);
	}

	private static void registerBoilerRecipes() {
		// ONLY FOR JEI
		RecipeUtil.addBoilerRecipe("WaterToSteam", new RecipeItem(new FluidStack(FluidRegistry.WATER, 15)), new RecipeItem(new FluidStack(FluidRegistry.getFluid("steam"), HeatManager.STEAM_PER_UNIT_WATER / 2)), 1, 100, 0D);
	}

	private static void addShapedModuleRecipe(ItemStack stack, ItemStack holder, Object... obj) {
		GameRegistry.addRecipe(new ModuleCrafterRecipe(stack, holder, obj));
	}

	private static void addShapedModuleRecipe(ItemStack stack, boolean generateHolder, Object... obj) {
		ItemStack holder = null;
		if (generateHolder) {
			IModuleItemContainer container = modularmachines.api.modules.ModuleManager.getContainerFromItem(stack);
			if (container != null) {
				IMaterial material = container.getMaterial();
				EnumModuleSizes size = container.getSize();
				if (material instanceof IMetalMaterial) {
					holder = modularmachines.api.modules.ModuleManager.getHolder((IMetalMaterial) material, (size == EnumModuleSizes.SMALL) ? 2 : (size == EnumModuleSizes.MEDIUM) ? 1 : 0);
				}
			}
		}
		GameRegistry.addRecipe(new ModuleCrafterRecipe(stack, holder, obj));
	}

	private static void addShapedModuleRecipe(ItemStack stack, Object... obj) {
		ItemStack holder = null;
		IModuleItemContainer container = modularmachines.api.modules.ModuleManager.getContainerFromItem(stack);
		if (container != null) {
			IMaterial material = container.getMaterial();
			EnumModuleSizes size = container.getSize();
			if (material instanceof IMetalMaterial) {
				holder = modularmachines.api.modules.ModuleManager.getHolder((IMetalMaterial) material, (size == EnumModuleSizes.SMALL) ? 2 : (size == EnumModuleSizes.MEDIUM) ? 1 : 0);
			}
		}
		GameRegistry.addRecipe(new ModuleCrafterRecipe(stack, holder, obj));
	}*/
	
	private static int i = 0;
	
	private static void addShapedRecipe(ItemStack stack, Object... obj) {
		addShapedRecipe(Integer.toString(i++), stack, obj);
	}
	
	private static void addShapedRecipe(String name, ItemStack stack, Object... obj) {
		ShapedOreRecipe recipe = new ShapedOreRecipe(null, stack, obj);
		recipe.setRegistryName(new ResourceLocation(Constants.MOD_ID, name));
		ForgeRegistries.RECIPES.register(recipe);
	}
	
	private static void addShapelessRecipe(String name, ItemStack stack, Object... obj) {
		ShapelessOreRecipe recipe = new ShapelessOreRecipe(null, stack, obj);
		recipe.setRegistryName(new ResourceLocation(Constants.MOD_ID, name));
		ForgeRegistries.RECIPES.register(recipe);
	}
}
