package nedelosk.modularmachines.common.modular.module.energy;

import java.util.ArrayList;

import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.module.IModule;
import nedelosk.modularmachines.api.modular.module.IModuleEngine;
import nedelosk.modularmachines.api.modular.module.Module;
import nedelosk.modularmachines.api.modular.module.ModuleStack;
import nedelosk.modularmachines.common.modular.config.ModularConfig;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleEngine extends Module implements IModuleEngine {

	public int energy;
	
	public ModuleEngine(int energy) {
		this.energy = energy;
	}
	
	public ModuleEngine(String modifier, int energy) {
		super(modifier);
		this.energy = energy;
	}
	
	public ModuleEngine(NBTTagCompound nbt) {
		super(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		energy = nbt.getInteger("Energy");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Energy", energy);
	}

	@Override
	public int getEngineModifier() {
		return energy;
	}
	
	@Override
	public String getModuleName() {
		return "engine";
	}

}
