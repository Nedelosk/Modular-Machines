package modularmachines.api.modules.properties;

import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.storage.IStorageModuleProperties;

public interface IModuleModuleStorageProperties extends IStorageModuleProperties {

	int getAllowedComplexity(IModuleContainer container);
}
