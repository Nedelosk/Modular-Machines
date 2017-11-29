package modularmachines.common.modules.components.handlers;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

import modularmachines.api.IOMode;
import modularmachines.api.modules.components.handlers.IEnergyHandlerComponent;
import modularmachines.api.modules.components.handlers.IIOComponent;
import modularmachines.common.modules.components.ModuleComponent;

public class EnergyHandlerComponent extends ModuleComponent implements IEnergyHandlerComponent {
	private final EnergyStorage energyStorage;
	
	public EnergyHandlerComponent(int capacity) {
		energyStorage = new EnergyStorage(capacity);
	}
	
	public EnergyHandlerComponent(int capacity, int maxTransfer) {
		energyStorage = new EnergyStorage(capacity, maxTransfer);
	}
	
	public EnergyHandlerComponent(int capacity, int maxReceive, int maxExtract) {
		energyStorage = new EnergyStorage(capacity, maxReceive, maxExtract);
	}
	
	@Override
	public boolean supportsMode(IOMode ioMode, @Nullable EnumFacing facing) {
		IIOComponent ioComponent = provider.getComponent(IIOComponent.class);
		if (ioComponent == null) {
			return true;
		}
		return ioComponent.supportsMode(ioMode, facing);
	}
	
	@Override
	public IOMode getMode(@Nullable EnumFacing facing) {
		IIOComponent ioComponent = provider.getComponent(IIOComponent.class);
		if (ioComponent == null) {
			return IOMode.NONE;
		}
		return ioComponent.getMode(facing);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if (compound.hasKey("Energy")) {
			CapabilityEnergy.ENERGY.readNBT(energyStorage, null, compound.getTag("Energy"));
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTBase energyTag = CapabilityEnergy.ENERGY.writeNBT(energyStorage, null);
		if (energyTag != null) {
			compound.setTag("Energy", energyTag);
		}
		return compound;
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
