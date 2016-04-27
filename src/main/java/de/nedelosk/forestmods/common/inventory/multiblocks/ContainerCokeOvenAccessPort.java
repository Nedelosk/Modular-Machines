package de.nedelosk.forestmods.common.inventory.multiblocks;

import de.nedelosk.forestmods.common.blocks.tile.TileCokeOvenAccessPort;
import de.nedelosk.forestmods.common.inventory.slots.SlotCokeOven;
import de.nedelosk.forestmods.common.inventory.slots.SlotOutput;
import de.nedelosk.forestmods.library.inventory.ContainerBase;
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
