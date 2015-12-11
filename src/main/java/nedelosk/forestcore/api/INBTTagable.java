package nedelosk.forestcore.api;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTTagable {

	void readFromNBT(NBTTagCompound nbt);

	void writeToNBT(NBTTagCompound nbt);

}