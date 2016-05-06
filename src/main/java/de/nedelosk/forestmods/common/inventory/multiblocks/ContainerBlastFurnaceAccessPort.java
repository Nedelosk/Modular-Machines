package de.nedelosk.forestmods.common.inventory.multiblocks;

import de.nedelosk.forestmods.common.blocks.tile.TileBlastFurnaceAccessPort;
import de.nedelosk.forestmods.common.inventory.slots.SlotBlastFurnace;
import de.nedelosk.forestmods.library.inventory.ContainerBase;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerBlastFurnaceAccessPort extends ContainerBase<TileBlastFurnaceAccessPort> {

	public ContainerBlastFurnaceAccessPort(TileBlastFurnaceAccessPort tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		addSlotToContainer(new SlotBlastFurnace(handler, 0, 53, 35));
		addSlotToContainer(new SlotBlastFurnace(handler, 1, 71, 35));
		addSlotToContainer(new SlotBlastFurnace(handler, 2, 89, 35));
		addSlotToContainer(new SlotBlastFurnace(handler, 3, 107, 35));
	}
}
