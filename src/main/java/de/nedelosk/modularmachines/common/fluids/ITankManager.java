package de.nedelosk.modularmachines.common.fluids;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.IFluidHandler;

public interface ITankManager extends IFluidHandler {

	FluidTankSimple[] getTanks();

	FluidTankSimple getTank(int position);

	void readFromNBT(NBTTagCompound nbt);

	void writeToNBT(NBTTagCompound nbt);
}
