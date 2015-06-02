package nedelosk.forestday.structure.base.gui.container;

import nedelosk.forestday.structure.base.blocks.tile.TileRegulator;
import nedelosk.forestday.structure.base.gui.slots.SlotCoilGrinding;
import nedelosk.forestday.structure.base.gui.slots.SlotCoilHeat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerRegulatorGrinding extends ContainerRegulator {

	public ContainerRegulatorGrinding(InventoryPlayer inventory, TileRegulator tile) {
		super(inventory, tile);
		this.tile = tile;
		
		
		addSlotsToContainer(inventory);
	}

	private TileRegulator tile;
	
	private void addSlotsToContainer(InventoryPlayer inventory)
	{
		
		addSlotToContainer(new SlotCoilGrinding(tile, 0, 62, 16));
		addSlotToContainer(new SlotCoilGrinding(tile, 1, 80, 16));
		addSlotToContainer(new SlotCoilGrinding(tile, 2, 98, 16));
		addSlotToContainer(new SlotCoilGrinding(tile, 3, 98, 34));
		addSlotToContainer(new SlotCoilGrinding(tile, 4, 98, 52));
		addSlotToContainer(new SlotCoilGrinding(tile, 5, 80, 52));
		addSlotToContainer(new SlotCoilGrinding(tile, 6, 62, 52));
		addSlotToContainer(new SlotCoilGrinding(tile, 7, 62, 34));
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUseableByPlayer(player);
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) 
	{
		ItemStack itemStack = null;
		Slot sourceSlot = (Slot) inventorySlots.get(slot);
		
		if (sourceSlot != null){
			ItemStack sourceItemStack = sourceSlot.getStack();
			if (sourceItemStack == null)
				return null;
			itemStack = sourceItemStack.copy();			
		
			if (slot > 35){
				/* Machine into player inventory*/
				if(!this.mergeItemStack(sourceItemStack, 0, 35, true)){
					return null;
				}
				
			}
			
			
			if (sourceItemStack.stackSize == 0){
            	sourceSlot.putStack(null);
            } 
			else {
            	sourceSlot.onSlotChanged();
            }

            if (sourceItemStack.stackSize == itemStack.stackSize) {
            	return null;
            }
			
            sourceSlot.onPickupFromSlot(player, sourceItemStack);
		}

        return itemStack;
    }

}
