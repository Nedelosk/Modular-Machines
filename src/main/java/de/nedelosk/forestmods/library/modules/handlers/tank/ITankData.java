package de.nedelosk.forestmods.library.modules.handlers.tank;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidTank;

public interface ITankData<T extends IFluidTank> {

	void setTank(T tank);

	T getTank();

	void setDirection(ForgeDirection direction);

	ForgeDirection getDirection();

	EnumTankMode getMode();

	void writeToNBT(NBTTagCompound nbt);

	void readFromNBT(NBTTagCompound nbt);
}
