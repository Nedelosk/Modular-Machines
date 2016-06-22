package de.nedelosk.modularmachines.api.transport;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public interface IPartSide {

	EnumFacing getSide();

	boolean isActive();

	void setActive();

	void setUnactive();

	ITransportPart getPart();

	void writeToNBT(NBTTagCompound compound);

	void readFromNBT(NBTTagCompound compound);
}
