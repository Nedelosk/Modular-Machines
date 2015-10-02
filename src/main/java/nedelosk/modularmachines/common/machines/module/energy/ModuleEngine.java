package nedelosk.modularmachines.common.machines.module.energy;

import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleWithItem;
import nedelosk.modularmachines.api.modular.module.basic.basic.Module;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleEngine;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleEngine extends Module implements IModuleEngine, IModuleWithItem {

	public int speed;
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
		speed = nbt.getInteger("Speed");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Speed", speed);
	}

	@Override
	public int getSpeedModifier() {
		return speed;
	}
	
	@Override
	public String getModuleName() {
		return "Engine";
	}

	@Override
	public void writeToItemNBT(NBTTagCompound nbt, int tier) {
		nbt.setInteger("Speed", speedModifiers[tier]);
		
	}

}
