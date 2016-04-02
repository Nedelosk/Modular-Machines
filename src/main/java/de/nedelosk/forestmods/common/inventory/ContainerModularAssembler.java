package de.nedelosk.forestmods.common.inventory;

import de.nedelosk.forestcore.inventory.ContainerBase;
import de.nedelosk.forestmods.common.blocks.tile.TileModularAssembler;
import de.nedelosk.forestmods.common.inventory.slots.SlotModularAssembler;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerModularAssembler extends ContainerBase<TileModularAssembler> {

	public ContainerModularAssembler(TileModularAssembler tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		addSlotToContainer(new SlotModularAssembler(inventoryBase, 0, p_i1824_3_, p_i1824_4_));
	}
}
