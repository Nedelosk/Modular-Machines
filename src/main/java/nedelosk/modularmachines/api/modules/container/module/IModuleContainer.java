package nedelosk.modularmachines.api.modules.container.module;

import nedelosk.modularmachines.api.modular.IModular;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleContainer {

	String getCategoryUID();

	void setCategoryUID(String categoryUID);

	void readFromNBT(NBTTagCompound nbt, IModular modular);

	void writeToNBT(NBTTagCompound nbt, IModular modular);
}
