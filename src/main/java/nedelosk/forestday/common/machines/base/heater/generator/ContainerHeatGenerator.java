package nedelosk.forestday.common.machines.base.heater.generator;

import nedelosk.nedeloskcore.common.blocks.tile.TileBase;
import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerHeatGenerator extends ContainerBase {

	public ContainerHeatGenerator(TileHeatGenerator tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		addSlotToContainer(new Slot(inventoryBase, 0, 62, 52));
		addSlotToContainer(new Slot(inventoryBase, 1, 80, 52));
		addSlotToContainer(new Slot(inventoryBase, 2, 98, 52));
		
		addSlotToContainer(new Slot(inventoryBase, 3, 62, 15));
		addSlotToContainer(new Slot(inventoryBase, 4, 80, 15));
		addSlotToContainer(new Slot(inventoryBase, 5, 98, 15));
	}

}
