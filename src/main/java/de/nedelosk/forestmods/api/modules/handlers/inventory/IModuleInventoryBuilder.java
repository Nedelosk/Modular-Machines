package de.nedelosk.forestmods.api.modules.handlers.inventory;

import de.nedelosk.forestmods.api.modules.handlers.IModuleContentBuilder;
import net.minecraft.item.ItemStack;

public interface IModuleInventoryBuilder extends IModuleContentBuilder<ItemStack> {

	void initSlot(int index, boolean isInput);

	void setPlayerInvPosition(int playerInvPosition);

	void setInventoryName(String name);

	@Override
	IModuleInventory build();
}
