package modularmachines.api.modules.properties;

import modularmachines.api.modules.IModuleProperties;
import modularmachines.api.modules.containers.IModuleContainer;

public interface IModuleControllerProperties extends IModuleProperties {

	int getAllowedComplexity(IModuleContainer container);
}
