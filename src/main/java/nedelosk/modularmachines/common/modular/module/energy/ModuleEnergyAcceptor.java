package nedelosk.modularmachines.common.modular.module.energy;

import nedelosk.modularmachines.api.modular.module.Module;
import nedelosk.modularmachines.api.modular.module.energy.IModuleEnergyAcceptor;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleEnergyAcceptor extends Module implements IModuleEnergyAcceptor {

	public ModuleEnergyAcceptor() {
	}
	
	public ModuleEnergyAcceptor(NBTTagCompound nbt) {
		super(nbt);
	}

	@Override
	public String getModuleName() {
		return "energyacceptor";
	}

}
