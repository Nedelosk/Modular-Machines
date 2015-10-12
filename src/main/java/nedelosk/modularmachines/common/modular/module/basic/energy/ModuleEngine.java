package nedelosk.modularmachines.common.modular.module.basic.energy;

import nedelosk.modularmachines.api.modular.module.basic.Module;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleEngine;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleEngine extends Module implements IModuleEngine {

	public int[] speedModifiers;
	
	public ModuleEngine() {
	}
	
	public ModuleEngine(String modifier, int... speeds) {
		super(modifier);
		this.speedModifiers = speeds;
	}
	
	public ModuleEngine(NBTTagCompound nbt) {
		super(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		speedModifiers = nbt.getIntArray("Speed");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setIntArray("Speed", speedModifiers);
	}
	
	@Override
	public String getModuleName() {
		return "Engine";
	}

	@Override
	public int getSpeedModifier(int tier) {
		return speedModifiers[tier];
	}

}
