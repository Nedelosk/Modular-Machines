package nedelosk.nedeloskcore.api;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTTagable {

	void readFromNBT(NBTTagCompound nbt);

	void writeToNBT(NBTTagCompound nbt);
	
}
