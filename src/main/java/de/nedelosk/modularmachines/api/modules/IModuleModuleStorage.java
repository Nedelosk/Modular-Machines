package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IModuleModuleStorage extends IModule, IModuleModuleStorageProperties {

	EnumStoragePosition getCurrentPosition(IModuleState state);

}
