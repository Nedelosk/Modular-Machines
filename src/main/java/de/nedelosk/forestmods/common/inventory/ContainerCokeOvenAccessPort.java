package de.nedelosk.forestmods.common.inventory;

import de.nedelosk.forestcore.inventory.ContainerBase;
import de.nedelosk.forestcore.inventory.slots.SlotOutput;
import de.nedelosk.forestmods.common.blocks.tile.TileCokeOvenAccessPort;
import de.nedelosk.forestmods.common.inventory.slots.SlotCokeOven;
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
