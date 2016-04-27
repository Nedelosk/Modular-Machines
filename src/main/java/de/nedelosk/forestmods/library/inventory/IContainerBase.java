package de.nedelosk.forestmods.library.inventory;

import net.minecraft.inventory.Slot;

public interface IContainerBase<T extends IGuiHandler> {

	void addSlot(Slot slot);

	T getInventoryBase();
}
