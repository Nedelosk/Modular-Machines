package modularmachines.common.modules.container.components;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import modularmachines.api.modules.container.ContainerComponent;
import modularmachines.common.energy.EnergyStorageWrapper;
import modularmachines.common.energy.EnergyTransferMode;
import modularmachines.common.energy.tesla.TeslaConsumerWrapper;
import modularmachines.common.energy.tesla.TeslaHelper;
import modularmachines.common.energy.tesla.TeslaHolderWrapper;
import modularmachines.common.energy.tesla.TeslaProducerWrapper;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;

public class EnergyManager extends ContainerComponent implements IEnergyStorage {
	
	public final List<IEnergyStorage> energyStorages;
	private EnergyTransferMode externalMode = EnergyTransferMode.BOTH;
	
	public EnergyManager() {
		this.energyStorages = new ArrayList<>();
	}
	
	public void setExternalMode(EnergyTransferMode externalMode) {
		this.externalMode = externalMode;
	}
	
	public EnergyTransferMode getExternalMode() {
		return externalMode;
	}
	
	public void addStorage(IEnergyStorage storage) {
		if (!energyStorages.contains(storage)) {
			energyStorages.add(storage);
		}
	}
	
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int totalExtract = 0;
		for (IEnergyStorage energyStorage : energyStorages) {
			int extract = energyStorage.extractEnergy(maxExtract, simulate);
			totalExtract += extract;
			maxExtract -= extract;
			if (maxExtract <= 0) {
				break;
			}
		}
		return totalExtract;
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int totalReceived = 0;
		for (IEnergyStorage energyStorage : energyStorages) {
			int receive = energyStorage.receiveEnergy(maxReceive, simulate);
			totalReceived += receive;
			maxReceive -= receive;
			if (maxReceive <= 0) {
				break;
			}
			break;
		}
		return totalReceived;
	}
	
	@Override
	public int getEnergyStored() {
		int energyStored = 0;
		for (IEnergyStorage energyStorage : energyStorages) {
			energyStored += energyStorage.getEnergyStored();
		}
		return energyStored;
	}
	
	@Override
	public int getMaxEnergyStored() {
		int capacity = 0;
		for (IEnergyStorage energyStorage : energyStorages) {
			capacity += energyStorage.getMaxEnergyStored();
		}
		return capacity;
	}
	
	@Override
	public boolean canExtract() {
		return !energyStorages.isEmpty();
	}
	
	@Override
	public boolean canReceive() {
		return !energyStorages.isEmpty();
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY ||
				capability == TeslaHelper.TESLA_PRODUCER && externalMode.canExtract() ||
				capability == TeslaHelper.TESLA_CONSUMER && externalMode.canReceive() ||
				capability == TeslaHelper.TESLA_HOLDER;
	}
	
	@Override
	@Nullable
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			IEnergyStorage energyStorage = new EnergyStorageWrapper(this, externalMode);
			return CapabilityEnergy.ENERGY.cast(energyStorage);
		} else {
			Capability<ITeslaProducer> teslaProducer = TeslaHelper.TESLA_PRODUCER;
			Capability<ITeslaConsumer> teslaConsumer = TeslaHelper.TESLA_CONSUMER;
			Capability<ITeslaHolder> teslaHolder = TeslaHelper.TESLA_HOLDER;
			
			if (capability == teslaProducer && externalMode.canExtract()) {
				return teslaProducer.cast(new TeslaProducerWrapper(this));
			} else if (capability == teslaConsumer && externalMode.canReceive()) {
				return teslaConsumer.cast(new TeslaConsumerWrapper(this));
			} else if (capability == teslaHolder) {
				return teslaHolder.cast(new TeslaHolderWrapper(this));
			} else {
				return null;
			}
		}
	}
}