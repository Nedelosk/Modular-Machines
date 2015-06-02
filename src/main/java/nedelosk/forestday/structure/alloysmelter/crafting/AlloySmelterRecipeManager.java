package nedelosk.forestday.structure.alloysmelter.crafting;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestday.api.crafting.IAlloySmelterRecipe;
import nedelosk.forestday.common.machines.wood.workbench.WorkbenchRecipe;
import net.minecraft.item.ItemStack;

public class AlloySmelterRecipeManager implements IAlloySmelterRecipe {

	private static ArrayList<AlloySmelterRecipe> recipes = new ArrayList();
	
	public static AlloySmelterRecipeManager instance;
	
	public static void addRecipe(AlloySmelterRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public static void removeRecipe(AlloySmelterRecipe recipe)
	{
		recipes.remove(recipe);
	}
	
	public static void addAllRecipes(List<AlloySmelterRecipe> recipe)
	{
		recipes.addAll(recipe);
	}
	
	public static List<AlloySmelterRecipe> removeRecipes(ItemStack stack)
	{
		List<AlloySmelterRecipe> list = new ArrayList();
		for(AlloySmelterRecipe recipe : recipes)
		{
			if(recipe.getOutput1().getItem() == stack.getItem() && recipe.getOutput1().getItemDamage() == stack.getItemDamage())
			{
				list.add(recipe);
			}
		}
		return list;
	}
	
	public void addRecipe(ItemStack input1, ItemStack input2, ItemStack output1, int minHeat, int maxHeat)
	{
		recipes.add(new AlloySmelterRecipe(input1, input2, output1, minHeat, maxHeat));
	}
	
	public void addRecipe(String input1, ItemStack input2, ItemStack output1, int minHeat, int maxHeat)
	{
		recipes.add(new AlloySmelterRecipe(input1, input2, output1, minHeat, maxHeat));
	}
	
	public void addRecipe(ItemStack input1, String input2, ItemStack output1, int minHeat, int maxHeat)
	{
		recipes.add(new AlloySmelterRecipe(input1, input2, output1, minHeat, maxHeat));
	}
	
	public void addRecipe(String input1, String input2, ItemStack output1, int minHeat, int maxHeat)
	{
		recipes.add(new AlloySmelterRecipe(input1, input2, output1, minHeat, maxHeat));
	}
	
	public static AlloySmelterRecipe getRecipe(ItemStack input1, ItemStack input2, int heat){
		for(AlloySmelterRecipe sr : AlloySmelterRecipeManager.recipes){
			if (sr.getInput1().getItem() == input1.getItem() && sr.getInput2().getItem() == input2.getItem() && heat < sr.getMaxHeat() && heat > sr.getMinHeat() && input1.getItemDamage() == sr.getInput1().getItemDamage() && input2.getItemDamage() == sr.getInput2().getItemDamage()){
				return sr;
			}
		}
		return null;
	}

	public static AlloySmelterRecipeManager getInstance() {
		return instance;
	}
	
	public static List<AlloySmelterRecipe> getRecipe()
	{
		return recipes;
	}
	
}