package nedelosk.nedeloskcore.api;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTTagable {

	public void readFromNBT(NBTTagCompound nbt);

	public void writeToNBT(NBTTagCompound nbt);
	
}
