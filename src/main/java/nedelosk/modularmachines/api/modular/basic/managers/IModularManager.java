package nedelosk.modularmachines.api.modular.basic.managers;

import nedelosk.modularmachines.api.modular.IModular;
import net.minecraft.nbt.NBTTagCompound;

public interface IModularManager<M extends IModular> {

	void readFromNBT(NBTTagCompound nbt);

	void writeToNBT(NBTTagCompound nbt);

	M getModular();

	void setModular(M modular);
}
