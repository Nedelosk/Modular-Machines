package nedelosk.modularmachines.api.basic.machine.module;

import net.minecraft.nbt.NBTTagCompound;

public interface IModuleSpecial extends IModule {

	void writeToItemNBT(NBTTagCompound nbt, int tier);
	
}
