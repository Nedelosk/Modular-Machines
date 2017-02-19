package modularmachines.common.modules.storages.items;

import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.Module;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.filters.DefaultFilter;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleChest extends Module {

	public final ItemHandlerModule itemHandler;
	
	public ModuleChest(IModuleStorage storage) {
		super(storage);
		itemHandler = new ItemHandlerModule(this);
		for (int j = 0; j < 3; ++j) {
			for (int k = 0; k < 9; ++k) {
				itemHandler.addSlot(true).addFilter(DefaultFilter.INSTANCE);
			}
		}
	}
	
	@Override
	protected void initPages() {
		super.initPages();
		addPage(new PageChest(this));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		itemHandler.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		itemHandler.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public ItemHandlerModule getItemHandler() {
		return itemHandler;
	}

}
