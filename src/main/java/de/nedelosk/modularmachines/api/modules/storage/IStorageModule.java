package de.nedelosk.modularmachines.api.modules.storage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.containers.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;

public interface IStorageModule extends IModule, IStorageModuleProperties {

	@Nonnull
	IStorage createStorage(IModuleProvider provider, IStoragePosition position);

	@Nonnull
	IStoragePage createPage(@Nullable IModularAssembler assembler, @Nullable IModular modular, @Nullable IStorage storage, @Nonnull IStoragePosition position);

	@Nonnull
	IStoragePage createSecondPage(@Nonnull IStoragePosition position);

}
