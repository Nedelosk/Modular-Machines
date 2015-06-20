package nedelosk.forestday.common.machines.base.furnace.base;

import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;

public class ContainerFurnace extends ContainerBase {

	public ContainerFurnace(TileBaseInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
        this.addSlotToContainer(new Slot(inventoryBase, 0, 56, 35));
        this.addSlotToContainer(new SlotFurnace(inventory.player, inventoryBase, 1, 116, 35));
	}

}
