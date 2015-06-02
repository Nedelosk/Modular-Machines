/**
 * (C) 2015 Nedelosk
 */
package nedelosk.forestday.api.structure.crafting;

import java.util.ArrayList;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestday.api.crafting.ForestdayCrafting;
import nedelosk.forestday.api.crafting.IMaceratorRecipe;
import nedelosk.forestday.common.core.Defaults;
import nedelosk.forestday.common.registrys.ForestdayItemRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class BrokenRecipes {

	public static ArrayList<BrokenItemStack> maceratorRecipes = new ArrayList<BrokenItemStack>();
	
	public static void addMaceratorRecipe(ItemStack input, ItemStack output, int minRoughness, int maxRoughness)
	{
		maceratorRecipes.add(new BrokenItemStack(input, output, minRoughness, maxRoughness));
	}
	
	public static void addMaceratorRecipe(String input, ItemStack output, int minRoughness, int maxRoughness)
	{
		maceratorRecipes.add(new BrokenItemStack(input, output, minRoughness, maxRoughness));
		Defaults.log(Level.INFO,"Add Broken Recipe: {0}, {1}, {2}, {3}", input, output, minRoughness, maxRoughness);
	}
	
	public static void addMaceratorOreRecipe(ItemStack input, ItemStack outputPowder, ItemStack outputDust, IMaceratorRecipe recipe)
	{
		maceratorRecipes.add(new BrokenItemStack(input, new ItemStack(Blocks.gravel), 1, 99));
		recipe.addRecipe(input, outputPowder, 100, 299, 100, true);
		maceratorRecipes.add(new BrokenItemStack(input, new ItemStack(GameRegistry.findItem("Forestday", "fd.item.dust"), 2, 4), 300, 749));
		recipe.addRecipe(input, outputDust, 500, 549, 100, true);
		maceratorRecipes.add(new BrokenItemStack(input, new ItemStack(GameRegistry.findItem("Forestday", "fd.item.powder"), 2, 4), 550, 749));
		Defaults.log(Level.INFO,"Add Broken Ore Recipe: {0}, {1}, {3}", input, outputPowder, outputDust);
	}
	
	public static void addMaceratorOreRecipe(String input, ItemStack outputPowder, ItemStack outputDust, IMaceratorRecipe recipe)
	{
		maceratorRecipes.add(new BrokenItemStack(input, new ItemStack(Blocks.gravel), 1, 99));
		recipe.addRecipe(input, outputPowder, 100, 299, 100, true);
		maceratorRecipes.add(new BrokenItemStack(input, new ItemStack(GameRegistry.findItem("Forestday", "fd.item.dust"), 2, 4), 300, 499));
		recipe.addRecipe(input, outputDust, 500, 549, 100, true);
		maceratorRecipes.add(new BrokenItemStack(input, new ItemStack(GameRegistry.findItem("Forestday", "fd.item.powder"), 2, 4), 550, 749));
		Defaults.log(Level.INFO,"Add Broken Ore Recipe: {0}, {1}, {3}", input, outputPowder, outputDust);
	}
	
}
