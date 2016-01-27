package nedelosk.modularmachines.api.modular.basic;

import nedelosk.modularmachines.api.modular.Modular;
import nedelosk.modularmachines.api.modular.basic.managers.IModularInventoryManager;
import nedelosk.modularmachines.api.modular.basic.managers.ModularInventoryManager;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ModularInventory extends Modular implements IModularInventory {

	public ModularInventory() {
		inventoryManager = new ModularInventoryManager(this);
	}

	public ModularInventory(NBTTagCompound nbt) {
		super(nbt);
	}

	protected IModularInventoryManager inventoryManager;

	@Override
	public IModularInventoryManager getInventoryManager() {
		if (inventoryManager == null) {
			inventoryManager = new ModularInventoryManager(this);
		}
		return inventoryManager;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) throws Exception {
		super.writeToNBT(nbt);
		NBTTagCompound inventory = new NBTTagCompound();
		getInventoryManager().writeToNBT(inventory);
		nbt.setTag("InventoryManager", inventory);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) throws Exception {
		super.readFromNBT(nbt);
		if (inventoryManager == null) {
			inventoryManager = new ModularInventoryManager(this);
		}
		inventoryManager.readFromNBT(nbt.getCompoundTag("InventoryManager"));
	}
}
