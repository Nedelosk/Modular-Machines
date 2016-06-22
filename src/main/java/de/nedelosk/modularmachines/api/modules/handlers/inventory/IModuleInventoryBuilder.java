package de.nedelosk.modularmachines.api.modules.handlers.inventory;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.IContentFilter;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentBuilder;
import net.minecraft.item.ItemStack;

public interface IModuleInventoryBuilder<M extends IModule> extends IModuleContentBuilder<ItemStack, M> {

	/**
	 * @return The index of the new slot.
	 */
	int addInventorySlot(boolean isInput, IContentFilter<ItemStack, M>... filters);

	/**
	 * Set the title of the inventory.
	 */
	void setInventoryName(String name);

	@Override
	IModuleInventory build();
}
