package de.nedelosk.forestmods.api.transport;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public interface IPartSide {

	ForgeDirection getSide();

	boolean isActive();

	void setActive();

	void setUnactive();

	ITransportPart getPart();

	void writeToNBT(NBTTagCompound compound);

	void readFromNBT(NBTTagCompound compound);
}
