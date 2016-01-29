package nedelosk.modularmachines.api.modular.basic;

import nedelosk.modularmachines.api.modular.Modular;
import nedelosk.modularmachines.api.modular.basic.managers.IModularGuiManager;
import nedelosk.modularmachines.api.modular.basic.managers.IModularInventoryManager;
import nedelosk.modularmachines.api.modular.basic.managers.ModularGuiManager;
import nedelosk.modularmachines.api.modular.basic.managers.ModularInventoryManager;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ModularInventory extends Modular implements IModularInventory {

	protected IModularGuiManager guiManager;
	protected IModularInventoryManager inventoryManager;

	public ModularInventory() {
		inventoryManager = new ModularInventoryManager();
		inventoryManager.setModular(this);
		guiManager = new ModularGuiManager();
		guiManager.setModular(this);
	}

	public ModularInventory(NBTTagCompound nbt) {
		super(nbt);
	}

	@Override
	public IModularInventoryManager getInventoryManager() {
		if (inventoryManager == null) {
			inventoryManager = new ModularInventoryManager();
			inventoryManager.setModular(this);
		}
		return inventoryManager;
	}

	@Override
	public IModularGuiManager getGuiManager() {
		if (guiManager == null) {
			guiManager = new ModularGuiManager();
			guiManager.setModular(this);
		}
		return guiManager;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagCompound inventory = new NBTTagCompound();
		getInventoryManager().writeToNBT(inventory);
		nbt.setTag("InventoryManager", inventory);
		NBTTagCompound gui = new NBTTagCompound();
		getGuiManager().writeToNBT(nbt);
		nbt.setTag("GuiManager", gui);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		getInventoryManager().readFromNBT(nbt.getCompoundTag("InventoryManager"));
		getInventoryManager().setModular(this);
		getGuiManager().readFromNBT(nbt.getCompoundTag("GuiManager"));
		getGuiManager().setModular(this);
	}
}
