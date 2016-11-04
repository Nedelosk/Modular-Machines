package modularmachines.common.plugins.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import modularmachines.api.recipes.IModuleCrafterRecipe;
import net.minecraft.item.ItemStack;

public class ModuleCrafterRecipeWrapper extends BlankRecipeWrapper implements IShapedCraftingRecipeWrapper {

	@Nonnull
	private final IModuleCrafterRecipe recipe;
	private final int width;
	private final int height;

	public ModuleCrafterRecipeWrapper(@Nonnull IModuleCrafterRecipe recipe) {
		this.recipe = recipe;
		for(Object input : this.recipe.getInput()) {
			if (input instanceof ItemStack) {
				ItemStack itemStack = (ItemStack) input;
				if (itemStack.stackSize != 1) {
					itemStack.stackSize = 1;
				}
			}
		}
		this.width = recipe.getWidth();
		this.height = recipe.getHeight();
	}

	@Nonnull
	@Override
	public List getInputs() {
		List items = new ArrayList<>();
		items.add(getHolder());
		items.addAll(Arrays.asList(recipe.getInput()));
		return items;
	}

	@Nonnull
	public List getGridInputs() {
		return Arrays.asList(recipe.getInput());
	}

	@Nonnull
	@Override
	public List<ItemStack> getOutputs() {
		return Collections.singletonList(recipe.getRecipeOutput());
	}

	public ItemStack getHolder() {
		return recipe.getHolder();
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, getInputs());
		ingredients.setOutputs(ItemStack.class, getOutputs());
	}
}
