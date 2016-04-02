package de.nedelosk.forestmods.api.producers.handlers.inventory;

import de.nedelosk.forestmods.api.producers.handlers.IModuleContentBuilder;
import net.minecraft.item.ItemStack;

public interface IModuleInventoryBuilder extends IModuleContentBuilder<ItemStack> {

	void initSlot(int index, boolean isInput);

	void setInventoryName(String name);

	@Override
	IModuleInventory build();
}
