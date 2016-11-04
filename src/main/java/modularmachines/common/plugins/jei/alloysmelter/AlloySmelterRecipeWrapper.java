package modularmachines.common.plugins.jei.alloysmelter;

import java.awt.Color;

import modularmachines.api.recipes.IRecipe;
import modularmachines.api.recipes.Recipe;
import modularmachines.common.plugins.jei.ModuleRecipeWrapper;
import modularmachines.common.utils.Translator;
import net.minecraft.client.Minecraft;

public class AlloySmelterRecipeWrapper extends ModuleRecipeWrapper {

	public AlloySmelterRecipeWrapper(IRecipe recipe, String recipeCategoryUid) {
		super(recipe, recipeCategoryUid);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		minecraft.fontRendererObj.drawString(Translator.translateToLocalFormatted("gui.mm.jei.heat", recipe.get(Recipe.HEAT)), 72, 5, Color.gray.getRGB());
	}
}
