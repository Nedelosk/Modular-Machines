package modularmachines.api.recipes;

import java.util.HashMap;

import com.google.common.collect.Maps;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeRegistry {

	private static final HashMap<String, IRecipeHandler> handlers = Maps.newHashMap();

	/**
	 * Remove a recipe from the registry
	 */
	public static boolean removeRecipe(IRecipe recipe) {
		IRecipeHandler handler = getRecipeHandler(recipe.getRecipeCategory());
		if (handler == null) {
			return false;
		}
		return handler.getRecipes().remove(recipe);
	}

	/**
	 * @param testSizeSame
	 *            Test the stackSize or the amount of the two RecipeItem's.
	 * @return True if the input equals the item
	 */
	public static boolean itemEqualsItem(RecipeItem item, RecipeItem input, boolean testSizeSame) {
		if (input != null) {
			if (item.isItem()) {
				if (!input.isItem()) {
					return false;
				} else if (item.item.getItem() == input.item.getItem() && (item.item.getItemDamage() == input.item.getItemDamage() || item.item.getItemDamage() == OreDictionary.WILDCARD_VALUE)
						&& (testSizeSame && item.item.getCount() <= input.item.getCount() || !testSizeSame) && (!item.item.hasTagCompound() && !input.item.hasTagCompound() || ItemStack.areItemStackTagsEqual(item.item, input.item))) {
					return true;
				}
				return false;
			} else if (item.isFluid()) {
				if (!input.isFluid()) {
					return false;
				} else if (item.fluid.isFluidEqual(input.fluid)) {
					if (testSizeSame && item.fluid.amount <= input.fluid.amount || !testSizeSame) {
						return true;
					}
				}
				return false;
			} else if (item.isOre()) {
				if (input.isOre()) {
					if (item.ore.equals(input.ore)) {
						if (testSizeSame && item.ore.stackSize <= input.ore.stackSize || !testSizeSame) {
							return true;
						}
					}
					return false;
				}
				if (!input.isItem()) {
					return false;
				}
				if (testSizeSame && !(item.ore.stackSize <= input.item.getCount())) {
					return false;
				}
				int ore = OreDictionary.getOreID(item.ore.oreDict);
				ItemStack oreStack = input.item.copy();
				for (int oreID : OreDictionary.getOreIDs(oreStack)) {
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
	 * @return A map with all recipe handlers that are in the registry.
	 */
	public static HashMap<String, IRecipeHandler> getHandlers() {
		return handlers;
	}
}
