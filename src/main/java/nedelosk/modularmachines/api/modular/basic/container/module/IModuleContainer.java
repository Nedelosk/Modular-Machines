package nedelosk.modularmachines.api.modular.basic.container.module;

import nedelosk.modularmachines.api.modular.IModular;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleContainer {

	void readFromNBT(NBTTagCompound nbt, IModular modular);

	void writeToNBT(NBTTagCompound nbt, IModular modular);
}
