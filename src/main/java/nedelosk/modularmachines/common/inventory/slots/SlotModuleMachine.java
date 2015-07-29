package nedelosk.modularmachines.common.inventory.slots;

import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import nedelosk.modularmachines.common.blocks.tile.TileModularMachine;
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
		return ((TileModularMachine)inventory).getStackInSlot(page, this.getSlotIndex());
	}
	
	@Override
    public void putStack(ItemStack p_75215_1_)
    {
		((TileModularMachine)inventory).setInventorySlotContents(this.page, getSlotIndex(), p_75215_1_);
        this.onSlotChanged();
    }
	
	@Override
    public ItemStack decrStackSize(int p_75209_1_)
    {
        return ((TileModularMachine)inventory).decrStackSize(this.page, getSlotIndex(), p_75209_1_);
    }

}
