package nedelosk.modularmachines.common.modular.handler;

import nedelosk.nedeloskcore.api.INBTTagable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

public class EnergyHandler implements IEnergyHandler, INBTTagable {

	public EnergyStorage storage;
	
	public EnergyHandler(EnergyStorage storage) {
		this.storage = storage;
	}
	
	public EnergyHandler(NBTTagCompound nbt) {
		readFromNBT(nbt);
	}
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return storage != null;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagCompound nbtTag = nbt.getCompoundTag("EnergyManager");
		storage = new EnergyStorage(nbtTag.getInteger("Capacity"));
		storage.readFromNBT(nbtTag);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagCompound nbtTag = new NBTTagCompound();
		storage.writeToNBT(nbtTag);
		nbtTag.setInteger("Capacity", storage.getMaxEnergyStored());
		nbt.setTag("EnergyManager", nbtTag);
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return storage.getMaxEnergyStored();
	}
	
	public EnergyStorage getStorage() {
		return storage;
	}

}
