package modularmachines.common.modules.container.components;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.components.handlers.IEnergyHandlerComponent;
import modularmachines.api.modules.container.ContainerComponent;
import modularmachines.api.modules.events.Events;
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
	
	public final Set<IEnergyHandlerComponent> handlers = new HashSet<>();
	private int maxEnergy;
	private EnergyTransferMode externalMode = EnergyTransferMode.BOTH;
	
	public void setExternalMode(EnergyTransferMode externalMode) {
		this.externalMode = externalMode;
	}
	
	public EnergyTransferMode getExternalMode() {
		return externalMode;
	}
	
	@Override
	public void onModuleRemoved(IModule module) {
		IEnergyHandlerComponent energyHandler = module.getComponent(IEnergyHandlerComponent.class);
		if (energyHandler != null) {
			removeHandler(energyHandler);
		}
	}
	
	@Override
	public void onModuleAdded(IModule module) {
		IEnergyHandlerComponent energyHandler = module.getComponent(IEnergyHandlerComponent.class);
		if (energyHandler != null) {
			addHandler(energyHandler);
		}
	}
	
	private void addHandler(IEnergyHandlerComponent handler) {
		handlers.add(handler);
		maxEnergy += handler.getMaxEnergyStored();
	}
	
	private void removeHandler(IEnergyHandlerComponent handler) {
		handlers.remove(handler);
		maxEnergy -= handler.getMaxEnergyStored();
	}
	
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int totalExtract = 0;
		for (IEnergyStorage energyStorage : handlers) {
			int extract = energyStorage.extractEnergy(maxExtract, simulate);
			totalExtract += extract;
			maxExtract -= extract;
			if (maxExtract <= 0) {
				break;
			}
		}
		if (!simulate && totalExtract != 0) {
			container.receiveEvent(new Events.EnergyChangeEvent(totalExtract, true));
		}
		return totalExtract;
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int totalReceived = 0;
		for (IEnergyStorage energyStorage : handlers) {
			int receive = energyStorage.receiveEnergy(maxReceive, simulate);
			totalReceived += receive;
			maxReceive -= receive;
			if (maxReceive <= 0) {
				break;
			}
			break;
		}
		if (!simulate && totalReceived != 0) {
			container.receiveEvent(new Events.EnergyChangeEvent(totalReceived, false));
		}
		return totalReceived;
	}
	
	@Override
	public int getEnergyStored() {
		int energyStored = 0;
		for (IEnergyStorage energyStorage : handlers) {
			energyStored += energyStorage.getEnergyStored();
		}
		return energyStored;
	}
	
	@Override
	public int getMaxEnergyStored() {
		return maxEnergy;
	}
	
	@Override
	public boolean canExtract() {
		return !handlers.isEmpty();
	}
	
	@Override
	public boolean canReceive() {
		return !handlers.isEmpty();
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