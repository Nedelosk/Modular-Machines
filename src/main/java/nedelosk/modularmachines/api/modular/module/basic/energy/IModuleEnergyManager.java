package nedelosk.modularmachines.api.modular.module.basic.energy;

import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleManager;
import nedelosk.modularmachines.api.modular.module.basic.gui.IModuleGui;

public interface IModuleEnergyManager extends IModuleGui, IModuleManager {

	int getSpeedModifier();
	
}
