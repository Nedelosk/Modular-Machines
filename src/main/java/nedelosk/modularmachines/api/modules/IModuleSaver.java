package nedelosk.modularmachines.api.modules;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleSaver {

	void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception;

	void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception;
}
