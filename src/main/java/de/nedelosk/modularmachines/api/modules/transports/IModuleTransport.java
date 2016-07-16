package de.nedelosk.modularmachines.api.modules.transports;

import de.nedelosk.modularmachines.api.modules.IModule;

public interface IModuleTransport extends IModule {

	EnumTransportMode getMode();

}
