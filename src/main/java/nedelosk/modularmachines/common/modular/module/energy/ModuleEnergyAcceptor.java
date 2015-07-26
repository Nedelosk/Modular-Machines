package nedelosk.modularmachines.common.modular.module.energy;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.module.IModule;
import nedelosk.modularmachines.api.modular.module.Module;
import nedelosk.modularmachines.api.modular.module.ModuleStack;
import nedelosk.modularmachines.api.modular.module.energy.IModuleEnergyAcceptor;
import nedelosk.modularmachines.common.modular.config.ModularConfig;
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
