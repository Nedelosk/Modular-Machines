package nedelosk.modularmachines.api.modules.container.inventory;

import nedelosk.modularmachines.api.modular.basic.IModularDefault;
import net.minecraft.nbt.NBTTagCompound;

public interface IInventoryContainer {

	String getCategoryUID();

	void setCategoryUID(String categoryUID);

	void readFromNBT(NBTTagCompound nbt, IModularDefault modular);

	void writeToNBT(NBTTagCompound nbt, IModularDefault modular);
}
