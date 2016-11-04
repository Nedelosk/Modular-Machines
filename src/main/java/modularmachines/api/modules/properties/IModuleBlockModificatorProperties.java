package modularmachines.api.modules.properties;

import modularmachines.api.modules.IModuleProperties;
import modularmachines.api.modules.handlers.IModuleContentHandler;
import modularmachines.api.modules.handlers.block.IBlockModificator;
import modularmachines.api.modules.state.IModuleState;

public interface IModuleBlockModificatorProperties extends IModuleProperties {

	<B extends IBlockModificator & IModuleContentHandler> B createBlockModificator(IModuleState state);
}
