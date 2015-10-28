package nedelosk.forestday.api.guis;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public interface IContainerBase<T extends IInventory> {

	void addSlot(Slot slot);

	T getInventoryBase();

}
