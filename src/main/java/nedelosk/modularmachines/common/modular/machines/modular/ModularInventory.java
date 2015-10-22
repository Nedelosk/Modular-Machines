package nedelosk.modularmachines.common.modular.machines.modular;

import nedelosk.modularmachines.api.modular.machines.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.machines.manager.IModularInventoryManager;
import nedelosk.modularmachines.common.modular.machines.modular.managers.ModularInventoryManager;
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
		return inventoryManager;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) throws Exception{
		super.writeToNBT(nbt);
		NBTTagCompound inventory = new NBTTagCompound();
		if(inventoryManager == null)
			inventoryManager = new ModularInventoryManager(this);
		inventoryManager.writeToNBT(inventory);
		nbt.setTag("InventoryManager", inventory);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) throws Exception{
		super.readFromNBT(nbt);
		if(inventoryManager == null)
			inventoryManager = new ModularInventoryManager(this);
		inventoryManager.readFromNBT(nbt.getCompoundTag("InventoryManager"));
	}
	
}
