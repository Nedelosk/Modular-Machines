package de.nedelosk.modularmachines.api.inventory;

import net.minecraft.inventory.Slot;

public interface IContainerBase<T extends IGuiHandler> {

	void addSlot(Slot slot);

	T getHandler();
}
