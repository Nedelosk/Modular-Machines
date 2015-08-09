package nedelosk.forestday.common.machines.base.wood.campfire;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestday.api.crafting.ICampfireRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CampfireRecipeManager implements ICampfireRecipe{

	private static List<CampfireRecipe> recipes = new ArrayList();
	
	@Override
	public void addRecipe(ItemStack input, ItemStack input2, ItemStack output, int potTier, int burnTime)
	{
		add(new CampfireRecipe(input, input2, output, potTier, burnTime));
	}
	
	@Override
	public void addRecipe(ItemStack input, ItemStack output, int potTier, int burnTime)
	{
		add(new CampfireRecipe(input, output, potTier, burnTime));
	}
	
	public void add(CampfireRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public static CampfireRecipe getRecipe(ItemStack input, ItemStack input2, int potTier){
		for(CampfireRecipe sr : recipes){
			boolean a = sr.input.getItem() == input.getItem();
			Item i = sr.input.getItem();
			Item o = input.getItem();
			if(sr.input.getItem() == input.getItem() && sr.input.getItemDamage() == input.getItemDamage() && ItemStack.areItemStackTagsEqual(input, sr.input) && potTier >= sr.potTier && (sr.input2== null || input2 != null  && sr.input2.getItem() == input2.getItem() && sr.input2.getItemDamage() == input2.getItemDamage() && ItemStack.areItemStackTagsEqual(input2, sr.input2))){
				return sr;
			}
		}
		return null;
	}
	
	public static void removeRecipe(CampfireRecipe recipe)
	{
		recipes.remove(recipe);
	}
	
	public static void addAllRecipes(List<CampfireRecipe> recipe)
	{
		recipes.addAll(recipe);
	}
	
	public static List<CampfireRecipe> removeRecipes(ItemStack stack)
	{
		List<CampfireRecipe> list = new ArrayList();
		for(CampfireRecipe recipe : recipes)
		{
			if(recipe.output.getItem() == stack.getItem() && recipe.output.getItemDamage() == stack.getItemDamage() && ItemStack.areItemStackTagsEqual(stack, recipe.output))
			{
				list.add(recipe);
			}
		}
		return list;
	}
	
}