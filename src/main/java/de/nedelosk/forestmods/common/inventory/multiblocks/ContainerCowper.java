package de.nedelosk.forestmods.common.inventory.multiblocks;

import de.nedelosk.forestmods.common.blocks.tile.TileCowperBase;
import de.nedelosk.forestmods.common.inventory.slots.SlotAirHeatingPlant;
import de.nedelosk.forestmods.library.inventory.ContainerBase;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerCowper extends ContainerBase<TileCowperBase> {

	public ContainerCowper(TileCowperBase tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		addSlotToContainer(new SlotAirHeatingPlant(handler.getController().getInventory(), 0, 80, 35));
	}
}
