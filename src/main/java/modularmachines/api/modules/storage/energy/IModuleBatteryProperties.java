package modularmachines.api.modules.storage.energy;

import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.storage.IStorageModuleProperties;

public interface IModuleBatteryProperties extends IStorageModuleProperties {

	int getCapacity(IModuleState state);

	int getMaxReceive(IModuleState state);

	int getMaxExtract(IModuleState state);

	int getTier(IModuleContainer container);
}
