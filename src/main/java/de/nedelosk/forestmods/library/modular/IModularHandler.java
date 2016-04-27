package de.nedelosk.forestmods.library.modular;

import net.minecraft.nbt.NBTTagCompound;

public interface IModularHandler {

	void readFromNBT(NBTTagCompound nbt);

	void writeToNBT(NBTTagCompound nbt);

	IModular getModular();

	void setModular(IModular modular);
}
