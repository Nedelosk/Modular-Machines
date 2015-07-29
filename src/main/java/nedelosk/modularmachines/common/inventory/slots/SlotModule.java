package nedelosk.modularmachines.common.inventory.slots;

import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.module.ModuleEntry;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotModule extends Slot {
	
	public ModuleEntry entry;
	
	public SlotModule(IInventory inventory, int ID, int x, int y, ModuleEntry entry) {
		super(inventory, ID, x, y);
		this.entry = entry;
	}
	
	@Override
	public ItemStack getStack() {
		return ((TileModularAssembler)inventory).getStackInSlot(entry.page, entry.ID);
	}
	
	@Override
    public void putStack(ItemStack p_75215_1_)
    {
		((TileModularAssembler)inventory).setInventorySlotContents(entry.page, entry.ID, p_75215_1_);
        this.onSlotChanged();
    }
	
	@Override
    public ItemStack decrStackSize(int p_75209_1_)
    {
        return ((TileModularAssembler)inventory).decrStackSize(entry.page, entry.ID, p_75209_1_);
    }
	
	@Override
	public int getSlotStackLimit() {
		return 1;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		int tier = entry.parent == null ? 3 : entry.parent.getTier();
		for(String moduleName : entry.moduleNames)
		{
			if(ModularMachinesApi.getModuleItem(moduleName, stack) != null && ModularMachinesApi.getModuleItem(moduleName, stack).getTier() <= tier)
				return true;
		}
		return false;
	}	

}
