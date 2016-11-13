package modularmachines.common.plugins.jei.lathe;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import modularmachines.common.plugins.jei.CategoryUIDs;
import modularmachines.common.plugins.jei.ModuleRecipeCategory;
import modularmachines.common.plugins.jei.ModuleRecipeWrapper;

public class LatheRecipeCategory extends ModuleRecipeCategory {

	@Nonnull
	private final IDrawableAnimated arrow;
	@Nonnull
	private final IDrawable arrowDefault;
	private int inputSlot = 0;
	private int outputSlotFirst = 1;
	private int outputSlotSecond = 2;

	public LatheRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createBlankDrawable(166, 55), guiHelper, "gui.mm.jei.category.lathe", CategoryUIDs.LATHE);
		arrowDefault = guiHelper.createDrawable(WIDGET_TEXTURE, 54, 0, 22, 17);
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(WIDGET_TEXTURE, 76, 0, 22, 17);
		this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 100, IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		slot.draw(minecraft, 56, 24);
		slot.draw(minecraft, 116, 24);
		slot.draw(minecraft, 134, 24);
	}

	@Override
	public void drawAnimations(Minecraft minecraft) {
		arrowDefault.draw(minecraft, 82, 24);
		arrow.draw(minecraft, 82, 24);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ModuleRecipeWrapper recipeWrapper) {
		recipeLayout.getItemStacks().init(inputSlot, true, 56, 24);
		recipeLayout.getItemStacks().init(outputSlotFirst, false, 116, 24);
		recipeLayout.getItemStacks().init(outputSlotSecond, false, 134, 24);
		List inputs = recipeWrapper.getInputs();
		recipeLayout.getItemStacks().setFromRecipe(inputSlot, inputs.get(0));
		List outputs = recipeWrapper.getOutputs();
		recipeLayout.getItemStacks().setFromRecipe(outputSlotFirst, outputs.get(0));
		if (outputs.size() > 1 && outputs.get(1) != null) {
			recipeLayout.getItemStacks().setFromRecipe(outputSlotSecond, outputs.get(1));
		}
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ModuleRecipeWrapper recipeWrapper, IIngredients ingredients) {
		setRecipe(recipeLayout, recipeWrapper);
	}
}
