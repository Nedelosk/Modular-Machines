package de.nedelosk.modularmachines.api.modules.storage.module;

import javax.annotation.Nonnull;

import de.nedelosk.modularmachines.api.modules.containers.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.properties.IModuleModuleStorageProperties;
import de.nedelosk.modularmachines.api.modules.storage.IStorageModule;

public interface IModuleModuleStorage<S extends IAddableModuleStorage & IDefaultModuleStorage> extends IStorageModule, IModuleModuleStorageProperties {

	@Override
	@Nonnull
	S createStorage(IModuleProvider provider, IStoragePosition position);
}
