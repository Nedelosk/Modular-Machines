package de.nedelosk.forestmods.common.inventory.multiblocks;

import de.nedelosk.forestcore.inventory.ContainerBase;
import de.nedelosk.forestmods.common.blocks.tile.TileBlastFurnaceBase;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerBlastFurnaceFluidPort extends ContainerBase<TileBlastFurnaceBase> {

	public ContainerBlastFurnaceFluidPort(TileBlastFurnaceBase tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
	}
}
