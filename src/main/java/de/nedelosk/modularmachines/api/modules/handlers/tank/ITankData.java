package de.nedelosk.modularmachines.api.modules.handlers.tank;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.IFluidTank;

public interface ITankData<T extends IFluidTank> {

	void setTank(T tank);

	T getTank();

	void setFacing(EnumFacing direction);

	EnumFacing getFacing();

	EnumTankMode getMode();

	void writeToNBT(NBTTagCompound nbt);

	void readFromNBT(NBTTagCompound nbt);
}
