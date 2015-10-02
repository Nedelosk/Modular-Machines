package nedelosk.modularmachines.common.machines.module.energy;

import nedelosk.modularmachines.api.modular.module.basic.basic.Module;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleBattery;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleBattery extends Module implements IModuleBattery {

	public int energyStored;
    protected int maxReceive;
    protected int maxExtract;
	
	public ModuleBattery() {
	}
	
	public ModuleBattery(String modifier, int energyStored, int maxReceive, int maxExtract) {
		super(modifier);
		this.energyStored = energyStored;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
	}
	
	public ModuleBattery(NBTTagCompound nbt) {
		super(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("EnergyStored", energyStored);
		nbt.setInteger("MaxReceive", maxReceive);
		nbt.setInteger("MaxExtract", maxExtract);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.energyStored = nbt.getInteger("EnergyStored");
		this.maxReceive = nbt.getInteger("MaxReceive");
		this.maxExtract = nbt.getInteger("MaxExtract");
	}

	@Override
	public String getModuleName() {
		return "Battery";
	}

	@Override
	public int getMaxEnergyStored() {
		return energyStored;
	}
	
	@Override
	public int getMaxEnergyReceive() {
		return maxReceive;
	}
	
	@Override
	public int getMaxEnergyExtract() {
		return maxExtract;
	}

}
