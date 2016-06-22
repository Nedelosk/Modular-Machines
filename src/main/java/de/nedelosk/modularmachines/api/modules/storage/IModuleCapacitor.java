package de.nedelosk.modularmachines.api.modules.storage;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModule;

public interface IModuleCapacitor extends IModule {

	int getSpeedModifier();

	int getEnergyModifier();

	boolean canWork(IModular modular);
}
