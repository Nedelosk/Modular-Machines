package nedelosk.modularmachines.api.modular.machines.basic;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotModular extends Slot {

	public SlotModular(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_, String page) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
		this.page = page;
	}
	
	public String page;
	
	@Override
	public ItemStack getStack() {
		return ((IModularTileEntity<IModularInventory>)inventory).getModular().getInventoryManager().getStackInSlot(page, this.getSlotIndex());
	}
	
	@Override
    public void putStack(ItemStack p_75215_1_)
    {
		((IModularTileEntity<IModularInventory>)inventory).getModular().getInventoryManager().setInventorySlotContents(this.page, getSlotIndex(), p_75215_1_);
        this.onSlotChanged();
    }
	
	@Override
    public ItemStack decrStackSize(int p_75209_1_)
    {
        return ((IModularTileEntity<IModularInventory>)inventory).getModular().getInventoryManager().decrStackSize(this.page, getSlotIndex(), p_75209_1_);
    }

}
