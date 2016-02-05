package de.nedelosk.forestmods.api.recipes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Maps;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeRegistry {

	private static final HashMap<String, ArrayList<IRecipe>> recipes = Maps.newHashMap();
	private static final HashMap<String, IRecipeHandler> handlers = Maps.newHashMap();

	public static boolean registerRecipe(IRecipe recipe) {
		if (recipes.get(recipe.getRecipeCategory()) == null) {
			recipes.put(recipe.getRecipeCategory(), new ArrayList<IRecipe>());
		}
		return recipes.get(recipe.getRecipeCategory()).add(recipe);
	}

	public static boolean registerRecipes(Collection<IRecipe> recipes) {
		if (RecipeRegistry.recipes.get(((Recipe) recipes.toArray()[0]).getRecipeCategory()) == null) {
			RecipeRegistry.recipes.put(((Recipe) recipes.toArray()[0]).getRecipeCategory(), new ArrayList<IRecipe>());
		}
		return RecipeRegistry.recipes.get(((Recipe) recipes.toArray()[0]).getRecipeCategory()).addAll(recipes);
	}

	public static boolean removeRecipe(IRecipe recipe) {
		if (recipes.get(recipe.getRecipeCategory()) == null) {
			return false;
		}
		if (!recipes.get(recipe.getRecipeCategory()).contains(recipe)) {
			return false;
		}
		return recipes.get(recipe.getRecipeCategory()).remove(recipe);
	}

	public static boolean removeRecipes(Collection<IRecipe> recipes) {
		if (recipes.isEmpty()) {
			return false;
		}
		if (RecipeRegistry.recipes.get(((Recipe) recipes.toArray()[0]).getRecipeCategory()) == null) {
			return false;
		}
		return RecipeRegistry.recipes.get(((Recipe) recipes.toArray()[0]).getRecipeCategory()).removeAll(recipes);
	}

	public static boolean recipeExists(IRecipe recipe) {
		List<IRecipe> recipes = RecipeRegistry.recipes.get(recipe.getRecipeCategory());
		if (recipes == null || recipes.isEmpty()) {
			return false;
		}
		for ( IRecipe r : recipes ) {
			int i = 0;
			for ( RecipeItem item : r.getInputs() ) {
				if (item != null && recipe.getInputs()[i] != null && recipe.getInputs()[i].equals(item)) {
					return true;
				}
				i++;
			}
		}
		return true;
	}

	public static List<IRecipe> removeRecipes(String recipeCategory, RecipeItem removeItem) {
		List<IRecipe> list = new ArrayList();
		for ( IRecipe recipe : recipes.get(recipeCategory) ) {
			for ( RecipeItem item : recipe.getOutputs() ) {
				if (item.isFluid() && removeItem.isFluid()) {
					if (item.fluid.isFluidEqual(removeItem.fluid)) {
						list.add(recipe);
					}
				} else if (item.isItem() && removeItem.isItem()) {
					if (item.item.getItem() == removeItem.item.getItem() && item.item.getItemDamage() == removeItem.item.getItemDamage()
							&& (item.item.stackTagCompound == null && removeItem.item.stackTagCompound == null
									|| ItemStack.areItemStackTagsEqual(item.item, removeItem.item))) {
						list.add(recipe);
					}
				} else if (item.isItem() && removeItem.isOre()) {
					int ore = OreDictionary.getOreID(removeItem.ore.oreDict);
					for ( int oreID : OreDictionary.getOreIDs(item.item) ) {
						if (ore == oreID) {
							list.add(recipe);
						}
					}
				}
			}
		}
		return list;
	}

	public static RecipeItem getRecipeInput(String recipeCategory, RecipeItem input) {
		ArrayList<IRecipe> recipes = getRecipes().get(recipeCategory);
		if (recipes == null || input == null) {
			return null;
		}
		for ( IRecipe recipe : recipes ) {
			ArrayList<RecipeItem> inputR = new ArrayList<RecipeItem>();
			for ( int i = 0; i < recipe.getInputs().length; i++ ) {
				RecipeItem item = recipe.getInputs().clone()[i];
				if (item.isItem()) {
					inputR.add(new RecipeItem(i, item.item));
				} else if (item.isFluid()) {
					inputR.add(new RecipeItem(i, item.fluid));
				} else {
					inputR.add(new RecipeItem(i, item.ore));
				}
			}
			if (inputR.isEmpty()) {
				return null;
			}
			for ( int i = 0; i < inputR.size(); i++ ) {
				RecipeItem in = inputR.get(i);
				if (in == null) {
					continue;
				}
				if (in.isOre() && input.isItem()) {
					if (!(in.ore.stackSize <= input.item.stackSize)) {
						continue;
					}
					int ore = OreDictionary.getOreID(in.ore.getOreDict());
					for ( int oreID : OreDictionary.getOreIDs(input.item) ) {
						if (ore == oreID) {
							return in;
						}
					}
				} else if (in.equals(input)) {
					return in;
				}
			}
		}
		return null;
	}

	public static IRecipe getRecipe(String recipeCategory, RecipeItem[] inputs, Object... craftingModifiers) {
		ArrayList<IRecipe> recipes = getRecipes().get(recipeCategory);
		if (recipes == null) {
			return null;
		}
		for ( IRecipe recipe : recipes ) {
			boolean isBreak = false;
			ArrayList<RecipeItem> inputR = new ArrayList<RecipeItem>();
			for ( RecipeItem item : recipe.getInputs().clone() ) {
				if (item.isItem()) {
					inputR.add(new RecipeItem(0, item.item));
				} else if (item.isFluid()) {
					inputR.add(new RecipeItem(0, item.fluid));
				} else {
					inputR.add(new RecipeItem(0, item.ore));
				}
			}
			input : for ( int i = 0; i < inputR.size(); i++ ) {
				RecipeItem in = inputR.get(i);
				if (inputs[i] != null) {
					if (in.isItem()) {
						if (inputs[i].item == null) {
							isBreak = true;
							break;
						} else if (in.item.getItem() == inputs[i].item.getItem() && in.item.stackSize <= inputs[i].item.stackSize
								&& in.item.getItemDamage() == inputs[i].item.getItemDamage()
								&& (in.item.stackTagCompound == null && inputs[i].item.stackTagCompound == null
										|| ItemStack.areItemStackTagsEqual(in.item, inputs[i].item))) {
							continue;
						} else {
							isBreak = true;
							break;
						}
					} else if (in.isFluid()) {
						if (inputs[i].fluid == null) {
							isBreak = true;
							break;
						} else if (in.fluid.isFluidEqual(inputs[i].fluid)) {
							continue;
						} else {
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
						for ( int oreID : OreDictionary.getOreIDs(inputStack) ) {
							if (ore == oreID) {
								continue input;
							}
						}
						isBreak = true;
						break;
					}
				}
			}
			if (!isBreak) {
				if (recipe.matches(craftingModifiers)) {
					return recipe;
				}
			}
		}
		return null;
	}

	public static HashMap<String, ArrayList<IRecipe>> getRecipes() {
		return recipes;
	}

	public static void registerRecipeHandler(String recipeCategory, IRecipeHandler handler) {
		if (handlers.get(recipeCategory) != null) {
			return;
		}
		handlers.put(recipeCategory, handler);
	}

	public static IRecipeHandler getRecipeHandler(String recipeCategory) {
		return handlers.get(recipeCategory);
	}
}
