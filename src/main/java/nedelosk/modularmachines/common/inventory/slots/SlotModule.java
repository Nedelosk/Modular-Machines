package nedelosk.modularmachines.common.inventory.slots;

import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssenbler;
import nedelosk.nedeloskcore.utils.ItemUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotModule extends Slot {

	public String moduleName;
	public String moduleName2;
	public boolean active;
	public int tier;
	public String page;
	
	@Override
	public ItemStack getStack() {
		return ((TileModularAssenbler)inventory).getStackInSlot(page, this.getSlotIndex());
	}
	
	@Override
    public void putStack(ItemStack p_75215_1_)
    {
		((TileModularAssenbler)inventory).setInventorySlotContents(this.page, getSlotIndex(), p_75215_1_);
        this.onSlotChanged();
    }
	
	@Override
    public ItemStack decrStackSize(int p_75209_1_)
    {
        return ((TileModularAssenbler)inventory).decrStackSize(this.page, getSlotIndex(), p_75209_1_);
    }
	
	public SlotModule(IInventory inventory, int ID, int x, int y, String moduleName, int tier, String page) {
		super(inventory, ID, x, y);
		this.moduleName = moduleName;
		this.active = false;
		this.tier = tier;
		this.page = page;
	}
	
	public SlotModule(IInventory inventory, int ID, int x, int y, String moduleName, boolean active, int tier, String page) {
		super(inventory, ID, x, y);
		this.moduleName = moduleName;
		this.active = active;
		this.tier = tier;
		this.page = page;
	}
	
	public SlotModule(IInventory inventory, int ID, int x, int y, String moduleName, String moduleName2, int tier, String page) {
		super(inventory, ID, x, y);
		this.moduleName = moduleName;
		this.moduleName2 = moduleName2;
		this.active = false;
		this.tier = tier;
		this.page = page;
	}
	
	@Override
	public int getSlotStackLimit() {
		return 1;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		if(ModularMachinesApi.getModuleItem(moduleName, stack) != null && ModularMachinesApi.getModuleItem(moduleName, stack).getTier() <= tier)
			return true;
		else if(moduleName2 != null && ModularMachinesApi.getModuleItem(moduleName2, stack) != null && ModularMachinesApi.getModuleItem(moduleName2, stack).getTier() <= tier)
			return true;
		return false;
	}
	
	

}
