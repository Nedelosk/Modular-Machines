package de.nedelosk.modularmachines.common.modular.handlers;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IModuleBattery;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModule;
import net.minecraft.util.EnumFacing;

public class EnergyHandler implements IEnergyProvider, IEnergyReceiver {

	public IModular modular;

	public EnergyHandler(IModular machine) {
		this.modular = machine;
	}


	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return !modular.getModules(IModuleBattery.class).isEmpty();
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		for(IModuleState<IModuleBattery> state : modular.getModules(IModuleBattery.class)){
			IModuleBattery battery = state.getModule();
			int energy = battery.getStorage(state).receiveEnergy(maxReceive, true);
			if (energy > 0) {
				PacketHandler.INSTANCE.sendToAll(new PacketModule(modular.getHandler(), state));
				battery.getStorage(state).extractEnergy(maxReceive, simulate);
				return energy;
			}
		}
		return 0;
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		for(IModuleState<IModuleBattery> state : modular.getModules(IModuleBattery.class)){
			IModuleBattery battery = state.getModule();
			int energy = battery.getStorage(state).extractEnergy(maxExtract, true);
			if (energy > 0) {
				PacketHandler.INSTANCE.sendToAll(new PacketModule(modular.getHandler(), state));
				battery.getStorage(state).extractEnergy(maxExtract, simulate);
				return energy;
			}
		}
		return 0;
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		int energy = 0;
		for(IModuleState<IModuleBattery> state : modular.getModules(IModuleBattery.class)){
			IModuleBattery battery = state.getModule();
			energy+=battery.getStorage(state).getEnergyStored();
		}
		return energy;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		int maxEnergy = 0;
		for(IModuleState<IModuleBattery> state : modular.getModules(IModuleBattery.class)){
			IModuleBattery battery = state.getModule();
			maxEnergy+=battery.getStorage(state).getMaxEnergyStored();
		}
		return maxEnergy;
	}
}
