package nedelosk.modularmachines.api.modular.basic.container.inventory;

import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.basic.container.module.ISingleModuleContainer;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleDefault;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class InventoryContainer<M extends IModule, S extends IModuleSaver> implements ISingleInventoryContainer<M, S> {

	private IModuleInventory<M, S> inventory;

	public InventoryContainer(IModuleInventory<M, S> inv) {
		this.inventory = inv;
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

	/* NBT */
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModularInventory modular, ModuleStack stack) throws Exception {
		if (inventory != null) {
			nbt.setString("CategoryUID", inventory.getCategoryUID());
			nbt.setString("Module", inventory.getModuleUID());
			ISingleModuleContainer moduleStack = modular.getSingleModule(inventory.getCategoryUID());
			inventory.writeToNBT(nbt, modular, moduleStack.getStack());
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModularInventory modular, ModuleStack stack) throws Exception {
		if (nbt.hasKey("Module")) {
			String c = nbt.getString("CategoryUID");
			String m = nbt.getString("Module");
			ModuleStack moduleStack = modular.getSingleModule(c).getStack();
			inventory = ((IModuleDefault) moduleStack.getModule()).createInventory(moduleStack);
			inventory.readFromNBT(nbt, modular, moduleStack);
		}
	}
}
