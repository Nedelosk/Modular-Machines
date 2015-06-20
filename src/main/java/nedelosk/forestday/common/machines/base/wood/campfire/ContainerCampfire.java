package nedelosk.forestday.common.machines.base.wood.campfire;

import nedelosk.forestday.common.machines.base.slots.SlotCampfire;
import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerCampfire extends ContainerBase {

	public ContainerCampfire(IInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		addSlotToContainer(new Slot(inventoryBase, 0, 25, 35));
		addSlotToContainer(new Slot(inventoryBase, 1, 43, 35));
		addSlotToContainer(new Slot(inventoryBase, 2, 65, 53));
		addSlotToContainer(new Slot(inventoryBase, 3, 116, 35));
		addSlotToContainer(new SlotCampfire(inventoryBase, 4, 152, 17, "curb"));
		addSlotToContainer(new SlotCampfire(inventoryBase, 5, 152, 35, "pot_holder"));
		addSlotToContainer(new SlotCampfire(inventoryBase, 6, 152, 53, "pot"));
		
	}

}
