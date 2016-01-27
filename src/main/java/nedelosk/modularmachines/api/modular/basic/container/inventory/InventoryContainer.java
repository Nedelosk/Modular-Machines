package nedelosk.modularmachines.api.modular.basic.container.inventory;

import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public class InventoryContainer<M extends IModule> implements ISingleInventoryContainer<M> {

	private IModuleInventory<M> inventory;

	public InventoryContainer(IModuleInventory<M> inv) {
		this.inventory = inv;
	}

	public InventoryContainer() {
	}

	@Override
	public IModuleInventory<M> getInventory() {
		return inventory;
	}

	@Override
	public void setInventory(IModuleInventory<M> inv) {
		this.inventory = inv;
	}

	/* NBT */
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModularInventory modular, ModuleStack stack) throws Exception {
		if (inventory != null) {
			inventory.writeToNBT(nbt, modular, stack);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModularInventory modular, ModuleStack stack) throws Exception {
		inventory = stack.getModule().getInventory(stack);
		inventory.readFromNBT(nbt, modular, stack);
	}
}
