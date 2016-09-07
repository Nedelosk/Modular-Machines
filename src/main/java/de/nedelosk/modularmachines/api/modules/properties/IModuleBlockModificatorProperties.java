package de.nedelosk.modularmachines.api.modules.properties;

import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.block.IBlockModificator;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleBlockModificatorProperties extends IModuleProperties {

	<B extends IBlockModificator & IModuleContentHandler> B createBlockModificator(IModuleState state);
}
