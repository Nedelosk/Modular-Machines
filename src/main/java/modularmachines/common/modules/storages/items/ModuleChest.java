package modularmachines.common.modules.storages.items;

import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.modules.Module;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.filters.DefaultFilter;

public class ModuleChest extends Module {

	public final ItemHandlerModule itemHandler;
	
	public ModuleChest() {
		itemHandler = new ItemHandlerModule(this);
		for (int j = 0; j < 3; ++j) {
			for (int k = 0; k < 9; ++k) {
				itemHandler.addSlot(true).addFilter(DefaultFilter.INSTANCE);
			}
		}
	}
	
	@Override
	protected void createComponents() {
		super.createComponents();
		addComponent(new ModuleComponentChest(this));
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
