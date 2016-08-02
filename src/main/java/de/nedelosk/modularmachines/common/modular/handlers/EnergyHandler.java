package de.nedelosk.modularmachines.common.modular.handlers;

import java.util.ArrayList;
import java.util.List;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.nedelosk.modularmachines.api.energy.EnergyRegistry;
import de.nedelosk.modularmachines.api.energy.IEnergyInterface;
import de.nedelosk.modularmachines.api.energy.IEnergyType;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.Optional;

@Optional.InterfaceList({
	@Optional.Interface(iface = "net.darkhax.tesla.api.ITeslaConsumer", modid = "tesla"),
	@Optional.Interface(iface = "net.darkhax.tesla.api.ITeslaProducer", modid = "tesla"),
	@Optional.Interface(iface = "net.darkhax.tesla.api.ITeslaHolder", modid = "tesla"),
	@Optional.Interface(iface = "cofh.api.energy.IEnergyReceiver", modid = "CoFHLib"),
	@Optional.Interface(iface = "cofh.api.energy.IEnergyProvider", modid = "CoFHLib")
})
public class EnergyHandler implements ICapabilityProvider, IEnergyInterface, IEnergyProvider, IEnergyReceiver, ITeslaConsumer, ITeslaHolder, ITeslaProducer {

	@CapabilityInject(ITeslaConsumer.class)
	public static Capability<ITeslaConsumer> TESLA_CONSUMER = null;
	@CapabilityInject(ITeslaProducer.class)
	public static Capability<ITeslaProducer> TESLA_PRODUCER = null;
	@CapabilityInject(ITeslaHolder.class)
	public static Capability<ITeslaHolder> TESLA_HOLDER = null;

	public List<IEnergyInterface> interfaces;

	public EnergyHandler(List<IEnergyInterface> interfaces) {
		this.interfaces = interfaces;
	}

	@Override
	public long extractEnergy(IEnergyType energyType, long maxExtract, boolean simulate) {
		long totalExtract = 0;
		STATES: for(IEnergyInterface energyInterface : interfaces){
			for(IEnergyType type : energyInterface.getValidTypes()){
				if(type == EnergyRegistry.redstoneFlux){
					long extract = energyInterface.extractEnergy(type, maxExtract, true);
					if(extract > 0){
						energyInterface.extractEnergy(type, maxExtract, false);
						totalExtract+=extract;
						maxExtract-=extract;
						if(maxExtract <= 0){
							break STATES;
						}
					}
				}
			}
		}
		return totalExtract;
	}

	@Override
	public long receiveEnergy(IEnergyType energyType, long maxReceive, boolean simulate) {
		long totalReceived = 0;
		STATES: for(IEnergyInterface energyInterface : interfaces){
			for(IEnergyType type : energyInterface.getValidTypes()){
				if(type == EnergyRegistry.redstoneFlux){
					long receive = energyInterface.receiveEnergy(type, maxReceive, true);
					if(receive > 0){
						energyInterface.receiveEnergy(type, maxReceive, false);
						totalReceived+=receive;
						maxReceive-=receive;
						if(maxReceive <= 0){
							break STATES;
						}
						break;
					}
				}
			}
		}
		return totalReceived;
	}

	@Override
	public long getEnergyStored(IEnergyType energyType) {
		long energyStored = 0;
		for(IEnergyInterface energyInterface : interfaces){
			for(IEnergyType type : energyInterface.getValidTypes()){
				if(type == energyType){
					energyStored +=energyInterface.getEnergyStored(energyType);
				}
			}
		}
		return energyStored;
	}

	@Override
	public long getCapacity(IEnergyType energyType) {
		long capacity = 0;
		for(IEnergyInterface energyInterface : interfaces){
			for(IEnergyType type : energyInterface.getValidTypes()){
				if(type == energyType){
					capacity +=energyInterface.getCapacity(energyType);
				}
			}
		}
		return capacity;
	}

	@Override
	public boolean canConnectEnergy(IEnergyType energyType) {
		for(IEnergyInterface energyInterface : interfaces){
			for(IEnergyType type : energyInterface.getValidTypes()){
				if(type == energyType){
					if(energyInterface.canConnectEnergy(energyType)){
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean isInput(IEnergyType energyType) {
		for(IEnergyInterface energyInterface : interfaces){
			for(IEnergyType type : energyInterface.getValidTypes()){
				if(type == energyType){
					if(energyInterface.isInput(energyType)){
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean isOutput(IEnergyType energyType) {
		for(IEnergyInterface energyInterface : interfaces){
			for(IEnergyType type : energyInterface.getValidTypes()){
				if(type == energyType){
					if(energyInterface.isOutput(energyType)){
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public IEnergyType[] getValidTypes() {		
		List<IEnergyType> types = new ArrayList<>();
		for(IEnergyInterface energyInterface : interfaces){
			for(IEnergyType type : energyInterface.getValidTypes()){
				if(!types.contains(type)){
					types.add(type);
				}
			}
		}
		return types.toArray(new IEnergyType[types.size()]);
	}

	@Override
	public EnumFacing getFacing() {
		return null;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == TESLA_CONSUMER){
			return true;
		}else if(capability == TESLA_PRODUCER){
			return true;
		}else if(capability == TESLA_HOLDER){
			return true;
		}
		return false;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == TESLA_CONSUMER){
			return TESLA_CONSUMER.cast(this);
		}else if(capability == TESLA_PRODUCER){
			return TESLA_PRODUCER.cast(this);
		}else if(capability == TESLA_HOLDER){
			return TESLA_HOLDER.cast(this);
		}
		return null;
	}

	@Optional.Method(modid = "tesla")
	@Override
	public long takePower(long power, boolean simulated) {
		return extractEnergy(EnergyRegistry.redstoneFlux, power, simulated);
	}

	@Optional.Method(modid = "tesla")
	@Override
	public long getStoredPower() {
		return getEnergyStored(EnergyRegistry.redstoneFlux);
	}

	@Optional.Method(modid = "tesla")
	@Override
	public long getCapacity() {
		return getCapacity(EnergyRegistry.redstoneFlux);
	}

	@Optional.Method(modid = "tesla")
	@Override
	public long givePower(long power, boolean simulated) {
		return receiveEnergy(EnergyRegistry.redstoneFlux, power, simulated);
	}

	@Optional.Method(modid = "CoFHLib")
	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return canConnectEnergy(EnergyRegistry.redstoneFlux);
	}

	@Optional.Method(modid = "CoFHLib")
	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		return (int) receiveEnergy(EnergyRegistry.redstoneFlux, maxReceive, simulate);
	}

	@Optional.Method(modid = "CoFHLib")
	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		return (int) extractEnergy(EnergyRegistry.redstoneFlux, maxExtract, simulate);
	}

	@Optional.Method(modid = "CoFHLib")
	@Override
	public int getEnergyStored(EnumFacing from) {
		return (int) getEnergyStored(EnergyRegistry.redstoneFlux);
	}

	@Optional.Method(modid = "CoFHLib")
	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return (int) getCapacity(EnergyRegistry.redstoneFlux);
	}
}
