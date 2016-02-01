package nedelosk.modularmachines.api.modules.container.inventory;

import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IInventoryContainer {

	String getCategoryUID();
	
	void setCategoryUID(String categoryUID);
	
	void readFromNBT(NBTTagCompound nbt, IModularInventory modular);

	void writeToNBT(NBTTagCompound nbt, IModularInventory modular);
}
