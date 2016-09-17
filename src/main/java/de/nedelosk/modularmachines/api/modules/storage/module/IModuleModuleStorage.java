package de.nedelosk.modularmachines.api.modules.storage.module;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.position.EnumStoragePositions;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.properties.IModuleModuleStorageProperties;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IStorageModule;

public interface IModuleModuleStorage<S extends IAddableModuleStorage & IDefaultModuleStorage> extends IStorageModule, IModuleModuleStorageProperties {

	EnumStoragePositions getCurrentPosition(IModuleState state);

	@Override
	@Nonnull
	@Nullable
	S createStorage(IModuleState state, IModular modular, IStoragePosition position);

}
