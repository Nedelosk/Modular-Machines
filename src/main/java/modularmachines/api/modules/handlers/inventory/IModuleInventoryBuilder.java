package modularmachines.api.modules.handlers.inventory;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.handlers.IModuleContentHandlerBuilder;
import modularmachines.api.modules.handlers.filters.IContentFilter;

public interface IModuleInventoryBuilder<M extends IModule> extends IModuleContentHandlerBuilder<ItemStack, M> {

	/**
	 * @return The index of the new slot.
	 */
	int addInventorySlot(boolean isInput, int xPosition, int yPosition, IContentFilter<ItemStack, M>... filters);

	int addInventorySlot(boolean isInput, int xPosition, int yPosition, String backgroundTexture, IContentFilter<ItemStack, M>... filters);

	@Override
	IModuleInventory build();
}
