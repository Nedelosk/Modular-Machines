package de.nedelosk.modularmachines.api.modules.storages;

import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleBatteryProperties extends IModuleProperties {

	int getCapacity(IModuleState state);

	int getMaxReceive(IModuleState state);

	int getMaxExtract(IModuleState state);

	int getTier(IModuleContainer container);
}
