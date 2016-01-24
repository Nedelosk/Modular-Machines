package nedelosk.modularmachines.api.modular.basic.managers;

import net.minecraft.nbt.NBTTagCompound;

public interface IModularManager {
	
	void readFromNBT(NBTTagCompound nbt);

	void writeToNBT(NBTTagCompound nbt);
	
}
