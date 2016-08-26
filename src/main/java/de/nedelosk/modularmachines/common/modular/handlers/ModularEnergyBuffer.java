package de.nedelosk.modularmachines.common.modular.handlers;

import java.util.List;

import de.nedelosk.modularmachines.api.energy.IEnergyBuffer;
import de.nedelosk.modularmachines.api.modules.handlers.energy.IModuleEnergyBuffer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.util.EnumFacing;

public class ModularEnergyBuffer implements IEnergyBuffer {

	public List<IModuleEnergyBuffer> buffers;

	public ModularEnergyBuffer(List<IModuleEnergyBuffer> buffers) {
		this.buffers = buffers;
	}

	@Override
	public long extractEnergy(EnumFacing facing, long maxExtract, boolean simulate) {
		long totalExtract = 0;
		for(IModuleEnergyBuffer energyBuffer : buffers){
			long extract = energyBuffer.extractEnergy(facing, maxExtract, simulate);
			IModuleState state = energyBuffer.getModuleState();
			if(state != null){
				state.getModule().sendModuleUpdate(state);
			}
			totalExtract+=extract;
			maxExtract-=extract;
			if(maxExtract <= 0){
				break;
			}
		}
		return totalExtract;
	}

	@Override
	public long receiveEnergy(EnumFacing facing, long maxReceive, boolean simulate) {
		long totalReceived = 0;
		for(IModuleEnergyBuffer energyBuffer : buffers){
			long receive = energyBuffer.receiveEnergy(facing, maxReceive, simulate);
			IModuleState state = energyBuffer.getModuleState();
			if(state != null){
				state.getModule().sendModuleUpdate(state);
			}
			totalReceived+=receive;
			maxReceive-=receive;
			if(maxReceive <= 0){
				break;
			}
			break;
		}
		return totalReceived;
	}

	@Override
	public void setEnergyStored(long energy) {
		for(IEnergyBuffer energyBuffer : buffers){
			long capacity = energyBuffer.getCapacity();
			if(energy > capacity){
				energyBuffer.setEnergyStored(capacity);
				energy-=capacity;
			}else{
				energyBuffer.setEnergyStored(energy);
				break;
			}
			if(energy <= 0){
				break;
			}
		}
	}

	@Override
	public long getEnergyStored() {
		long energyStored = 0;
		for(IEnergyBuffer energyBuffer : buffers){
			energyStored +=energyBuffer.getEnergyStored();
		}
		return energyStored;
	}

	@Override
	public long getCapacity() {
		long capacity = 0;
		for(IEnergyBuffer energyBuffer : buffers){
			capacity +=energyBuffer.getCapacity();
		}
		return capacity;
	}
}