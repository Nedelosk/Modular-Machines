package modularmachines.common.modules.storages.items;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.pages.PageComponent;
import modularmachines.common.containers.SlotModule;
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
	public void createComponents() {
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
	
	private class ModuleComponentChest extends PageComponent<ModuleChest> {
		
		public ModuleComponentChest(ModuleChest parent) {
			super(parent);
		}
		
		@Override
		public void createSlots(List slots) {
			ItemHandlerModule itemHandler = parent.getItemHandler();
			for (int j = 0; j < 3; ++j) {
				for (int i = 0; i < 9; ++i) {
					slots.add(new SlotModule(itemHandler, i + j * 9, 8 + i * 18, 18 + j * 18));
				}
			}
		}
	}
	
}
