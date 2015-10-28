package nedelosk.forestday.common.crafting;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestday.api.crafting.IWorkbenchRecipe;
import nedelosk.forestday.api.crafting.OreStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class WorkbenchRecipeManager implements IWorkbenchRecipe {

	private static ArrayList<WorkbenchRecipe> recipes = new ArrayList();
	private static ArrayList<ItemStack> outputs = new ArrayList();

	public static WorkbenchRecipeManager instance;

	public static void addRecipe(WorkbenchRecipe recipe) {
		recipes.add(recipe);
	}

	public static void removeRecipe(WorkbenchRecipe recipe) {
		recipes.remove(recipe);
	}

	public static void addAllRecipes(List<WorkbenchRecipe> recipe) {
		recipes.addAll(recipe);
	}

	public static List<WorkbenchRecipe> removeRecipes(ItemStack stack) {
		List<WorkbenchRecipe> list = new ArrayList();
		for (WorkbenchRecipe recipe : recipes) {
			if (recipe.getOutput().getItem() == stack.getItem()
					&& recipe.getOutput().getItemDamage() == stack.getItemDamage()) {
				list.add(recipe);
			}
		}
		return list;
	}

	@Override
	public void addRecipe(ItemStack input, ItemStack inputTool, ItemStack output, int burnTime) {
		recipes.add(new WorkbenchRecipe(input, inputTool, output, burnTime));
	}

	@Override
	public void addRecipe(OreStack input, ItemStack inputTool, ItemStack output, int burnTime) {
		recipes.add(new WorkbenchRecipe(input, inputTool, output, burnTime));
	}

	@Override
	public void addRecipe(ItemStack input, OreStack inputTool, ItemStack output, int burnTime) {
		recipes.add(new WorkbenchRecipe(input, inputTool, output, burnTime));
	}

	@Override
	public void addRecipe(OreStack input, OreStack inputTool, ItemStack output, int burnTime) {
		recipes.add(new WorkbenchRecipe(input, inputTool, output, burnTime));
	}

	@Override
	public void addRecipe(ItemStack input, ItemStack inputTool, ItemStack inputPattern, ItemStack output,
			int burnTime) {
		recipes.add(new WorkbenchRecipe(input, inputTool, inputPattern, output, burnTime));
	}

	@Override
	public void addRecipe(OreStack input, ItemStack inputTool, ItemStack inputPattern, ItemStack output, int burnTime) {
		recipes.add(new WorkbenchRecipe(input, inputTool, inputPattern, output, burnTime));
	}

	@Override
	public void addRecipe(ItemStack input, OreStack inputTool, ItemStack inputPattern, ItemStack output, int burnTime) {
		recipes.add(new WorkbenchRecipe(input, inputTool, inputPattern, output, burnTime));
	}

	@Override
	public void addRecipe(ItemStack input, ItemStack inputTool, OreStack inputPattern, ItemStack output, int burnTime) {
		recipes.add(new WorkbenchRecipe(input, inputTool, inputPattern, output, burnTime));
	}

	@Override
	public void addRecipe(OreStack input, OreStack inputTool, ItemStack inputPattern, ItemStack output, int burnTime) {
		recipes.add(new WorkbenchRecipe(input, inputTool, inputPattern, output, burnTime));
	}

	@Override
	public void addRecipe(ItemStack input, OreStack inputTool, OreStack inputPattern, ItemStack output, int burnTime) {
		recipes.add(new WorkbenchRecipe(input, inputTool, inputPattern, output, burnTime));
	}

	@Override
	public void addRecipe(OreStack input, ItemStack inputTool, OreStack inputPattern, ItemStack output, int burnTime) {
		recipes.add(new WorkbenchRecipe(input, inputTool, inputPattern, output, burnTime));
	}

	@Override
	public void addRecipe(OreStack input, OreStack inputTool, OreStack inputPattern, ItemStack output, int burnTime) {
		recipes.add(new WorkbenchRecipe(input, inputTool, inputPattern, output, burnTime));
	}

	public static WorkbenchRecipe getRecipe(ItemStack input, ItemStack inputTool, ItemStack inputPattern) {
		for (WorkbenchRecipe sr : WorkbenchRecipeManager.recipes) {
			boolean foundInput = false;
			boolean foundTool = false;
			boolean foundPattern = false;

			if (sr.getInputPattern() == null && sr.getsInputPattern() == null) {
				foundPattern = true;
			}
			if (sr.getInput() != null && input != null) {
				if (sr.getInput().getItem() == input.getItem() && sr.getInput().getItemDamage() == input.getItemDamage()
						&& input.stackSize >= sr.getInput().stackSize) {
					foundInput = true;
				}
			}
			if (sr.getInputPattern() != null && inputPattern != null) {
				if (sr.getInputPattern().getItem() == inputPattern.getItem()
						&& sr.getInputPattern().getItemDamage() == inputPattern.getItemDamage()) {
					foundPattern = true;
				}
			}
			if (sr.getInputTool() != null && inputTool != null) {
				if (sr.getInputTool().getItem() == inputTool.getItem()) {
					foundTool = true;
				}
			}
			if (sr.getsInput() != null && input != null) {
				List<ItemStack> list = OreDictionary.getOres(sr.getsInput().getOreDict());
				for (ItemStack stack : list) {
					if (input.getItem() == stack.getItem() && input.stackSize >= sr.getsInput().getStackSize()) {
						foundInput = true;
						break;
					}
				}
			}
			if (sr.getsInputPattern() != null && inputPattern != null) {
				List<ItemStack> list = OreDictionary.getOres(sr.getsInputPattern().getOreDict());
				for (ItemStack stack : list) {
					if (inputPattern.getItem() == stack.getItem()) {
						foundPattern = true;
						break;
					}
				}
			}
			if (sr.getsInputTool() != null && inputTool != null) {
				List<ItemStack> list = OreDictionary.getOres(sr.getsInputTool().getOreDict());
				for (ItemStack stack : list) {
					if (inputTool.getItem() == stack.getItem()) {
						foundTool = true;
						break;
					}
				}
			}
			if (foundInput && foundPattern && foundTool) {
				return sr;
			}
		}
		return null;
	}

	public static ArrayList<WorkbenchRecipe> getRecipes() {
		return recipes;
	}

	public static WorkbenchRecipeManager getInstance() {
		return instance;
	}

	@Override
	public void addOutput(ItemStack stack) {
		outputs.add(stack);
	}

	@Override
	public ArrayList<ItemStack> getOutputs() {
		return outputs;
	}

}