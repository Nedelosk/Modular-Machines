package nedelosk.forestday.structure.base.gui.container;

import nedelosk.forestday.structure.base.blocks.tile.TileController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerController extends Container {

	public ContainerController(InventoryPlayer inventory, TileController tile) {
		this.tile = tile;
		
		
		addSlotsToContainer(inventory);
	}

	private TileController tile;
	
	private void addSlotsToContainer(InventoryPlayer inventory)
	{
		 addSlotToContainer(new Slot(tile, 0, 151, 15));
		
        for (int i1 = 0; i1 < 3; i1++) {
            for (int l1 = 0; l1 < 9; l1++) {
                addSlotToContainer(new Slot(inventory, l1 + i1 * 9 + 9, 8 + l1 * 18, 84 + i1 * 18));
            }
        }

        for (int j1 = 0; j1 < 9; j1++) {
            addSlotToContainer(new Slot(inventory, j1, 8 + j1 * 18, 142));
        }
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
