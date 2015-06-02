package nedelosk.forestday.structure.base.gui.container;

import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Slot;

public class ContainerCoilEngine extends ContainerBase {

	public ContainerCoilEngine(TileBaseInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		
		addSlotToContainer(new Slot(tile, 0, 80, 35));
		
	}

}
