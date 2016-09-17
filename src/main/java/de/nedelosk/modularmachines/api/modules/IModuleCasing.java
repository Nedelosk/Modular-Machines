package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.properties.IModuleBlockModificatorProperties;
import de.nedelosk.modularmachines.api.modules.storage.module.IAddableModuleStorage;
import de.nedelosk.modularmachines.api.modules.storage.module.IDefaultModuleStorage;
import de.nedelosk.modularmachines.api.modules.storage.module.IModuleModuleStorage;

public interface IModuleCasing<S extends IAddableModuleStorage & IDefaultModuleStorage> extends IModuleModuleStorage<S>, IModuleBlockModificatorProperties {
}
