package nedelosk.modularmachines.common.machines.module.energy;

import nedelosk.modularmachines.api.basic.machine.modular.IModular;
import nedelosk.modularmachines.api.basic.machine.module.Module;
import nedelosk.modularmachines.api.basic.machine.module.energy.IModuleCapacitor;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleCapacitor extends Module implements IModuleCapacitor {

	public IModular modular;
	public int speedModifier;
	public int energyModifier;
	
	public ModuleCapacitor(String modifier, int speedModifier, int energyModifier) {
		super(modifier);
		this.speedModifier = speedModifier;
		this.energyModifier = energyModifier;
	}
	
	public ModuleCapacitor(int speedModifier, int energyModifier) {
		this.speedModifier = speedModifier;
		this.energyModifier = energyModifier;
	}
	
	public ModuleCapacitor() {
	}
	
	public ModuleCapacitor(NBTTagCompound nbt) {
		super(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.energyModifier = nbt.getInteger("energyModifier");
		this.speedModifier = nbt.getInteger("speedModifier");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("energyModifier", energyModifier);
		nbt.setInteger("speedModifier", speedModifier);
	}

	@Override
	public int getSpeedModifier() {
		return speedModifier;
	}

	@Override
	public int getEnergyModifier() {
		return energyModifier;
	}

	@Override
	public String getModuleName() {
		return "Capacitor";
	}

	@Override
	public boolean canWork(IModular modular) {
		return true;
	}

}
