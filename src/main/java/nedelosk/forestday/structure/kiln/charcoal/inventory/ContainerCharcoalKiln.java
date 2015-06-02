package nedelosk.forestday.structure.kiln.charcoal.inventory;

import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Slot;

public class ContainerCharcoalKiln extends ContainerBase {

	public ContainerCharcoalKiln(TileBaseInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		
		addSlotToContainer(new Slot(tile, 0, 29, 52));
		addSlotToContainer(new Slot(tile, 1, 47, 52));
		addSlotToContainer(new Slot(tile, 2, 65, 52));
		addSlotToContainer(new Slot(tile, 3, 83, 52));
		
		addSlotToContainer(new Slot(tile, 4, 38, 34));
		addSlotToContainer(new Slot(tile, 5, 56, 34));
		addSlotToContainer(new Slot(tile, 6, 74, 34));
		
		addSlotToContainer(new Slot(tile, 7, 47, 16));
		addSlotToContainer(new Slot(tile, 8, 65, 16));

		addSlotToContainer(new Slot(tile, 9, 132, 10));
		addSlotToContainer(new Slot(tile, 10, 150, 10));
	}

}
