package modularmachines.common.modules.logic;

import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import modularmachines.api.modules.logic.LogicComponent;

public class EnergyComponent extends LogicComponent implements IEnergyStorage {
	
	private final EnergyStorage energyStorage;
	
	public EnergyComponent(int capacity, int maxTransfer) {
		energyStorage = new EnergyStorage(capacity, maxTransfer);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("Energy", CapabilityEnergy.ENERGY.writeNBT(energyStorage, null));
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		CapabilityEnergy.ENERGY.readNBT(energyStorage, null, compound.getTag("Energy"));
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return energyStorage.receiveEnergy(maxReceive, simulate);
	}
	
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return energyStorage.extractEnergy(maxExtract, simulate);
	}
	
	@Override
	public int getEnergyStored() {
		return energyStorage.getEnergyStored();
	}
	
	@Override
	public int getMaxEnergyStored() {
		return energyStorage.getMaxEnergyStored();
	}
	
	@Override
	public boolean canExtract() {
		return energyStorage.canExtract();
	}
	
	@Override
	public boolean canReceive() {
		return energyStorage.canReceive();
	}
	
}
