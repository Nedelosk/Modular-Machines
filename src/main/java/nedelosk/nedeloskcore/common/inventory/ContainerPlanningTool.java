package nedelosk.nedeloskcore.common.inventory;

import java.util.List;

import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.common.inventory.slots.SlotPlan;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerPlanningTool extends ContainerBase {
	
	public ContainerPlanningTool(InventoryPlanningTool toolInv, InventoryPlayer inventory) {
		super(toolInv, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		addSlotToContainer(new SlotPlan(inventoryBase, 0, 80, 27));
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		if(!player.worldObj.isRemote)
		((InventoryPlanningTool)inventoryBase).onGuiSaved(player);
	}

}
