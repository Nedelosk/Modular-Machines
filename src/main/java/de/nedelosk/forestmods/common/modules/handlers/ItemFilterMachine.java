package de.nedelosk.forestmods.common.modules.handlers;

import de.nedelosk.forestmods.api.modules.IModuleAdvanced;
import de.nedelosk.forestmods.api.modules.handlers.IContentFilter;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.recipes.RecipeRegistry;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemFilterMachine implements IContentFilter<ItemStack> {

	@Override
	public boolean isValid(int index, ItemStack content, ModuleStack<IModuleAdvanced> moduleStack, ForgeDirection facing) {
		return RecipeRegistry.isRecipeInput(moduleStack.getModule().getRecipeCategory(), new RecipeItem(index, content));
	}
}
