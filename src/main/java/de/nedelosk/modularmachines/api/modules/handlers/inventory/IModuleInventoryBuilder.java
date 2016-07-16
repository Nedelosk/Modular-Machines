package de.nedelosk.modularmachines.api.modules.handlers.inventory;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.IContentFilter;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandlerBuilder;
import net.minecraft.item.ItemStack;

public interface IModuleInventoryBuilder<M extends IModule> extends IModuleContentHandlerBuilder<ItemStack, M> {

	/**
	 * @return The index of the new slot.
	 */
	int addInventorySlot(boolean isInput, int xPosition, int yPosition, IContentFilter<ItemStack, M>... filters);

	@Override
	IModuleInventory build();
}
