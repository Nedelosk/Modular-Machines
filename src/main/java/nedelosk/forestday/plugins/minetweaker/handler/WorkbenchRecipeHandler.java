package nedelosk.forestday.plugins.minetweaker.handler;

import java.util.ArrayList;
import java.util.List;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import nedelosk.forestday.common.crafting.WorkbenchRecipeManager;
import nedelosk.forestday.common.crafting.WorkbenchRecipeManager.WorkbenchRecipe;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.fd.Workbench")
public class WorkbenchRecipeHandler {

	@ZenMethod
	public static void add(IIngredient output, IIngredient input, IIngredient inputTool, IIngredient inputPattern, int burnTime) {
		ItemStack resultToAdd = MineTweakerMC.getItemStack(output);
		ItemStack input1ToAdd = MineTweakerMC.getItemStack(input);
		ItemStack input2ToAdd = MineTweakerMC.getItemStack(inputTool);
		ItemStack input3ToAdd = MineTweakerMC.getItemStack(inputPattern);
		MineTweakerAPI.apply(new AddAction(input1ToAdd, input2ToAdd, input3ToAdd, resultToAdd, burnTime));
	}

	@ZenMethod
	public static void add(IIngredient output, IIngredient input, IIngredient inputTool, int burnTime) {
		ItemStack resultToAdd = MineTweakerMC.getItemStack(output);
		ItemStack input1ToAdd = MineTweakerMC.getItemStack(input);
		ItemStack input2ToAdd = MineTweakerMC.getItemStack(inputTool);
		MineTweakerAPI.apply(new AddAction(input1ToAdd, input2ToAdd, resultToAdd, burnTime));
	}

	@ZenMethod
	public static void remove(IItemStack result) {
		ItemStack resultToRemove = MineTweakerMC.getItemStack(result);
		MineTweakerAPI.apply(new RemoveAction(resultToRemove));
	}

	private static class AddAction implements IUndoableAction {

		private final WorkbenchRecipe recipe;

		public AddAction(ItemStack parent1ToAdd, ItemStack parent2ToAdd, ItemStack parent3ToAdd, ItemStack output, int burnTime) {
			recipe = new WorkbenchRecipe(parent1ToAdd, parent2ToAdd, parent3ToAdd, output, burnTime);
		}

		public AddAction(ItemStack parent1ToAdd, ItemStack parent2ToAdd, ItemStack output, int burnTime) {
			recipe = new WorkbenchRecipe(parent1ToAdd, parent2ToAdd, output, burnTime);
		}

		@Override
		public void apply() {
			WorkbenchRecipeManager.addRecipe(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			WorkbenchRecipeManager.removeRecipe(recipe);
		}

		@Override
		public String describe() {
			return "Adding Workbench Recipe";
		}

		@Override
		public String describeUndo() {
			return "Removing Recipe";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

	private static class RemoveAction implements IUndoableAction {

		private final ItemStack result;
		private List<WorkbenchRecipe> removedRecipes = new ArrayList<WorkbenchRecipe>();

		public RemoveAction(ItemStack result) {
			this.result = result;
		}

		@Override
		public void apply() {
			removedRecipes = WorkbenchRecipeManager.removeRecipes(result);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			WorkbenchRecipeManager.addAllRecipes(removedRecipes);
		}

		@Override
		public String describe() {
			return "Removing all Workbanch Recipe where '" + result.getDisplayName() + "' is the result.";
		}

		@Override
		public String describeUndo() {
			return "Adding back in all Workbanch Recipe where '" + result.getDisplayName() + "' is the result.";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
