package nedelosk.forestday.common.machines.base.wood.workbench;

import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.common.blocks.tile.TileMachineBase;
import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerWorkbench extends ContainerBase {

	public ContainerWorkbench(TileBaseInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		
		//Input
		addSlotToContainer(new Slot(inventoryBase, 0, 26, 36));
		
		//Output
		addSlotToContainer(new Slot(inventoryBase, 1, 134, 36));
		
		//Tool
		addSlotToContainer(new Slot(inventoryBase, 2, 80, 13));
		
		//Pattern
		addSlotToContainer(new Slot(inventoryBase, 3, 26, 13));
		
		//Storage
		addSlotToContainer(new Slot(inventoryBase, 4, 80, 61));
		
		//Tool Storage
		if(((TileMachineBase)inventoryBase).getBlockMetadata() == 2)
		{
		addSlotToContainer(new Slot(inventoryBase, 5, 177, 22));
		addSlotToContainer(new Slot(inventoryBase, 6, 177, 40));
		addSlotToContainer(new Slot(inventoryBase, 7, 177, 58));
		addSlotToContainer(new Slot(inventoryBase, 8, 177, 76));
		
		addSlotToContainer(new Slot(inventoryBase, 9, 195, 22));
		addSlotToContainer(new Slot(inventoryBase, 10, 195, 40));
		addSlotToContainer(new Slot(inventoryBase, 11, 195, 58));
		addSlotToContainer(new Slot(inventoryBase, 12, 195, 76));
		
		addSlotToContainer(new Slot(inventoryBase, 13, 213, 22));
		addSlotToContainer(new Slot(inventoryBase, 14, 213, 40));
		addSlotToContainer(new Slot(inventoryBase, 15, 213, 58));
		addSlotToContainer(new Slot(inventoryBase, 16, 213, 76));
		}
	}

}
