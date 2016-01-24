package nedelosk.modularmachines.api.modular.basic;

import nedelosk.modularmachines.api.modular.IModular;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleContainer<O extends Object> {
	
	void readFromNBT(NBTTagCompound nbt, IModular modular) throws Exception;

	void writeToNBT(NBTTagCompound nbt, IModular modular) throws Exception;
	
	O getModuleStack();
	
}
