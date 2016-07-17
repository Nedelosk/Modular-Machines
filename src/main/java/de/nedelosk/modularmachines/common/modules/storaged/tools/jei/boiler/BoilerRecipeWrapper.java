package de.nedelosk.modularmachines.common.modules.storaged.tools.jei.boiler;

import java.awt.Color;

import de.nedelosk.modularmachines.api.Translator;
import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.Recipe;
import de.nedelosk.modularmachines.common.plugins.jei.ModuleRecipeWrapper;
import net.minecraft.client.Minecraft;

public class BoilerRecipeWrapper extends ModuleRecipeWrapper {

	public BoilerRecipeWrapper(IRecipe recipe, String recipeCategoryUid) {
		super(recipe, recipeCategoryUid);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		minecraft.fontRendererObj.drawString(Translator.translateToLocalFormatted("gui.mm.jei.heat", recipe.get(Recipe.HEAT)), 58, 14, Color.gray.getRGB());
	}
}
