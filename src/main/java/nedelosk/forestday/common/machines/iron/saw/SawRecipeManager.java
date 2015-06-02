package nedelosk.forestday.common.machines.iron.saw;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestday.api.crafting.ISawRecipe;
import nedelosk.forestday.common.machines.wood.workbench.WorkbenchRecipe;
import net.minecraft.item.ItemStack;

public class SawRecipeManager implements ISawRecipe{

	private static ArrayList<SawRecipe> recipes = new ArrayList();
	
	public static SawRecipeManager instance;
	
	public static void addRecipe(SawRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public static void removeRecipe(WorkbenchRecipe recipe)
	{
		recipes.remove(recipe);
	}
	
	public static void addAllRecipes(List<SawRecipe> recipe)
	{
		recipes.addAll(recipe);
	}
	
	public static List<SawRecipe> removeRecipes(ItemStack stack)
	{
		List<SawRecipe> list = new ArrayList();
		for(SawRecipe recipe : recipes)
		{
			if(recipe.getOutput().getItem() == stack.getItem() && recipe.getOutput().getItemDamage() == stack.getItemDamage())
			{
				list.add(recipe);
			}
		}
		return list;
	}
	
	public void addRecipe(ItemStack input, ItemStack output)
	{
		recipes.add(new SawRecipe(input, output));
	}
	
	public static SawRecipe getRecipe(ItemStack input1){
		for(SawRecipe sr : SawRecipeManager.recipes){
			if (sr.getInput().getItem() == input1.getItem() && sr.getInput().getItemDamage() == input1.getItemDamage()){
				return sr;
			}
		}
		return null;
	}

	public static SawRecipeManager getInstance() {
		return instance;
	}
	
	public static List<SawRecipe> getRecipe()
	{
		return recipes;
	}
}