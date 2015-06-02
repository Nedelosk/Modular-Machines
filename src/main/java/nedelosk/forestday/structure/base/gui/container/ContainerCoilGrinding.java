package nedelosk.forestday.structure.base.gui.container;

import nedelosk.forestday.structure.base.blocks.tile.TileCoilGrinding;
import nedelosk.forestday.structure.base.gui.slots.SlotCoilGrinding;
import nedelosk.nedeloskcore.common.blocks.tile.TileBase;
import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerCoilGrinding extends ContainerBase {

	public ContainerCoilGrinding(TileCoilGrinding tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		
		//Input
		addSlotToContainer(new Slot(tile, 0, 26, 35));
		
		//Output
		addSlotToContainer(new Slot(tile, 1, 134, 35));
		
		//Coils
		addSlotToContainer(new SlotCoilGrinding(tile, 2, 80, 8));
		addSlotToContainer(new SlotCoilGrinding(tile, 3, 80, 26));
		addSlotToContainer(new SlotCoilGrinding(tile, 4, 80, 44));
		addSlotToContainer(new SlotCoilGrinding(tile, 5, 80, 62));
		
	}

}
