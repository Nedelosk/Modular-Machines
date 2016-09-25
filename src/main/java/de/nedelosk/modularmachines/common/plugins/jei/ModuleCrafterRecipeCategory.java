package de.nedelosk.modularmachines.common.plugins.jei;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.util.Translator;
import net.minecraft.util.ResourceLocation;

public class ModuleCrafterRecipeCategory extends BlankRecipeCategory<ModuleCrafterRecipeWrapper> {

	private static final int craftOutputSlot = 0;
	private static final int craftInputSlot1 = 1;
	private static final int craftHolderSlot = 11;

	public static final int width = 144;
	public static final int height = 54;

	@Nonnull
	private final IDrawable background;
	@Nonnull
	private final String localizedName;
	@Nonnull
	private final ICraftingGridHelper craftingGridHelper;

	public ModuleCrafterRecipeCategory(IGuiHelper guiHelper) {
		ResourceLocation location = new ResourceLocation("modularmachines", "textures/gui/module_crafter.png");
		background = guiHelper.createDrawable(location, 7, 16, width, height);
		localizedName = Translator.translateToLocal("gui.jei.category.moduleCrafter");
		craftingGridHelper = guiHelper.createCraftingGridHelper(craftInputSlot1, craftOutputSlot);
	}

	@Override
	@Nonnull
	public String getUid() {
		return CategoryUIDs.CRAFTING;
	}

	@Nonnull
	@Override
	public String getTitle() {
		return localizedName;
	}

	@Override
	@Nonnull
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ModuleCrafterRecipeWrapper recipeWrapper) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(craftOutputSlot, false, 122, 18);

		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 3; ++x) {
				int index = craftInputSlot1 + x + (y * 3);
				guiItemStacks.init(index, true, 27 + x * 18, y * 18);
			}
		}

		guiItemStacks.init(craftHolderSlot, true, 0, 0);

		craftingGridHelper.setInput(guiItemStacks, recipeWrapper.getGridInputs(), recipeWrapper.getWidth(), recipeWrapper.getHeight());
		craftingGridHelper.setOutput(guiItemStacks, recipeWrapper.getOutputs());

		guiItemStacks.set(craftHolderSlot, recipeWrapper.getHolder());
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull ModuleCrafterRecipeWrapper recipeWrapper, IIngredients ingredients) {
		setRecipe(recipeLayout, recipeWrapper);
	}

}
