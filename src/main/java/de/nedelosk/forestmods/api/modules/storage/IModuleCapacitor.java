package de.nedelosk.forestmods.api.modules.storage;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleCapacitor extends IModule {

	int getSpeedModifier();

	int getEnergyModifier();

	boolean canWork(IModular modular, ModuleStack capacitor);
}
