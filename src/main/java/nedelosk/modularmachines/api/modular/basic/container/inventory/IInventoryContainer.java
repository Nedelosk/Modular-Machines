package nedelosk.modularmachines.api.modular.basic.container.inventory;

import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IInventoryContainer {

	void readFromNBT(NBTTagCompound nbt, IModularInventory modular, ModuleStack stack) throws Exception;

	void writeToNBT(NBTTagCompound nbt, IModularInventory modular, ModuleStack stack) throws Exception;
}
