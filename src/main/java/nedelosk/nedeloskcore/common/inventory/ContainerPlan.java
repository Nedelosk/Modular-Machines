package nedelosk.nedeloskcore.common.inventory;

import nedelosk.nedeloskcore.common.blocks.tile.TilePlan;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerPlan extends ContainerBase<TilePlan> {

	public ContainerPlan(TilePlan tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		addSlotToContainer(new Slot(inventoryBase, 0, 26, 7));
		addSlotToContainer(new Slot(inventoryBase, 1, 26, 25));
		addSlotToContainer(new Slot(inventoryBase, 2, 26, 43));
		addSlotToContainer(new Slot(inventoryBase, 3, 26, 61));
	}

}
