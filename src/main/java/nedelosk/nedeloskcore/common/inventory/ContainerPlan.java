package nedelosk.nedeloskcore.common.inventory;

import nedelosk.forestday.common.machines.base.slots.SlotTool;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerPlan extends ContainerBase {

	public ContainerPlan(TileBaseInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		addSlotToContainer(new Slot(inventoryBase, 0, 26, 7));
		addSlotToContainer(new Slot(inventoryBase, 1, 26, 25));
		addSlotToContainer(new Slot(inventoryBase, 2, 26, 43));
		addSlotToContainer(new Slot(inventoryBase, 3, 26, 61));
		
		addSlotToContainer(new SlotTool(inventoryBase, 4, 152, 7));
		addSlotToContainer(new SlotTool(inventoryBase, 5, 152, 25));
		addSlotToContainer(new SlotTool(inventoryBase, 6, 152, 43));
		addSlotToContainer(new SlotTool(inventoryBase, 7, 152, 61));
	}

}
