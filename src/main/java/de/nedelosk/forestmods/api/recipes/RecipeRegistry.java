package de.nedelosk.forestmods.api.recipes;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.Maps;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeRegistry {

	private static final HashMap<String, ArrayList<IRecipe>> recipes = Maps.newHashMap();
	private static final HashMap<String, IRecipeHandler> handlers = Maps.newHashMap();

	/**
	 * Register a recipe
	 */
	public static boolean registerRecipe(IRecipe recipe) {
		if (recipes.get(recipe.getRecipeCategory()) == null) {
			recipes.put(recipe.getRecipeCategory(), new ArrayList<IRecipe>());
		}
		for(int index = 0; index < recipe.getInputs().length; index++) {
			recipe.getInputs()[index].index = index;
		}
		for(int index = 0; index < recipe.getOutputs().length; index++) {
			recipe.getOutputs()[index].index = index;
		}
		return recipes.get(recipe.getRecipeCategory()).add(recipe);
	}

	/**
	 * Remove a recipe from the registry
	 */
	public static boolean removeRecipe(IRecipe recipe) {
		if (recipes.get(recipe.getRecipeCategory()) == null) {
			return false;
		}
		if (!recipes.get(recipe.getRecipeCategory()).contains(recipe)) {
			return false;
		}
		return recipes.get(recipe.getRecipeCategory()).remove(recipe);
	}

	/**
	 * @return True when the stack is in a recipe
	 */
	public static boolean isRecipeInput(String recipeCategory, RecipeItem stack) {
		ArrayList<IRecipe> recipes = getRecipes().get(recipeCategory);
		if (recipes == null || stack == null) {
			return false;
		}
		for(IRecipe recipe : recipes) {
			ArrayList<RecipeItem> recipeInputs = new ArrayList<RecipeItem>();
			for(RecipeItem recipeInput : recipe.getInputs().clone()) {
				recipeInputs.add(recipeInput);
			}
			if (recipeInputs.isEmpty()) {
				return false;
			}
			for(int i = 0; i < recipeInputs.size(); i++) {
				RecipeItem in = recipeInputs.get(i);
				if (in == null) {
					continue;
				}
				if (in.isOre() && stack.isItem()) {
					if (in.index != stack.index) {
						continue;
					}
					int ore = OreDictionary.getOreID(in.ore.getOreDict());
					for(int oreID : OreDictionary.getOreIDs(stack.item)) {
						if (ore == oreID) {
							return true;
						}
					}
				} else if (in.equals(stack)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param recipeCategory
	 *            The category of the recipe from the machine
	 * @param machinesInputs
	 *            The inputs from the machine
	 * @param craftingModifiers
	 * @return The matching recipe to the inputs from the machine
	 */
	public static IRecipe getRecipe(String recipeCategory, RecipeItem[] machinesInputs, Object... craftingModifiers) {
		ArrayList<IRecipe> recipes = getRecipes().get(recipeCategory);
		if (recipes == null) {
			return null;
		}
		testRecipes: for(IRecipe recipe : recipes) {
			ArrayList<RecipeItem> recipeInputs = new ArrayList<RecipeItem>();
			for(RecipeItem recipeInput : recipe.getInputs().clone()) {
				recipeInputs.add(recipeInput);
			}
			testInput: for(int i = 0; i < recipeInputs.size(); i++) {
				RecipeItem recipeInput = recipeInputs.get(i);
				RecipeItem machineInput = machinesInputs[i];
				if (machineInput != null) {
					if (recipeInput.isItem()) {
						if (machineInput.item == null) {
							continue testRecipes;
						} else if (recipeInput.item.getItem() == machineInput.item.getItem()
								&& recipeInput.item.getItemDamage() == machineInput.item.getItemDamage()
								&& recipeInput.item.stackSize <= machineInput.item.stackSize
								&& (recipeInput.item.stackTagCompound == null && machineInput.item.stackTagCompound == null
										|| ItemStack.areItemStackTagsEqual(recipeInput.item, machineInput.item))) {
							continue;
						} else {
							continue testRecipes;
						}
					} else if (recipeInput.isFluid()) {
						if (machineInput.fluid == null) {
							continue testRecipes;
						} else if (recipeInput.fluid.isFluidEqual(machineInput.fluid)) {
							continue;
						} else {
							continue testRecipes;
						}
					} else if (recipeInput.isOre()) {
						if (machineInput.isOre()) {
							if (recipeInput.ore.equals(machineInput.ore) && recipeInput.ore.stackSize <= machineInput.ore.stackSize) {
								continue testInput;
							}
							continue testRecipes;
						}
						if (!machineInput.isItem() || !(recipeInput.ore.stackSize <= machineInput.item.stackSize)) {
							continue testRecipes;
						}
						int ore = OreDictionary.getOreID(recipeInput.ore.oreDict);
						ItemStack oreStack = machinesInputs[i].item;
						for(int oreID : OreDictionary.getOreIDs(oreStack)) {
							if (ore == oreID) {
								continue testInput;
							}
						}
						continue testRecipes;
					} else if (recipeInput.isNull()) {
						if (!machineInput.isNull()) {
							continue testRecipes;
						}
					}
				}
			}
			if (recipe.matches(craftingModifiers)) {
				return recipe;
			}
		}
		return null;
	}

	/**
	 * Register a recipe handler
	 */
	public static void registerRecipeHandler(String recipeCategory, IRecipeHandler handler) {
		if (handlers.get(recipeCategory) != null) {
			return;
		}
		handlers.put(recipeCategory, handler);
	}

	/**
	 * @return A recipe handler from the matching recipeCategory
	 */
	public static IRecipeHandler getRecipeHandler(String recipeCategory) {
		return handlers.get(recipeCategory);
	}

	/**
	 * @return A map with all recipe recipes that are in the registry
	 */
	public static HashMap<String, ArrayList<IRecipe>> getRecipes() {
		return recipes;
	}

	/**
	 * @return A map with all recipe handlers that are in the registry
	 */
	public static HashMap<String, IRecipeHandler> getHandlers() {
		return handlers;
	}
}
