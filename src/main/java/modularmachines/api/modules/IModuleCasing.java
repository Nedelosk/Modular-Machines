package modularmachines.api.modules;

import modularmachines.api.modules.properties.IModuleBlockModificatorProperties;
import modularmachines.api.modules.storage.module.IAddableModuleStorage;
import modularmachines.api.modules.storage.module.IDefaultModuleStorage;
import modularmachines.api.modules.storage.module.IModuleModuleStorage;

public interface IModuleCasing<S extends IAddableModuleStorage & IDefaultModuleStorage> extends IModuleModuleStorage<S>, IModuleBlockModificatorProperties {
}
