package de.nedelosk.modularmachines.common.plugins.cofh;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.nedelosk.modularmachines.api.energy.EnergyRegistry;
import de.nedelosk.modularmachines.api.energy.IEnergyInterface;
import de.nedelosk.modularmachines.api.energy.IEnergyType;
import net.minecraft.util.EnumFacing;

public class EnergyInterfaceRF implements IEnergyInterface {

	public IEnergyReceiver eR;
	public IEnergyProvider eP;
	public IEnergyHandler eH;
	public EnumFacing facing;

	public EnergyInterfaceRF(IEnergyReceiver eR, IEnergyProvider eP, IEnergyHandler eH, EnumFacing facing) {
		this.eR = eR;
		this.eP = eP;
		this.eH = eH;
		this.facing = facing;
	}

	@Override
	public IEnergyType[] getValidTypes() {
		return new IEnergyType[]{EnergyRegistry.redstoneFlux};
	}

	@Override
	public long extractEnergy(IEnergyType energyType, long maxExtract, boolean simulate) {
		if(eP != null){
			return eP.extractEnergy(facing, (int) maxExtract, simulate);
		}
		return 0;
	}

	@Override
	public long receiveEnergy(IEnergyType energyType, long maxReceive, boolean simulate) {
		if(eR != null){
			return eR.receiveEnergy(facing, (int) maxReceive, simulate);
		}
		return 0;
	}

	@Override
	public boolean isInput(IEnergyType energyType) {
		return eR != null;
	}

	@Override
	public boolean isOutput(IEnergyType energyType) {
		return eP != null;
	}

	@Override
	public boolean canConnectEnergy(IEnergyType energyType) {
		return eP != null && eP.canConnectEnergy(facing) || eR != null && eR.canConnectEnergy(facing) || eH != null && eH.canConnectEnergy(facing);
	}

	@Override
	public long getEnergyStored(IEnergyType energyType) {
		return eH == null ? 0 : eH.getEnergyStored(facing);
	}

	@Override
	public long getCapacity(IEnergyType energyType) {
		return eH == null ? 0 : eH.getMaxEnergyStored(facing);
	}

	@Override
	public EnumFacing getFacing() {
		return facing;
	}
}
