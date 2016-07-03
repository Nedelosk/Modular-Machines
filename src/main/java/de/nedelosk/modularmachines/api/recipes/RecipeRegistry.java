package de.nedelosk.modularmachines.api.recipes;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.Maps;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeRegistry {

	private static final HashMap<String, ArrayList<IRecipe>> recipes = Maps.newHashMap();
	private static final HashMap<String, IRecipeHandler> handlers = Maps.newHashMap();

	/**
	 * @return True if the recipe is not already registered
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
	 * @return True if the stack is a input, of a recipe, at the matching position.
	 */
	public static boolean isRecipeInput(String recipeCategory, RecipeItem inputToTest) {
		ArrayList<IRecipe> recipes = getRecipes().get(recipeCategory);
		if (recipes == null || inputToTest == null) {
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
				RecipeItem recipeInput = recipeInputs.get(i);
				if (recipeInput == null) {
					continue;
				}
				if (recipeInput.index != inputToTest.index) {
					continue;
				}
				if(inputEqualsItem(recipeInput, inputToTest, false)){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param recipeCategory The category of the recipe.
	 * @param inputs The inputs
	 * @param craftingModifiers
	 * @return The matching recipe to the inputs.
	 */
	public static IRecipe getRecipe(String recipeCategory, RecipeItem[] inputs, Object... craftingModifiers) {
		ArrayList<IRecipe> recipes = getRecipes().get(recipeCategory);
		if (recipes == null) {
			return null;
		}
		testRecipes: for(IRecipe recipe : recipes) {
			ArrayList<RecipeItem> recipeInputs = new ArrayList<RecipeItem>();
			for(RecipeItem recipeInput : recipe.getInputs().clone()) {
				recipeInputs.add(recipeInput);
			}
			for(int i = 0; i < recipeInputs.size(); i++) {
				RecipeItem recipeInput = recipeInputs.get(i);
				RecipeItem machineInput = inputs[i];
				if(recipeInput == null || machineInput == null){
					continue testRecipes;
				}
				if (machineInput.isNull()) {
					if (!recipeInput.isNull()) {
						continue testRecipes;
					}
					continue;
				}else if (recipeInput.isNull()) {
					if (!machineInput.isNull()) {
						continue testRecipes;
					}
					continue;
				}else if(inputEqualsItem(recipeInput, machineInput, false)){
					continue;
				}
				continue testRecipes;
			}
			IRecipeHandler handler = getRecipeHandler(recipeCategory);
			if(handler != null){
				if (handler.matches(recipe, craftingModifiers)) {
					return recipe;
				}
			}else{
				return recipe;
			}
		}
		return null;
	}

	/**
	 * @param testSize Test the stackSize or the amount of the two RecipeItem's.
	 * @return True if the input equals the item
	 */
	public static boolean inputEqualsItem(RecipeItem item, RecipeItem input, boolean testSize){
		if (input != null) {
			if (item.isItem()) {
				if (!input.isItem()) {
					return false;
				} else if (item.item.getItem() == input.item.getItem()
						&& item.item.getItemDamage() == input.item.getItemDamage()
						&& (testSize && item.item.stackSize == input.item.stackSize || !testSize && item.item.stackSize <= input.item.stackSize)
						&& (!item.item.hasTagCompound() && !input.item.hasTagCompound()
								|| ItemStack.areItemStackTagsEqual(item.item, input.item))) {
					return true;
				}
				return false;
			} else if (item.isFluid()) {
				if (!input.isFluid()) {
					return false;
				} else if (item.fluid.isFluidEqual(input.fluid)) {
					if(testSize && item.fluid.amount == input.fluid.amount || !testSize && item.fluid.amount <= input.fluid.amount){
						return true;
					}
				}
				return false;
			} else if (item.isOre()) {
				if (input.isOre()) {
					if (item.ore.equals(input.ore)) {
						if(testSize && item.ore.stackSize == input.ore.stackSize || !testSize && item.ore.stackSize <= input.ore.stackSize){
							return true;
						}
					}
					return false;
				}
				if (!input.isItem()) {
					return false;
				}
				if(testSize && !(item.ore.stackSize <= input.item.stackSize)){
					return false;
				}
				int ore = OreDictionary.getOreID(item.ore.oreDict);
				ItemStack oreStack = input.item.copy();
				for(int oreID : OreDictionary.getOreIDs(oreStack)) {
					if (ore == oreID) {
						return true;
					}
				}
				return false;
			}
		}
		return false;
	}

	/**
	 * Register a recipe handler for a recipeCategory.
	 */
	public static void registerRecipeHandler(IRecipeHandler handler) {
		String recipeCategory = handler.getRecipeCategory();
		if (handlers.containsKey(recipeCategory)) {
			return;
		}
		handlers.put(recipeCategory, handler);
	}

	/**
	 * @return A recipe handler from the matching recipeCategory.
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
	 * @return A map with all recipe handlers that are in the registry.
	 */
	public static HashMap<String, IRecipeHandler> getHandlers() {
		return handlers;
	}
}
