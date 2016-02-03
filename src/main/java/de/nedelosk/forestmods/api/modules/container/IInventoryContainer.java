package de.nedelosk.forestmods.api.modules.container;

import de.nedelosk.forestmods.api.modular.basic.IModularDefault;
import net.minecraft.nbt.NBTTagCompound;

public interface IInventoryContainer {

	String getCategoryUID();

	void setCategoryUID(String categoryUID);

	void readFromNBT(NBTTagCompound nbt, IModularDefault modular);

	void writeToNBT(NBTTagCompound nbt, IModularDefault modular);
}
