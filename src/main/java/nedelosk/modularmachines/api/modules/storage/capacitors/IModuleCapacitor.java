package nedelosk.modularmachines.api.modules.storage.capacitors;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModuleAddable;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IModuleCapacitor extends IModuleAddable {

	int getSpeedModifier();

	int getEnergyModifier();

	boolean canWork(IModular modular, ModuleStack capacitor);
}
