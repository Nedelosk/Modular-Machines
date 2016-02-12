package de.nedelosk.forestmods.common.inventory.multiblocks;

import de.nedelosk.forestcore.inventory.ContainerBase;
import de.nedelosk.forestmods.common.blocks.tile.TileCowperBase;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerCowperFluidPort extends ContainerBase<TileCowperBase> {

	public ContainerCowperFluidPort(TileCowperBase tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
	}
}
