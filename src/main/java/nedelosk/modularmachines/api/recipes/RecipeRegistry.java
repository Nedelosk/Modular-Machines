package nedelosk.modularmachines.api.recipes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Maps;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeRegistry {

	private static final HashMap<String, ArrayList<IRecipe>> recipes = Maps.newHashMap();

	public static boolean registerRecipe(IRecipe recipe) {
		if (recipes.get(recipe.getRecipeName()) == null)
			recipes.put(recipe.getRecipeName(), new ArrayList<IRecipe>());
		return recipes.get(recipe.getRecipeName()).add(recipe);
	}

	public static boolean registerRecipes(Collection<IRecipe> recipes) {
		if (RecipeRegistry.recipes.get(((Recipe) recipes.toArray()[0]).getRecipeName()) == null)
			RecipeRegistry.recipes.put(((Recipe) recipes.toArray()[0]).getRecipeName(), new ArrayList<IRecipe>());
		return RecipeRegistry.recipes.get(((Recipe) recipes.toArray()[0]).getRecipeName()).addAll(recipes);
	}

	public static boolean removeRecipe(IRecipe recipe) {
		if (recipes.get(recipe.getRecipeName()) == null)
			return false;
		if (!recipes.get(recipe.getRecipeName()).contains(recipe))
			return false;
		return recipes.get(recipe.getRecipeName()).remove(recipe);
	}

	public static boolean removeRecipes(Collection<IRecipe> recipes) {
		if (recipes.isEmpty())
			return false;
		if (RecipeRegistry.recipes.get(((Recipe) recipes.toArray()[0]).getRecipeName()) == null)
			return false;
		return RecipeRegistry.recipes.get(((Recipe) recipes.toArray()[0]).getRecipeName()).removeAll(recipes);
	}

	public static List<IRecipe> removeRecipes(String recipeName, RecipeItem removeItem) {
		List<IRecipe> list = new ArrayList();
		for (IRecipe recipe : recipes.get(recipeName)) {
			for (RecipeItem item : recipe.getOutputs()) {
				if (item.isFluid() && removeItem.isFluid()) {
					if (item.fluid.isFluidEqual(removeItem.fluid))
						list.add(recipe);
				} else if (item.isItem() && removeItem.isItem()) {
					if (item.item.getItem() == removeItem.item.getItem()
							&& item.item.getItemDamage() == removeItem.item.getItemDamage()
							&& (item.item.stackTagCompound == null && removeItem.item.stackTagCompound == null
									|| ItemStack.areItemStackTagsEqual(item.item, removeItem.item)))
						list.add(recipe);
				} else if (item.isItem() && removeItem.isOre()) {
					int ore = OreDictionary.getOreID(removeItem.ore.oreDict);
					for (int oreID : OreDictionary.getOreIDs(item.item)) {
						if (ore == oreID) {
							list.add(recipe);
						}
					}
				}
			}
		}
		return list;
	}
	
	public static RecipeInput isRecipeInput(String recipeName, RecipeInput input) {
		ArrayList<IRecipe> recipes = getRecipes().get(recipeName);
		if (recipes == null || input == null)
			return null;
		for (IRecipe recipe : recipes) {
			ArrayList<RecipeInput> inputR = new ArrayList<RecipeInput>();
			for (int i = 0;i < recipe.getInputs().length;i++) {
				RecipeItem item = recipe.getInputs().clone()[i];
				if (item.isItem())
					inputR.add(new RecipeInput(i, item.item));
				else if (item.isFluid())
					inputR.add(new RecipeInput(i, item.fluid));
				else
					inputR.add(new RecipeInput(i, item.ore));
			}
			if(inputR.isEmpty()){
				return null;
			}
			for(int i = 0;i < inputR.size();i++){
				RecipeInput in = inputR.get(i);
				if(in == null)
					continue;
				if(in.isOre() && input.isItem()){
					int ore = OreDictionary.getOreID(in.ore.getOreDict());
					for(int oreID : OreDictionary.getOreIDs(input.item)){
						if (ore == oreID) {
							return in;
						}
					}
				}else if(in.equals(input)){
					return in;
				}
			}
		}
		return null;
	}

	public static IRecipe getRecipe(String recipeName, RecipeInput[] inputs, Object... craftingModifiers) {
		ArrayList<IRecipe> recipes = getRecipes().get(recipeName);
		if (recipes == null)
			return null;
		for (IRecipe recipe : recipes) {
			boolean isBreak = false;
			ArrayList<RecipeInput> inputR = new ArrayList<RecipeInput>();
			for (RecipeItem item : recipe.getInputs().clone()) {
				if (item.isItem())
					inputR.add(new RecipeInput(0, item.item));
				else if (item.isFluid())
					inputR.add(new RecipeInput(0, item.fluid));
				else
					inputR.add(new RecipeInput(0, item.ore));
			}
			input: for (int i = 0; i < inputR.size(); i++) {
				RecipeInput in = inputR.get(i);
				if (inputs[i] != null)
					if (in.isItem()) {
						if (inputs[i].item == null) {
							isBreak = true;
							break;
						} else if (in.item.getItem() == inputs[i].item.getItem()
								&& in.item.stackSize <= inputs[i].item.stackSize
								&& in.item.getItemDamage() == inputs[i].item.getItemDamage()
								&& (in.item.stackTagCompound == null && inputs[i].item.stackTagCompound == null
										|| ItemStack.areItemStackTagsEqual(in.item, inputs[i].item)))
							continue;
						else {
							isBreak = true;
							break;
						}
					} else if (in.isFluid()) {
						if (inputs[i].fluid == null) {
							isBreak = true;
							break;
						} else if (in.fluid.isFluidEqual(inputs[i].fluid))
							continue;
						else {
							isBreak = true;
							break;
						}
					} else if (in.isOre()) {
						if (inputs[i].item == null) {
							isBreak = true;
							break;
						}
						int ore = OreDictionary.getOreID(in.ore.oreDict);
						ItemStack inputStack = inputs[i].item;
						for (int oreID : OreDictionary.getOreIDs(inputStack)) {
							if (ore == oreID) {
								continue input;
							}
						}
						isBreak = true;
						break;
					}

			}
			if (!isBreak)
				if(recipe.matches(craftingModifiers))
					return recipe;
		}
		return null;
	}

	public static HashMap<String, ArrayList<IRecipe>> getRecipes() {
		return recipes;
	}

}
