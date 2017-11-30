package modularmachines.common.core;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import net.minecraftforge.fml.common.registry.ForgeRegistries;

import modularmachines.common.core.managers.ModItems;
import modularmachines.common.items.ModuleItems;

public class ModRecipes {
	
	public static void registerRecipes() {
		// Casings
		addShapedRecipe("casing.bronze", new ItemStack(ModItems.itemCasings, 1), "+++", "+ +", "---", '+', "plateBronze", '-', Blocks.BRICK_BLOCK);
		addShapedRecipe("casing.iron", new ItemStack(ModItems.itemCasings, 1, 1), "+++", "+ +", "---", '+', "plateIron", '-', Blocks.BRICK_BLOCK);
		addShapedRecipe("casing.steel", new ItemStack(ModItems.itemCasings, 1, 2), "+++", "+ +", "---", '+', "plateSteel", '-', Blocks.BRICK_BLOCK);
		addShapedRecipe("casing.magmarium", new ItemStack(ModItems.itemCasings, 1, 3), "+++", "+ +", "---", '+', "plateMagmarium", '-', Blocks.BRICK_BLOCK);
		// Module Racks
		addShapedRecipe("module_rack.bricks", new ItemStack(ModItems.itemModuleRack, 1, 0), "BIB", "BIB", "BIB", 'I', "ingotBrick", 'B', new ItemStack(Blocks.BRICK_BLOCK));
		addShapedRecipe("module_rack.bronze", new ItemStack(ModItems.itemModuleRack, 1, 1), "IPI", "IPI", "IPI", 'I', "ingotBronze", 'P', "plateBronze");
		addShapedRecipe("module_rack.iron", new ItemStack(ModItems.itemModuleRack, 1, 2), "IPI", "IPI", "IPI", 'I', "ingotIron", 'P', "plateIron");
		addShapedRecipe("module_rack.steel", new ItemStack(ModItems.itemModuleRack, 1, 3), "IPI", "IPI", "IPI", 'I', "ingotSteel", 'P', "plateSteel");
		addShapedRecipe("module_rack.magmarium", new ItemStack(ModItems.itemModuleRack, 1, 4), "IPI", "IPI", "IPI", 'I', "ingotMagmarium", 'P', "plateMagmarium");
		// Engines
		addShapedRecipe("engine.steam.bronze", new ItemStack(ModItems.itemEngineSteam, 1, 0), "GHP", "BII", "GHP", 'I', "rodBronze", 'H', "ingotBronze", 'G', "gearBronze", 'P', "plateBronze", 'B', Blocks.PISTON);
		addShapedRecipe("engine.steam.iron", new ItemStack(ModItems.itemEngineSteam, 1, 1), "GHP", "BII", "GHP", 'I', "rodIron", 'H', "ingotIron", 'G', "gearIron", 'P', "plateIron", 'B', new ItemStack(ModItems.itemEngineSteam, 1, 0));
		addShapedRecipe("engine.steam.steel", new ItemStack(ModItems.itemEngineSteam, 1, 2), "GHP", "BII", "GHP", 'I', "rodSteel", 'H', "ingotSteel", 'G', "gearSteel", 'P', "plateSteel", 'B', new ItemStack(ModItems.itemEngineSteam, 1, 1));
		addShapedRecipe("engine.steam.magmarium", new ItemStack(ModItems.itemEngineSteam, 1, 3), "GHP", "BII", "GHP", 'I', "rodMagmarium", 'H', "ingotMagmarium", 'G', "gearMagmarium", 'P', "plateMagmarium", 'B', new ItemStack(ModItems.itemEngineSteam, 1, 2));
		// Turbines
		addShapedRecipe("engine.steam.bronze", new ItemStack(ModItems.itemTurbineSteam, 1, 0), "SPI", "PBP", "IPS", 'I', "ingotBronze", 'B', "blockBronze", 'P', "plateBronze", 'S', "screwBronze");
		addShapedRecipe("engine.steam.iron", new ItemStack(ModItems.itemTurbineSteam, 1, 1), "SPR", "PBP", "RPS", 'R', "rodIron", 'B', "blockIron", 'P', "plateIron", 'S', "screwIron");
		addShapedRecipe("engine.steam.steel", new ItemStack(ModItems.itemTurbineSteam, 1, 2), "SPR", "PBP", "RPS", 'R', "rodSteel", 'B', "blockSteel", 'P', "plateSteel", 'S', "screwSteel");
		addShapedRecipe("engine.steam.magmarium", new ItemStack(ModItems.itemTurbineSteam, 1, 3), "SPR", "PBP", "RPS", 'R', "rodMagmarium", 'B', "blockMagmarium", 'P', "plateMagmarium", 'S', "screwMagmarium");
		
		addShapedRecipe("water_intake", ModuleItems.WATER_INTAKE.get(), "GBG", "BCB", "IBI", 'B', new ItemStack(Blocks.IRON_BARS), 'G', "gearIron", 'I', "ingotIron", 'C', Items.BUCKET);
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
