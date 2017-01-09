package modularmachines.common.plugins.jei.pulverizer;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import modularmachines.common.plugins.jei.CategoryUIDs;
import modularmachines.common.plugins.jei.ModuleRecipeCategory;
import modularmachines.common.plugins.jei.ModuleRecipeWrapper;
import modularmachines.common.plugins.jei.TooltipCallback;

public class PulverizerRecipeCategory extends ModuleRecipeCategory {

	@Nonnull
	private final IDrawableAnimated arrow;
	@Nonnull
	private final IDrawable arrowDefault;
	private static final int INPUT = 0;
	private static final int OUTPUT_FIRST = 1;
	private static final int OUTPUT_SECOND = 2;

	public PulverizerRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper.createBlankDrawable(166, 55), guiHelper, "gui.mm.jei.category.pulverizer", CategoryUIDs.PULVERIZER);
		arrowDefault = guiHelper.createDrawable(WIDGET_TEXTURE, 54, 0, 22, 17);
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(WIDGET_TEXTURE, 76, 0, 22, 17);
		this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 100, IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		slot.draw(minecraft, 56, 16);
		slot.draw(minecraft, 116, 16);
		slot.draw(minecraft, 134, 16);
	}

	@Override
	public void drawAnimations(Minecraft minecraft) {
		arrowDefault.draw(minecraft, 82, 16);
		arrow.draw(minecraft, 82, 16);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ModuleRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemGroup = recipeLayout.getItemStacks();
		// init input slot
		itemGroup.init(INPUT, true, 56, 16);
		// init output slots
		itemGroup.init(OUTPUT_FIRST, false, 116, 16);
		itemGroup.init(OUTPUT_SECOND, false, 134, 16);
		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		// set input
		itemGroup.set(INPUT, inputs.get(0));
		// set outputs
		TooltipCallback tooltipCallback = new TooltipCallback();
		List<ItemStack> outputs = ingredients.getOutputs(ItemStack.class);
		itemGroup.set(OUTPUT_FIRST, outputs.get(0));
		tooltipCallback.addChanceTooltip(OUTPUT_FIRST, recipeWrapper.getChance(0));
		if (outputs.size() > 1 && outputs.get(1) != null) {
			itemGroup.set(OUTPUT_SECOND, outputs.get(1));
			tooltipCallback.addChanceTooltip(OUTPUT_SECOND, recipeWrapper.getChance(1));
		}
		itemGroup.addTooltipCallback(tooltipCallback);
	}
}
