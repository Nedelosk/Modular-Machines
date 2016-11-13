package modularmachines.common.modular;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.EnumFacing;

import modularmachines.api.energy.IEnergyBuffer;
import modularmachines.api.modules.controller.IModuleControl;
import modularmachines.api.modules.controller.IModuleControlled;
import modularmachines.api.modules.handlers.IModuleContentHandler;
import modularmachines.api.modules.state.IModuleState;

public class ModularEnergyBuffer<E extends IEnergyBuffer & IModuleContentHandler> implements IEnergyBuffer {

	public final List<E> buffers;

	public ModularEnergyBuffer(List<E> buffers) {
		this.buffers = buffers;
	}

	@Override
	public long extractEnergy(IModuleState moduleState, EnumFacing facing, long maxExtract, boolean simulate) {
		List<E> buffers = new ArrayList<>(this.buffers);
		if (moduleState != null && moduleState.getModule() instanceof IModuleControlled) {
			buffers.clear();
			IModuleControl control = ((IModuleControlled) moduleState.getModule()).getModuleControl(moduleState);
			for (E energyBuffer : this.buffers) {
				if (control.hasPermission(energyBuffer.getModuleState())) {
					buffers.add(energyBuffer);
				}
			}
		}
		long totalExtract = 0;
		for (E energyBuffer : buffers) {
			IModuleState state = energyBuffer.getModuleState();
			long extract = energyBuffer.extractEnergy(state, facing, maxExtract, simulate);
			if (state != null && extract > 0) {
				state.getModule().sendModuleUpdate(state);
			}
			totalExtract += extract;
			maxExtract -= extract;
			if (maxExtract <= 0) {
				break;
			}
		}
		return totalExtract;
	}

	@Override
	public long receiveEnergy(IModuleState moduleState, EnumFacing facing, long maxReceive, boolean simulate) {
		List<E> buffers = new ArrayList<>(this.buffers);
		if (moduleState != null && moduleState.getModule() instanceof IModuleControlled) {
			buffers.clear();
			IModuleControl control = ((IModuleControlled) moduleState.getModule()).getModuleControl(moduleState);
			for (E energyBuffer : this.buffers) {
				if (control.hasPermission(energyBuffer.getModuleState())) {
					buffers.add(energyBuffer);
				}
			}
		}
		long totalReceived = 0;
		for (E energyBuffer : buffers) {
			IModuleState state = energyBuffer.getModuleState();
			long receive = energyBuffer.receiveEnergy(state, facing, maxReceive, simulate);
			if (state != null && receive > 0) {
				state.getModule().sendModuleUpdate(state);
			}
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
	public void setEnergy(long energy) {
		for (IEnergyBuffer energyBuffer : buffers) {
			long capacity = energyBuffer.getCapacity();
			if (energy > capacity) {
				energyBuffer.setEnergy(capacity);
				energy -= capacity;
			} else {
				energyBuffer.setEnergy(energy);
				break;
			}
			if (energy <= 0) {
				break;
			}
		}
	}

	@Override
	public long getEnergyStored() {
		long energyStored = 0;
		for (IEnergyBuffer energyBuffer : buffers) {
			energyStored += energyBuffer.getEnergyStored();
		}
		return energyStored;
	}

	@Override
	public long getCapacity() {
		long capacity = 0;
		for (IEnergyBuffer energyBuffer : buffers) {
			capacity += energyBuffer.getCapacity();
		}
		return capacity;
	}

	@Override
	public int getTier() {
		int tier = 1;
		for (IEnergyBuffer energyBuffer : buffers) {
			if (energyBuffer.getTier() > tier) {
				tier = energyBuffer.getTier();
			}
		}
		return tier;
	}

	@Override
	public boolean canExtract() {
		return !buffers.isEmpty();
	}

	@Override
	public boolean canReceive() {
		return !buffers.isEmpty();
	}
}