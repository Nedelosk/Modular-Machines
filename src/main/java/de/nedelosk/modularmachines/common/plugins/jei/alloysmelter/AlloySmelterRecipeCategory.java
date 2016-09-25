package de.nedelosk.modularmachines.common.plugins.jei.alloysmelter;

import java.util.List;

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

public class AlloySmelterRecipeCategory extends ModuleRecipeCategory {
	@Nonnull
	private final IDrawableAnimated arrow;
	@Nonnull
	private final IDrawable arrowDefault;
	private int inputSlotFirst = 0;
	private int inputSlotSecond = 1;
	private int outputSlotFirst = 2;
	private int outputSlotSecond = 3;

	public AlloySmelterRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 166, 55), guiHelper, "gui.mm.jei.category.alloysmelter", CategoryUIDs.ALLOYSMELTER);

		arrowDefault = guiHelper.createDrawable(widgetTexture, 54, 0, 22, 17);

		IDrawableStatic arrowDrawable = guiHelper.createDrawable(widgetTexture, 76, 0, 22, 17);
		this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 100, IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		slot.draw(minecraft, 36, 16);
		slot.draw(minecraft, 54, 16);
		slot.draw(minecraft, 116, 16);
		slot.draw(minecraft, 134, 16);
	}

	@Override
	public void drawAnimations(Minecraft minecraft) {
		arrowDefault.draw(minecraft, 82, 16);
		arrow.draw(minecraft, 82, 16);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ModuleRecipeWrapper recipeWrapper) {
		recipeLayout.getItemStacks().init(inputSlotFirst, true, 36, 16);
		recipeLayout.getItemStacks().init(inputSlotSecond, true, 54, 16);
		recipeLayout.getItemStacks().init(outputSlotFirst, false, 116, 16);
		recipeLayout.getItemStacks().init(outputSlotSecond, false, 134, 16);

		List inputs = recipeWrapper.getInputs();
		recipeLayout.getItemStacks().setFromRecipe(inputSlotFirst, inputs.get(0));
		if(inputs.get(1) != null){
			recipeLayout.getItemStacks().setFromRecipe(inputSlotSecond, inputs.get(1));
		}

		List outputs = recipeWrapper.getOutputs();
		recipeLayout.getItemStacks().setFromRecipe(outputSlotFirst, outputs.get(0));
		if(outputs.size() > 1 && outputs.get(1) != null){
			recipeLayout.getItemStacks().setFromRecipe(outputSlotSecond, outputs.get(1));
		}
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ModuleRecipeWrapper recipeWrapper, IIngredients ingredients) {
		setRecipe(recipeLayout, recipeWrapper);
	}
}
