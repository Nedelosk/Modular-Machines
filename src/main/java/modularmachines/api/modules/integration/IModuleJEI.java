package modularmachines.api.modules.integration;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.state.IModuleState;

public interface IModuleJEI extends IModule {

	String[] getJEIRecipeCategorys(IModuleContainer container);

	void openJEI(IModuleState state);
}
