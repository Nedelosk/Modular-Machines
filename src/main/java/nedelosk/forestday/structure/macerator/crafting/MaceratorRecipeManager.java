package nedelosk.forestday.structure.macerator.crafting;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestday.api.crafting.IMaceratorRecipe;
import nedelosk.forestday.structure.alloysmelter.crafting.AlloySmelterRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MaceratorRecipeManager implements IMaceratorRecipe {

	private static ArrayList<MaceratorRecipe> recipes = new ArrayList();
	
	public static MaceratorRecipeManager instance;
	
	public static void addRecipe(MaceratorRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public static void removeRecipe(MaceratorRecipe recipe)
	{
		recipes.remove(recipe);
	}
	
	public static void addAllRecipes(List<MaceratorRecipe> recipe)
	{
		recipes.addAll(recipe);
	}
	
	public static List<MaceratorRecipe> removeRecipes(ItemStack stack)
	{
		List<MaceratorRecipe> list = new ArrayList();
		for(MaceratorRecipe recipe : recipes)
		{
			if(recipe.getOutput1().getItem() == stack.getItem() && recipe.getOutput1().getItemDamage() == stack.getItemDamage())
			{
				list.add(recipe);
			}
		}
		return list;
	}
	
	public void addRecipe(ItemStack input, ItemStack output, int minRoughness, int maxRoughness, int burnTime, boolean isNEI)
	{
		recipes.add(new MaceratorRecipe(input, output, minRoughness, maxRoughness, burnTime, isNEI));
	}
	
	public void addRecipe(String input, ItemStack output, int minRoughness, int maxRoughness, int burnTime, boolean isNEI)
	{
		recipes.add(new MaceratorRecipe(input, output, minRoughness, maxRoughness, burnTime, isNEI));
	}
	
	public static MaceratorRecipe getRecipe(ItemStack input, int roughness){
		for(MaceratorRecipe sr : MaceratorRecipeManager.recipes){
			if(sr.getInput() != null)
			{
			if (sr.getInput().getItem() == input.getItem() && roughness < sr.getMaxRoughness() && roughness > sr.getMinRoughness() && input.getItemDamage() == sr.getInput().getItemDamage()){
				return sr;
			}
			}
			if(sr.getOredictInput() != null)
			{
			List<ItemStack> list = OreDictionary.getOres(sr.getOredictInput());
			ItemStack stackI = input.copy();
			stackI.stackSize = 1;
			for(ItemStack stack : list)
			{
				if(input.getItem() == stack.getItem() && input.getItemDamage() == stack.getItemDamage() && roughness < sr.getMaxRoughness() && roughness > sr.getMinRoughness())
				{
					return sr;
				}
			}
			}
		}
		
		return null;
	}
	
	public static MaceratorRecipe getRecipe(ItemStack input)
	{
		for(MaceratorRecipe sr : MaceratorRecipeManager.recipes){
			if(sr.getInput() != null)
			{
			if (sr.getInput().getItem() == input.getItem() && input.getItemDamage() == sr.getInput().getItemDamage()){
				return sr;
			}
			}
			if(sr.getOredictInput() != null)
			{
			List<ItemStack> list = OreDictionary.getOres(sr.getOredictInput());
			ItemStack stackI = input.copy();
			stackI.stackSize = 1;
			for(ItemStack stack : list)
			{
				if(input.getItem() == stack.getItem() && input.getItemDamage() == stack.getItemDamage())
				{
					return sr;
				}
			}
			}
		}
		
		return null;
	}

	public static MaceratorRecipeManager getInstance() {
		return instance;
	}
	
	public static List<MaceratorRecipe> getRecipe()
	{
		return recipes;
	}
	
}