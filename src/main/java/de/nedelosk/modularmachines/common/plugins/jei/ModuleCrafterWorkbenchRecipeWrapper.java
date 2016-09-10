package de.nedelosk.modularmachines.common.plugins.jei;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import de.nedelosk.modularmachines.api.recipes.IModuleCrafterRecipe;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.ItemStack;

public class ModuleCrafterWorkbenchRecipeWrapper extends BlankRecipeWrapper implements IShapedCraftingRecipeWrapper {

	@Nonnull
	private final IModuleCrafterRecipe recipe;
	private final int width;
	private final int height;

	public ModuleCrafterWorkbenchRecipeWrapper(@Nonnull IModuleCrafterRecipe recipe) {
		this.recipe = recipe;
		for (Object input : this.recipe.getInput()) {
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
		return Arrays.asList(recipe.getInput());
	}

	@Nonnull
	@Override
	public List<ItemStack> getOutputs() {
		return Collections.singletonList(recipe.getRecipeOutput());
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

}
