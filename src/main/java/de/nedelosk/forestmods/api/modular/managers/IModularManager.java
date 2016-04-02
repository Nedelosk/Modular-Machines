package de.nedelosk.forestmods.api.modular.managers;

import de.nedelosk.forestmods.api.modular.IModular;
import net.minecraft.nbt.NBTTagCompound;

public interface IModularManager<M extends IModular> {

	void readFromNBT(NBTTagCompound nbt);

	void writeToNBT(NBTTagCompound nbt);

	M getModular();

	void setModular(M modular);

	void onModularAssembled();
}
