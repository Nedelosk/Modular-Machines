package de.nedelosk.modularmachines.api.energy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;

public interface IEnergyInterface {

	long extractEnergy(IEnergyType energyType, long maxExtract, boolean simulate);

	long receiveEnergy(IEnergyType energyType, long maxReceive, boolean simulate);

	long getEnergyStored(IEnergyType energyType);

	long getCapacity(IEnergyType energyType);

	boolean canConnectEnergy(IEnergyType energyType);

	boolean isInput(IEnergyType energyType);

	boolean isOutput(IEnergyType energyType);

	@Nonnull
	IEnergyType[] getValidTypes();

	@Nullable
	EnumFacing getFacing();
}
