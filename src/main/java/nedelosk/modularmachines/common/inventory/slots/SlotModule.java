package nedelosk.modularmachines.common.inventory.slots;

import nedelosk.modularmachines.api.IModularAssembler;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.module.ModuleEntry;
import nedelosk.modularmachines.api.techtree.TechTreeManager;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotModule extends Slot {
	
	public ModuleEntry entry;
	public EntityPlayer player;
	
	public SlotModule(IInventory inventory, int ID, int x, int y, ModuleEntry entry, InventoryPlayer inventoryPlayer) {
		super(inventory, ID, x, y);
		this.entry = entry;
		this.player = inventoryPlayer.player;
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
		int tier = entry.parent == null ? 3 : entry.parent.getTier((IModularAssembler)inventory);
		for(int i = 0;i < entry.moduleNames.length;i++)
		{
			if(entry.activatedModuleNames[i])
			{
				String moduleName = entry.moduleNames[i];
				if(ModularMachinesApi.getModuleItem(moduleName, stack) != null)
					if(ModularMachinesApi.getModuleItem(moduleName, stack).getTier() <= tier)
						if(ModularMachinesApi.getModuleItem(moduleName, stack).module.getTechTreeKeys() == null && ModularMachinesApi.getModuleItem(moduleName, stack).module.getTechTreeKeys(ModularMachinesApi.getModuleItem(moduleName, stack).getTier()) == null || (ModularMachinesApi.getModuleItem(moduleName, stack).module.getTechTreeKeys() != null && TechTreeManager.isEntryComplete(player, ModularMachinesApi.getModuleItem(moduleName, stack).module.getTechTreeKeys())) || (ModularMachinesApi.getModuleItem(moduleName, stack).module.getTechTreeKeys(ModularMachinesApi.getModuleItem(moduleName, stack).getTier()) != null && TechTreeManager.isEntryComplete(player, ModularMachinesApi.getModuleItem(moduleName, stack).getModule().getTechTreeKeys(ModularMachinesApi.getModuleItem(moduleName, stack).getTier())[ModularMachinesApi.getModuleItem(moduleName, stack).getTier()])))
							return true;
			}
		}
		return false;
	}	

}
