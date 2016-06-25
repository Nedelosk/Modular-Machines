package de.nedelosk.modularmachines.common.modules.tools.jei;

import java.util.List;

import javax.annotation.Nonnull;

import de.nedelosk.modularmachines.common.plugins.jei.ModuleRecipeCategory;
import de.nedelosk.modularmachines.common.plugins.jei.ModuleRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import net.minecraft.client.Minecraft;

public class AlloySmelterRecipeCategory extends ModuleRecipeCategory {
	@Nonnull
	private final IDrawableAnimated arrow;
	private int inputSlotFirst = 0;
	private int inputSlotSecond = 1;
	private int outputSlotFirst = 2;
	private int outputSlotSecond = 3;

	public AlloySmelterRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createDrawable(guiTexture, 0, 0, 166, 65), "module.inventory.alloysmelter.name", ModuleCategoryUIDs.ALLOYSMELTER);

		IDrawableStatic arrowDrawable = guiHelper.createDrawable(widgetTexture, 54, 0, 22, 17);
		this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
	}

	@Override
	public void drawAnimations(Minecraft minecraft) {
		arrow.draw(minecraft, 82, 24);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ModuleRecipeWrapper recipeWrapper) {
		recipeLayout.getItemStacks().init(inputSlotFirst, true, 36, 24);
		recipeLayout.getItemStacks().init(inputSlotSecond, true, 54, 24);
		recipeLayout.getItemStacks().init(outputSlotFirst, false, 116, 24);
		recipeLayout.getItemStacks().init(outputSlotSecond, false, 134, 24);

		List inputs = recipeWrapper.getInputs();
		recipeLayout.getItemStacks().setFromRecipe(inputSlotFirst, inputs.get(0));
		if(inputs.get(1) != null){
			recipeLayout.getItemStacks().setFromRecipe(inputSlotSecond, inputs.get(1));
		}

		List outputs = recipeWrapper.getOutputs();
		recipeLayout.getItemStacks().setFromRecipe(outputSlotFirst, outputs.get(0));
		if(outputs.get(1) != null){
			recipeLayout.getItemStacks().setFromRecipe(outputSlotSecond, outputs.get(1));
		}
	}
}
