package nedelosk.modularmachines.api.modular.module.basic.factory;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleFactory {

	<M> M createModule(String moduleName);
	
	<M> M createModule(String name, NBTTagCompound nbt, IModular modular);
	
}
