package de.nedelosk.forestmods.api.modules.storage.capacitors;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.IModuleAddable;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleCapacitor extends IModuleAddable {

	int getSpeedModifier();

	int getEnergyModifier();

	boolean canWork(IModular modular, ModuleStack capacitor);
}