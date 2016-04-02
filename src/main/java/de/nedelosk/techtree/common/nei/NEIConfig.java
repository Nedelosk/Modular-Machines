package de.nedelosk.techtree.common.nei;

import codechicken.nei.api.IConfigureNEI;

public class NEIConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		/*
		 * GuiCraftingRecipe.craftinghandlers.add(new
		 * ShapedModularCraftingHandler()); GuiUsageRecipe.usagehandlers.add(new
		 * ShapedModularCraftingHandler());
		 * GuiCraftingRecipe.craftinghandlers.add(new
		 * ShapelessModularCraftingHandler());
		 * GuiUsageRecipe.usagehandlers.add(new
		 * ShapelessModularCraftingHandler());
		 */
	}

	@Override
	public String getName() {
		return "Tech Tree NEI Plugin";
	}

	@Override
	public String getVersion() {
		return "1.0a";
	}
}
