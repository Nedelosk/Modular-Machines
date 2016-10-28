package de.nedelosk.modularmachines.common.plugins.jei.boiler;

import javax.annotation.Nonnull;

import de.nedelosk.modularmachines.common.plugins.jei.CategoryUIDs;
import de.nedelosk.modularmachines.common.plugins.jei.ModuleRecipeCategory;
import de.nedelosk.modularmachines.common.plugins.jei.ModuleRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;

public class BoilerRecipeCategory extends ModuleRecipeCategory {

	@Nonnull
	private final IDrawableAnimated arrow;
	@Nonnull
	private final IDrawable arrowDefault;
	private int inputSlotFirst = 0;
	private int inputSlotSecond = 1;
	private int outputSlotFirst = 2;
	private int outputSlotSecond = 3;

	public BoilerRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 166, 55), guiHelper, "gui.mm.jei.category.boiler", CategoryUIDs.BOILER);
		arrowDefault = guiHelper.createDrawable(widgetTexture, 54, 0, 22, 17);
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(widgetTexture, 76, 0, 22, 17);
		this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 100, IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		tank.draw(minecraft, 36, 2);
		tank.draw(minecraft, 112, 2);
	}

	@Override
	public void drawAnimations(Minecraft minecraft) {
		arrowDefault.draw(minecraft, 72, 25);
		arrow.draw(minecraft, 72, 25);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ModuleRecipeWrapper recipeWrapper) {
		recipeLayout.getFluidStacks().init(0, true, 37, 3, 16, 58, 16000, false, tankOverlay);
		recipeLayout.getFluidStacks().init(1, false, 113, 3, 16, 58, 16000, false, tankOverlay);
		recipeLayout.getFluidStacks().set(0, recipeWrapper.getFluidInputs());
		recipeLayout.getFluidStacks().set(1, recipeWrapper.getFluidOutputs());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ModuleRecipeWrapper recipeWrapper, IIngredients ingredients) {
		setRecipe(recipeLayout, recipeWrapper);
	}
}
