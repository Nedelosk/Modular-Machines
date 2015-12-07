package nedelosk.forestday.common.inventory;

import nedelosk.forestday.api.inventory.ContainerBase;
import nedelosk.forestday.common.blocks.tiles.TileKiln;
import nedelosk.forestday.common.inventory.slots.SlotOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerKiln extends ContainerBase<TileKiln> {

	public ContainerKiln(InventoryPlayer inventory, TileKiln tile) {
		super(tile, inventory);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		return null;
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {

		// input
		addSlotToContainer(new Slot(inventoryBase, 0, 44, 26));
		addSlotToContainer(new Slot(inventoryBase, 1, 44, 44));

		// outnput
		addSlotToContainer(new SlotOutput(inventoryBase, 2, 116, 26));
		addSlotToContainer(new SlotOutput(inventoryBase, 3, 116, 44));

	}

}
