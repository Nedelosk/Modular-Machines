package de.nedelosk.modularmachines.api.modules.storage.energy;

import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IStorageModuleProperties;

public interface IModuleBatteryProperties extends IStorageModuleProperties {

	int getCapacity(IModuleState state);

	int getMaxReceive(IModuleState state);

	int getMaxExtract(IModuleState state);

	int getTier(IModuleContainer container);
}
