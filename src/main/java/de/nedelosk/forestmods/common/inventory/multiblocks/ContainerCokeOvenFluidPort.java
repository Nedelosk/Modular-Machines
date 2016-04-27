package de.nedelosk.forestmods.common.inventory.multiblocks;

import de.nedelosk.forestmods.common.blocks.tile.TileCokeOvenFluidPort;
import de.nedelosk.forestmods.library.inventory.ContainerBase;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerCokeOvenFluidPort extends ContainerBase<TileCokeOvenFluidPort> {

	public ContainerCokeOvenFluidPort(TileCokeOvenFluidPort tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
	}
}
