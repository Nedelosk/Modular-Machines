package de.nedelosk.forestmods.api.modules.handlers.inventory;

import de.nedelosk.forestmods.api.modules.handlers.IContentFilter;
import de.nedelosk.forestmods.api.modules.handlers.IModuleContentBuilder;
import net.minecraft.item.ItemStack;

public interface IModuleInventoryBuilder extends IModuleContentBuilder<ItemStack> {

	void initSlot(int index, boolean isInput, IContentFilter<ItemStack>... filters);

	void setInventoryName(String name);

	@Override
	IModuleInventory build();
}
