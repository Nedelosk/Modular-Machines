package nedelosk.modularmachines.common.modular.module.energy;

import nedelosk.modularmachines.api.modular.module.Module;
import nedelosk.modularmachines.api.modular.module.energy.IModuleBattery;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleBattery extends Module implements IModuleBattery {

	public int energyStored;
	
	public ModuleBattery() {
	}
	
	public ModuleBattery(String modifier, int energyStored) {
		super(modifier);
		this.energyStored = energyStored;
	}
	
	public ModuleBattery(NBTTagCompound nbt) {
		super(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("EnergyStored", energyStored);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.energyStored = nbt.getInteger("EnergyStored");
	}

	@Override
	public String getModuleName() {
		return "Battery";
	}

	@Override
	public int getMaxEnergyStored() {
		return energyStored;
	}

}
