package nedelosk.modularmachines.api.modules.energy;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IModuleCapacitor extends IModule {

	int getSpeedModifier();

	int getEnergyModifier();

	boolean canWork(IModular modular, ModuleStack capacitor);
}
