package de.nedelosk.forestmods.library.modules.handlers.inventory;

import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.handlers.IContentFilter;
import de.nedelosk.forestmods.library.modules.handlers.IModuleContentBuilder;
import net.minecraft.item.ItemStack;

public interface IModuleInventoryBuilder<M extends IModule> extends IModuleContentBuilder<ItemStack, M> {

	void initSlot(int index, boolean isInput, IContentFilter<ItemStack, M>... filters);

	void setInventoryName(String name);

	@Override
	IModuleInventory build();
}
