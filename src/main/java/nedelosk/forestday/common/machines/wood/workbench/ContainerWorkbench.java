package nedelosk.forestday.common.machines.wood.workbench;

import nedelosk.forestday.common.items.tools.ItemToolForestday;
import nedelosk.forestday.common.machines.base.slots.SlotPattern;
import nedelosk.forestday.common.machines.base.slots.SlotTool;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.common.inventory.ContainerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerWorkbench extends ContainerBase {

	public ContainerWorkbench(TileBaseInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		
		//Input
		addSlotToContainer(new Slot(tile, 0, 26, 36));
		
		//Output
		addSlotToContainer(new Slot(tile, 1, 134, 36));
		
		//Tool
		addSlotToContainer(new Slot(tile, 2, 80, 13));
		
		//Pattern
		addSlotToContainer(new Slot(tile, 3, 26, 13));
		
		//Storage
		addSlotToContainer(new Slot(tile, 4, 80, 61));
		
		//Tool Storage
		if(tile.getBlockMetadata() == 1)
		{
		addSlotToContainer(new Slot(tile, 5, 177, 22));
		addSlotToContainer(new Slot(tile, 6, 177, 40));
		addSlotToContainer(new Slot(tile, 7, 177, 58));
		addSlotToContainer(new Slot(tile, 8, 177, 76));
		
		addSlotToContainer(new Slot(tile, 9, 195, 22));
		addSlotToContainer(new Slot(tile, 10, 195, 40));
		addSlotToContainer(new Slot(tile, 11, 195, 58));
		addSlotToContainer(new Slot(tile, 12, 195, 76));
		
		addSlotToContainer(new Slot(tile, 13, 213, 22));
		addSlotToContainer(new Slot(tile, 14, 213, 40));
		addSlotToContainer(new Slot(tile, 15, 213, 58));
		addSlotToContainer(new Slot(tile, 16, 213, 76));
		
		addSlotToContainer(new SlotTool(tile, 17, -18, 22));
		addSlotToContainer(new SlotTool(tile, 18, -18, 40));
		addSlotToContainer(new SlotTool(tile, 19, -18, 58));
		addSlotToContainer(new SlotTool(tile, 20, -18, 76));
		}
	}
	
	   public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
	    {
	        ItemStack itemstack = null;
	        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);

	        if (slot != null && slot.getHasStack())
	        {
	            ItemStack itemstack1 = slot.getStack();
	            itemstack = itemstack1.copy();
	            
	            if (itemstack1.getItem() instanceof ItemToolForestday)
	            {
	            	if (!this.mergeItemStack(itemstack1, 0, 36, false))
	                    {
	                        return null;
	                    }
	
	            }
	            slot.onPickupFromSlot(p_82846_1_, itemstack1);
	        }

	        return itemstack;
	    }

}
