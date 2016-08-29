package de.nedelosk.modularmachines.api.energy;

import net.minecraft.util.EnumFacing;

public interface IEnergyBuffer {

	long extractEnergy(EnumFacing facing, long maxExtract, boolean simulate);

	long receiveEnergy(EnumFacing facing, long maxReceive, boolean simulate);

	void setEnergy(long energy);

	long getEnergyStored();

	long getCapacity();

	int getTier();
}
