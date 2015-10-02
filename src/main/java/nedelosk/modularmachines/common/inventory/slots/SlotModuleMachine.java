package nedelosk.modularmachines.common.inventory.slots;

import nedelosk.modularmachines.api.modular.machines.basic.IModularInventory;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotModuleMachine extends Slot {

	public SlotModuleMachine(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_, String page) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
		this.page = page;
	}
	
	public String page;
	
	@Override
	public ItemStack getStack() {
		return ((TileModular<IModularInventory>)inventory).getModular().getInventoryManager().getStackInSlot(page, this.getSlotIndex());
	}
	
	@Override
    public void putStack(ItemStack p_75215_1_)
    {
		((TileModular<IModularInventory>)inventory).getModular().getInventoryManager().setInventorySlotContents(this.page, getSlotIndex(), p_75215_1_);
        this.onSlotChanged();
    }
	
	@Override
    public ItemStack decrStackSize(int p_75209_1_)
    {
        return ((TileModular<IModularInventory>)inventory).getModular().getInventoryManager().decrStackSize(this.page, getSlotIndex(), p_75209_1_);
    }

}
