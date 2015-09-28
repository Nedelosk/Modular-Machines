package nedelosk.modularmachines.common.inventory.slots;

import nedelosk.modularmachines.common.inventory.ContainerModularAssembler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class SlotAssemblerOut extends Slot
{
	public ContainerModularAssembler parent;

    public SlotAssemblerOut(int par3, int par4, int par5, ContainerModularAssembler parent)
    {
        super(parent.getInventoryBase(), par3, par4, par5);
        this.parent = parent;
    }

    @Override
	public boolean isItemValid (ItemStack stack)
    {
        return false;
    }
    
    @Override
    public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
      parent.onResultTaken(playerIn, stack);
      stack.onCrafting(playerIn.getEntityWorld(), playerIn, 1);

      super.onPickupFromSlot(playerIn, stack);
    }
}