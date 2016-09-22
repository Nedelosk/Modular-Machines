package de.nedelosk.modularmachines.api.energy;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.util.EnumFacing;

public interface IEnergyBuffer {

	long extractEnergy(IModuleState state, EnumFacing facing, long maxExtract, boolean simulate);

	long receiveEnergy(IModuleState state, EnumFacing facing, long maxReceive, boolean simulate);

	void setEnergy(long energy);

	long getEnergyStored();

	long getCapacity();

	int getTier();

	boolean canExtract();

	boolean canReceive();
}
