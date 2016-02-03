package de.nedelosk.forestmods.common.plugins.minetweaker.handler;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.forestcore.utils.OreStack;
import de.nedelosk.forestmods.api.recipes.IRecipe;
import de.nedelosk.forestmods.api.recipes.Recipe;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.recipes.RecipeRegistry;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.nedelosk.mm.modular")
public class ModularRecipeHandler {

	@ZenMethod
	public static void add(IIngredient[] inputs, IIngredient[] outputs, int speedModifier, int energy, String recipeName, int... modifiers) {
		RecipeItem[] inputItems = new RecipeItem[inputs.length];
		for ( int i = 0; i < inputs.length; i++ ) {
			IIngredient input = inputs[i];
			if (input instanceof ILiquidStack) {
				inputItems[i] = new RecipeItem(MineTweakerMC.getLiquidStack((ILiquidStack) input));
			} else if (input instanceof IItemStack) {
				inputItems[i] = new RecipeItem(MineTweakerMC.getItemStack(input));
			} else if (input instanceof IOreDictEntry) {
				inputItems[i] = new RecipeItem(new OreStack(((IOreDictEntry) input).getName()));
			}
		}
		RecipeItem[] outputItems = new RecipeItem[outputs.length];
		for ( int i = 0; i < outputs.length; i++ ) {
			IIngredient output = outputs[i];
			if (output instanceof ILiquidStack) {
				outputItems[i] = new RecipeItem(MineTweakerMC.getLiquidStack((ILiquidStack) output));
			} else if (output instanceof IItemStack) {
				outputItems[i] = new RecipeItem(MineTweakerMC.getItemStack(output));
			}
		}
		MineTweakerAPI.apply(new AddAction(inputItems, outputItems, speedModifier, energy, recipeName, modifiers));
	}

	@ZenMethod
	public static void remove(IIngredient result, String recipeName) {
		if (result instanceof IItemStack) {
			ItemStack resultToRemove = MineTweakerMC.getItemStack(result);
			MineTweakerAPI.apply(new RemoveAction(new RecipeItem(resultToRemove), recipeName));
		} else if (result instanceof ILiquidStack) {
			FluidStack resultToRemove = MineTweakerMC.getLiquidStack((ILiquidStack) result);
			MineTweakerAPI.apply(new RemoveAction(new RecipeItem(resultToRemove), recipeName));
		} else if (result instanceof IOreDictEntry) {
			MineTweakerAPI.apply(new RemoveAction(new RecipeItem(new OreStack(((IOreDictEntry) result).getName())), recipeName));
		}
	}

	private static class AddAction implements IUndoableAction {

		private final IRecipe recipe;

		public AddAction(RecipeItem[] input, RecipeItem[] output, int speedModifier, int energy, String recipeName, int[] modifiers) {
			recipe = new Recipe(input, output, speedModifier, energy, recipeName, modifiers);
		}

		@Override
		public void apply() {
			RecipeRegistry.registerRecipe(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			RecipeRegistry.removeRecipe(recipe);
		}

		@Override
		public String describe() {
			return "Adding " + recipe.getRecipeName() + " Recipe";
		}

		@Override
		public String describeUndo() {
			return "Removing" + recipe.getRecipeName() + " Recipe";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

	private static class RemoveAction implements IUndoableAction {

		private final RecipeItem result;
		private final String recipeName;
		private List<IRecipe> removedRecipes = new ArrayList<IRecipe>();

		public RemoveAction(RecipeItem result, String recipeName) {
			this.result = result;
			this.recipeName = recipeName;
		}

		@Override
		public void apply() {
			removedRecipes = RecipeRegistry.removeRecipes(recipeName, result);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			RecipeRegistry.registerRecipes(removedRecipes);
		}

		@Override
		public String describe() {
			if (result.isItem()) {
				return "Removing all " + recipeName + " Recipe where '" + result.item.getDisplayName() + "' is the result.";
			} else if (result.isFluid()) {
				return "Removing all " + recipeName + " Recipe where '" + result.fluid.getLocalizedName() + "' is the result.";
			} else {
				return "Removing all " + recipeName + " Recipe where '" + result.ore.getOreDict() + "' is the result.";
			}
		}

		@Override
		public String describeUndo() {
			if (result.isItem()) {
				return "Adding back in all " + recipeName + "Recipe where '" + result.item.getDisplayName() + "' is the result.";
			} else if (result.isFluid()) {
				return "Adding back in all " + recipeName + "Recipe where '" + result.fluid.getLocalizedName() + "' is the result.";
			} else {
				return "Adding back in all " + recipeName + "Recipe where '" + result.ore.getOreDict() + "' is the result.";
			}
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
