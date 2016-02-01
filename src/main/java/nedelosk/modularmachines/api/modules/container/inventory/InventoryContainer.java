package nedelosk.modularmachines.api.modules.container.inventory;

import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleDefault;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.container.module.ISingleModuleContainer;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class InventoryContainer<M extends IModule, S extends IModuleSaver> implements ISingleInventoryContainer<M, S> {

	private IModuleInventory<M, S> inventory;
	private String categoryUID;

	public InventoryContainer(IModuleInventory<M, S> inv, String categoryUID) {
		this.inventory = inv;
		this.categoryUID = categoryUID;
	}

	public InventoryContainer() {
	}

	@Override
	public IModuleInventory<M, S> getInventory() {
		return inventory;
	}

	@Override
	public void setInventory(IModuleInventory<M, S> inv) {
		this.inventory = inv;
	}
	
	@Override
	public String getCategoryUID() {
		return categoryUID;
	}
	
	public void setCategoryUID(String categoryUID) {
		this.categoryUID = categoryUID;
	}

	/* NBT */
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModularInventory modular) {
		nbt.setString("CategoryUID", categoryUID);
		if (inventory != null) {
			nbt.setString("Module", inventory.getModuleUID());
			ISingleModuleContainer moduleStack = modular.getModuleManager().getSingleModule(categoryUID);
			inventory.writeToNBT(nbt, modular, moduleStack.getStack());
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModularInventory modular) {
		categoryUID = nbt.getString("CategoryUID");
		if (nbt.hasKey("Module")) {
			String m = nbt.getString("Module");
			ModuleStack moduleStack = modular.getModuleManager().getSingleModule(categoryUID).getStack();
			inventory = ((IModuleDefault) moduleStack.getModule()).createInventory(moduleStack);
			inventory.readFromNBT(nbt, modular, moduleStack);
		}
	}
}
