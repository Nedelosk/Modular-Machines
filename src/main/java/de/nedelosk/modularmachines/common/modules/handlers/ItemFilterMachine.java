package de.nedelosk.modularmachines.common.modules.handlers;

import de.nedelosk.modularmachines.api.modules.handlers.IContentFilter;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tool.IModuleTool;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.api.recipes.RecipeRegistry;
import net.minecraft.item.ItemStack;

public class ItemFilterMachine implements IContentFilter<ItemStack, IModuleTool> {

	@Override
	public boolean isValid(int index, ItemStack content, IModuleState<IModuleTool> state) {
		return RecipeRegistry.isRecipeInput(state.getModule().getRecipeCategory(state), new RecipeItem(index, content));
	}
}
