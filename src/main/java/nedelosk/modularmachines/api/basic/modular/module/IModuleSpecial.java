package nedelosk.modularmachines.api.basic.modular.module;

import net.minecraft.nbt.NBTTagCompound;

public interface IModuleSpecial extends IModule {

	void writeToItemNBT(NBTTagCompound nbt, int tier);
	
}
