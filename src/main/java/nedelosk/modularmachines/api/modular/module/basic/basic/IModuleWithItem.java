package nedelosk.modularmachines.api.modular.module.basic.basic;

import nedelosk.modularmachines.api.modular.module.basic.IModule;
import net.minecraft.nbt.NBTTagCompound;

public interface IModuleWithItem extends IModule {

	void writeToItemNBT(NBTTagCompound nbt, int tier);
	
}
