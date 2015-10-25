package nedelosk.forestday.common.inventory;

import nedelosk.forestday.common.blocks.tiles.TileCampfire;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerCampfire extends ContainerBase<TileCampfire> {

	public ContainerCampfire(TileCampfire tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		addSlotToContainer(new Slot(inventoryBase, 0, 25, 35));
		addSlotToContainer(new Slot(inventoryBase, 1, 43, 35));
		addSlotToContainer(new Slot(inventoryBase, 2, 65, 53));
		addSlotToContainer(new Slot(inventoryBase, 3, 116, 35));
		
	}

}
