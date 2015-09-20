package nedelosk.modularmachines.common.inventory;

import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerSelectionSlot extends ContainerBase {
	
	public ContainerSelectionSlot(IInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
		addSlot(inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
	}
	
	protected void addSlot(InventoryPlayer inventory){
		addSlotToContainer(new Slot(inventoryBase, 0, 80, 35));
	}

}
