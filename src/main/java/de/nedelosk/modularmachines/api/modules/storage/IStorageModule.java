package de.nedelosk.modularmachines.api.modules.storage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface IStorageModule extends IModule, IStorageModuleProperties {

	@Nonnull
	IStorage createStorage(IModuleState state, IModular modular, IStoragePosition position);

	@Nonnull
	IStoragePage createPage(@Nullable IModularAssembler assembler, @Nullable IModular modular, @Nullable IStorage storage, @Nonnull IModuleState state, @Nonnull IStoragePosition position);

}
