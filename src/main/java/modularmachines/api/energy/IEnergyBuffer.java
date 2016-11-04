package modularmachines.api.energy;

import javax.annotation.Nullable;

import modularmachines.api.modules.state.IModuleState;
import net.minecraft.util.EnumFacing;

public interface IEnergyBuffer {

	long extractEnergy(@Nullable IModuleState state, @Nullable EnumFacing facing, long maxExtract, boolean simulate);

	long receiveEnergy(@Nullable IModuleState state, @Nullable EnumFacing facing, long maxReceive, boolean simulate);

	void setEnergy(long energy);

	long getEnergyStored();

	long getCapacity();

	int getTier();

	boolean canExtract();

	boolean canReceive();
}
