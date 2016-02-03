package de.nedelosk.forestmods.common.inventory;

import de.nedelosk.forestcore.inventory.ContainerBase;
import de.nedelosk.forestmods.common.blocks.tile.TileCowperAccessPort;
import de.nedelosk.forestmods.common.inventory.slots.SlotAirHeatingPlant;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerCowperAccessPort extends ContainerBase<TileCowperAccessPort> {

	public ContainerCowperAccessPort(TileCowperAccessPort tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		addSlotToContainer(new SlotAirHeatingPlant(inventoryBase, 0, 53, 35));
	}
}
