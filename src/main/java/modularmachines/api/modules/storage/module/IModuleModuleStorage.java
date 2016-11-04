package modularmachines.api.modules.storage.module;

import javax.annotation.Nonnull;

import modularmachines.api.modules.containers.IModuleProvider;
import modularmachines.api.modules.position.IStoragePosition;
import modularmachines.api.modules.properties.IModuleModuleStorageProperties;
import modularmachines.api.modules.storage.IStorageModule;

public interface IModuleModuleStorage<S extends IAddableModuleStorage & IDefaultModuleStorage> extends IStorageModule, IModuleModuleStorageProperties {

	@Override
	@Nonnull
	S createStorage(IModuleProvider provider, IStoragePosition position);
}
