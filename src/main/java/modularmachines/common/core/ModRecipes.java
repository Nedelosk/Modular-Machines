package modularmachines.common.core;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import net.minecraftforge.fml.common.registry.ForgeRegistries;

import modularmachines.common.items.ModuleItems;
import modularmachines.registry.ModItems;

public class ModRecipes {
	
	public static void registerRecipes() {
		// Casings
		addShapedRecipe("casing.bronze", new ItemStack(ModItems.itemCasings, 1), "+++", "+ +", "---", '+', "plateBronze", '-', Blocks.BRICK_BLOCK);
		/*addShapedRecipe("casing.iron", new ItemStack(ModItems.itemCasings, 1, 1), "+++", "+ +", "---", '+', "plateIron", '-', Blocks.BRICK_BLOCK);
		addShapedRecipe("casing.steel", new ItemStack(ModItems.itemCasings, 1, 2), "+++", "+ +", "---", '+', "plateSteel", '-', Blocks.BRICK_BLOCK);
		addShapedRecipe("casing.magmarium", new ItemStack(ModItems.itemCasings, 1, 3), "+++", "+ +", "---", '+', "plateMagmarium", '-', Blocks.BRICK_BLOCK);*/
		// Module Racks
		addShapedRecipe("module_rack.bricks", new ItemStack(ModItems.itemModuleRack, 1, 0), "BIB", "BIB", "BIB", 'I', "ingotBrick", 'B', new ItemStack(Blocks.BRICK_BLOCK));
		/*addShapedRecipe("module_rack.bronze", new ItemStack(ModItems.itemModuleRack, 1, 1), "IPI", "IPI", "IPI", 'I', "ingotBronze", 'P', "plateBronze");
		addShapedRecipe("module_rack.iron", new ItemStack(ModItems.itemModuleRack, 1, 2), "IPI", "IPI", "IPI", 'I', "ingotIron", 'P', "plateIron");
		addShapedRecipe("module_rack.steel", new ItemStack(ModItems.itemModuleRack, 1, 3), "IPI", "IPI", "IPI", 'I', "ingotSteel", 'P', "plateSteel");
		addShapedRecipe("module_rack.magmarium", new ItemStack(ModItems.itemModuleRack, 1, 4), "IPI", "IPI", "IPI", 'I', "ingotMagmarium", 'P', "plateMagmarium");*/
		// Engines
		addShapedRecipe("engine.steam.bronze", new ItemStack(ModItems.itemEngineSteam, 1, 0), "PG ", "BII", "PG ", 'I', "ingotBronze", 'G', "gearBronze", 'P', "plateBronze", 'B', Blocks.PISTON);
		// Turbines
		addShapedRecipe("turbine.steam.bronze", new ItemStack(ModItems.itemTurbineSteam, 1, 0), "SPI", "PBP", "IPS", 'I', "ingotBronze", 'B', "blockBronze", 'P', "plateBronze", 'S', "screwBronze");
		//Modules
		addShapedRecipe("water_intake", ModuleItems.WATER_INTAKE.get(), "GBG", "BCB", "IBI", 'B', new ItemStack(Blocks.IRON_BARS), 'G', "gearIron", 'I', "ingotIron", 'C', Items.BUCKET);
		addShapedRecipe("large_tank", ModuleItems.LARGE_TANK.get(), "BHB", "IGI", "BHB", 'G', "blockGlass", 'H', "gearBronze", 'I', "ingotBronze", 'B', "ingotBrick");
		addShapedRecipe("firebox", ModuleItems.FIREBOX.get(), "IBI", "BFB", "GBG", 'G', "gearIron", 'B', new ItemStack(Blocks.IRON_BARS), 'I', "ingotIron", 'F', new ItemStack(Items.FIRE_CHARGE));
		addShapedRecipe("boiler", ModuleItems.BOILER.get(), "PPP", "G G", "PFP", 'P', "plateBronze", 'G', "gearBronze", 'F', Blocks.FURNACE);
		//Screwdriver
		addShapedRecipe("screwdriver", new ItemStack(ModItems.screwdriver), "  I", "TI ", "SC ", 'I', "ingotIron", 'S', "stickWood", 'C', "ingotCopper", 'T', "ingotTin");
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
