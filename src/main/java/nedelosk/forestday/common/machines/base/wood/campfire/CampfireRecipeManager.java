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
			boolean a = sr.getInput().getItem() == input.getItem();
			Item i = sr.getInput().getItem();
			Item o = input.getItem();
			if(sr.getInput().getItem() == input.getItem() && sr.getInput().getItemDamage() == input.getItemDamage() && ItemStack.areItemStackTagsEqual(input, sr.getInput()) && potTier >= sr.getPotTier() && (sr.getInput2() == null || input2 != null  && sr.getInput2().getItem() == input2.getItem() && sr.getInput2().getItemDamage() == input2.getItemDamage() && ItemStack.areItemStackTagsEqual(input2, sr.getInput2()))){
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
			if(recipe.getOutput().getItem() == stack.getItem() && recipe.getOutput().getItemDamage() == stack.getItemDamage() && ItemStack.areItemStackTagsEqual(stack, recipe.getOutput()))
			{
				list.add(recipe);
			}
		}
		return list;
	}
	
	public static List<CampfireRecipe> getRecipes() {
		return recipes;
	}
	
}