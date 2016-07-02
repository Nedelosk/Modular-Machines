package de.nedelosk.modularmachines.common.inventory.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class SlotOutput extends Slot {

	public SlotOutput(IInventory iinventory, int slotIndex, int posX, int posY) {
		super(iinventory, slotIndex, posX, posY);
	}
	
	@Override
	public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
		for(int index = 0;index < inventory.getSizeInventory();index++){
			if(index != 1){
				inventory.removeStackFromSlot(index);
			}
		}
		
		super.onPickupFromSlot(playerIn, stack);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return false;
	}
}
