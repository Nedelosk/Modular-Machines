package nedelosk.modularmachines.common.inventory.multiblock;

import nedelosk.forestcore.library.inventory.ContainerBase;
import nedelosk.forestcore.library.inventory.slots.SlotOutput;
import nedelosk.modularmachines.common.inventory.slots.SlotCokeOven;
import nedelosk.modularmachines.common.multiblock.cokeoven.TileCokeOvenAccessPort;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerCokeOvenAccessPort extends ContainerBase<TileCokeOvenAccessPort> {

	public ContainerCokeOvenAccessPort(TileCokeOvenAccessPort tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		addSlotToContainer(new SlotCokeOven(inventoryBase, 0, 53, 35));
		addSlotToContainer(new SlotOutput(inventoryBase, 1, 107, 35));
	}
}
