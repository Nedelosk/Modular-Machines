package de.nedelosk.forestmods.library.modules.storage;

import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modules.IModule;

public interface IModuleCapacitor extends IModule {

	int getSpeedModifier();

	int getEnergyModifier();

	boolean canWork(IModular modular);
}
