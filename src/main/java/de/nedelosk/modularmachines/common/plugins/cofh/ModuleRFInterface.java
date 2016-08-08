package de.nedelosk.modularmachines.common.plugins.cofh;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.modularmachines.api.energy.EnergyRegistry;
import de.nedelosk.modularmachines.api.energy.IEnergyType;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.energy.IModuleEnergyInterface;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.INBTSerializable;

public class ModuleRFInterface<M extends IModule> implements IModuleEnergyInterface, INBTSerializable<NBTTagCompound> {

	protected final IModuleState<M> state;
	protected final EnergyStorage storage;

	public ModuleRFInterface(IModuleState<M> state, EnergyStorage storage) {
		this.state = state;
		this.storage = storage;
	}

	@Override
	public IModuleState getModuleState() {
		return state;
	}

	@Override
	public String getUID() {
		return "EnergyInterface";
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		storage.readFromNBT(nbt);
	}
	
	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		storage.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public long extractEnergy(IEnergyType energyType, long maxExtract, boolean simulate) {
		return storage.extractEnergy((int)maxExtract, simulate);
	}

	@Override
	public long receiveEnergy(IEnergyType energyType, long maxReceive, boolean simulate) {
		return storage.receiveEnergy((int)maxReceive, simulate);
	}

	@Override
	public long getEnergyStored(IEnergyType energyType) {
		return storage.getEnergyStored();
	}

	@Override
	public long getCapacity(IEnergyType energyType) {
		return storage.getMaxEnergyStored();
	}

	@Override
	public boolean canConnectEnergy(IEnergyType energyType) {
		return true;
	}

	@Override
	public boolean isInput(IEnergyType energyType) {
		return true;
	}

	@Override
	public boolean isOutput(IEnergyType energyType) {
		return true;
	}

	@Override
	public IEnergyType[] getValidTypes() {
		return new IEnergyType[]{EnergyRegistry.redstoneFlux};
	}

	@Override
	public EnumFacing getFacing() {
		return null;
	}
}
