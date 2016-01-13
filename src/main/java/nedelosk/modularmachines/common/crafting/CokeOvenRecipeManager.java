package nedelosk.modularmachines.common.crafting;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestday.api.crafting.OreStack;
import nedelosk.modularmachines.api.multiblocks.ICokeOvenRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CokeOvenRecipeManager implements ICokeOvenRecipe {

	private static ArrayList<CokeOvenRecipe> recipes = new ArrayList();
	public static CokeOvenRecipeManager instance;

	public static void addRecipe(CokeOvenRecipe recipe) {
		recipes.add(recipe);
	}

	public static void removeRecipe(CokeOvenRecipe recipe) {
		recipes.remove(recipe);
	}

	public static void addAllRecipes(List<CokeOvenRecipe> recipe) {
		recipes.addAll(recipe);
	}

	@Override
	public void addRecipe(int burnTime, ItemStack input, ItemStack output) {
		recipes.add(new CokeOvenRecipe(burnTime, input, output));
	}

	@Override
	public void addRecipe(int burnTime, OreStack input, ItemStack output) {
		recipes.add(new CokeOvenRecipe(burnTime, input, output));
	}

	public static boolean isItemInput(ItemStack stack) {
		for ( CokeOvenRecipe sr : CokeOvenRecipeManager.recipes ) {
			Object obj = sr.getInput();
			if (obj instanceof ItemStack) {
				ItemStack stackInput = (ItemStack) obj;
				if (stackInput.getItem() == stack.getItem() && stackInput.getItemDamage() == stack.getItemDamage()
						&& ItemStack.areItemStackTagsEqual(stack, stackInput)) {
					return true;
				}
			} else if (obj instanceof OreStack) {
				List<ItemStack> list = OreDictionary.getOres(((OreStack) obj).oreDict);
				for ( ItemStack stackInput : list ) {
					if (stackInput.getItem() == stack.getItem() && stackInput.getItemDamage() == stack.getItemDamage()
							&& ItemStack.areItemStackTagsEqual(stack, stackInput)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static CokeOvenRecipe getRecipe(ItemStack stack) {
		for ( CokeOvenRecipe sr : CokeOvenRecipeManager.recipes ) {
			Object obj = sr.getInput();
			if (obj instanceof ItemStack) {
				ItemStack stackInput = (ItemStack) obj;
				if (stackInput.getItem() == stack.getItem() && stackInput.getItemDamage() == stack.getItemDamage()
						&& ItemStack.areItemStackTagsEqual(stack, stackInput)) {
					return sr;
				}
			} else if (obj instanceof OreStack) {
				List<ItemStack> list = OreDictionary.getOres(((OreStack) obj).oreDict);
				for ( ItemStack stackInput : list ) {
					if (stackInput.getItem() == stack.getItem() && stackInput.getItemDamage() == stack.getItemDamage()
							&& ItemStack.areItemStackTagsEqual(stack, stackInput)) {
						return sr;
					}
				}
			}
		}
		return null;
	}

	public static ArrayList<CokeOvenRecipe> getRecipes() {
		return recipes;
	}

	public static CokeOvenRecipeManager getInstance() {
		return instance;
	}

	public class CokeOvenRecipe {

		private Object input;
		private ItemStack output;
		private int burntTime;

		public CokeOvenRecipe(int burnTime, ItemStack input, ItemStack output) {
			this.input = input;
			this.output = output;
			this.burntTime = burnTime;
		}

		public CokeOvenRecipe(int burnTime, OreStack input, ItemStack output) {
			this.input = input;
			this.output = output;
			this.burntTime = burnTime;
		}

		public int getBurntTime() {
			return burntTime;
		}

		public Object getInput() {
			return input;
		}

		public ItemStack getOutput() {
			return output;
		}
	}
}