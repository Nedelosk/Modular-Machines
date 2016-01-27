package nedelosk.modularmachines.api.modules.integration;

import nedelosk.modularmachines.api.modular.integration.IWailaProvider;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;

public interface IModuleWaila<S extends IModuleSaver> extends IModule<S>, IWailaProvider {
}
