package nedelosk.modularmachines.plugins.mt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.recipes.ICraftingRecipe;
import minetweaker.api.recipes.IRecipeFunction;
import minetweaker.api.recipes.ShapedRecipe;
import minetweaker.mc1710.recipes.ShapedRecipeOre;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.nedelosk.mm.shapde.modular")
public class ShapedModuleRecipeHandler {

	private final List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();

	@ZenMethod
	public void addShaped(IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function) {
		addShaped(output, ingredients, function, false);
	}

	@ZenMethod
	public void addShapedMirrored(IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function) {
		addShaped(output, ingredients, function, true);
	}

	private class ActionRemoveRecipes implements IUndoableAction {

		private final List<Integer> removingIndices;
		private final List<IRecipe> removingRecipes;

		public ActionRemoveRecipes(List<IRecipe> recipes, List<Integer> indices) {
			this.removingIndices = indices;
			this.removingRecipes = recipes;
		}

		@Override
		public void apply() {
			for ( int i = removingIndices.size() - 1; i >= 0; i-- ) {
				recipes.remove((int) removingIndices.get(i));
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			for ( int i = 0; i < removingIndices.size(); i++ ) {
				int index = Math.min(recipes.size(), removingIndices.get(i));
				recipes.add(index, removingRecipes.get(i));
			}
		}

		@Override
		public String describe() {
			return "Removing " + removingIndices.size() + " recipes";
		}

		@Override
		public String describeUndo() {
			return "Restoring " + removingIndices.size() + " recipes";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

	private class ActionAddRecipe implements IUndoableAction {

		private final IRecipe recipe;
		private final ICraftingRecipe craftingRecipe;

		public ActionAddRecipe(IRecipe recipe, ICraftingRecipe craftingRecipe) {
			this.recipe = recipe;
			this.craftingRecipe = craftingRecipe;
		}

		@Override
		public void apply() {
			recipes.add(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			recipes.remove(recipe);
		}

		@Override
		public String describe() {
			return "Adding recipe for " + recipe.getRecipeOutput().getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing recipe for " + recipe.getRecipeOutput().getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

	private void addShaped(IItemStack output, IIngredient[][] ingredients, IRecipeFunction function, boolean mirrored) {
		ShapedRecipe recipe = new ShapedRecipe(output, ingredients, function, mirrored);
		IRecipe irecipe = convert(recipe);
		MineTweakerAPI.apply(new ActionAddRecipe(irecipe, recipe));
	}

	public static IRecipe convert(ShapedRecipe recipe) {
		IIngredient[] ingredients = recipe.getIngredients();
		byte[] posx = recipe.getIngredientsX();
		byte[] posy = recipe.getIngredientsY();
		Object[] converted = new Object[recipe.getHeight() * recipe.getWidth()];
		for ( int i = 0; i < ingredients.length; i++ ) {
			converted[posx[i] + posy[i] * recipe.getWidth()] = ingredients[i].getInternal();
		}
		int counter = 0;
		String[] parts = new String[recipe.getHeight()];
		ArrayList rarguments = new ArrayList();
		for ( int i = 0; i < recipe.getHeight(); i++ ) {
			char[] pattern = new char[recipe.getWidth()];
			for ( int j = 0; j < recipe.getWidth(); j++ ) {
				int off = i * recipe.getWidth() + j;
				if (converted[off] == null) {
					pattern[j] = ' ';
				} else {
					pattern[j] = (char) ('A' + counter);
					rarguments.add(pattern[j]);
					rarguments.add(converted[off]);
					counter++;
				}
			}
			parts[i] = new String(pattern);
		}
		rarguments.addAll(0, Arrays.asList(parts));
		return new ShapedRecipeOre(rarguments.toArray(), recipe);
	}
}
