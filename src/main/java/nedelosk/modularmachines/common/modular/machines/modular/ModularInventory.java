package nedelosk.modularmachines.common.modular.machines.modular;

import nedelosk.modularmachines.api.modular.machines.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.machines.manager.IModularInventoryManager;
import nedelosk.modularmachines.common.modular.machines.modular.managers.ModularInventoryManager;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ModularInventory extends Modular implements IModularInventory {

	public ModularInventory() {
	}
	
	public ModularInventory(NBTTagCompound nbt) {
		super(nbt);
		inventoryManager = new ModularInventoryManager(this);
	}
	
	protected IModularInventoryManager inventoryManager;
	
	@Override
	public IModularInventoryManager getInventoryManager() {
		return inventoryManager;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagCompound inventory = new NBTTagCompound();
		inventoryManager.writeToNBT(nbt);
		nbt.setTag("InventoryManager", inventory);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		inventoryManager.readFromNBT(nbt.getCompoundTag("InventoryManager"));
	}

}
