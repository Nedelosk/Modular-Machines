package modularmachines.api.modules.storage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import modularmachines.api.modular.IModular;
import modularmachines.api.modular.IModularAssembler;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.containers.IModuleProvider;
import modularmachines.api.modules.position.IStoragePosition;

public interface IStorageModule extends IModule, IStorageModuleProperties {

	@Nonnull
	IStorage createStorage(IModuleProvider provider, IStoragePosition position);

	@Nonnull
	IStoragePage createPage(@Nullable IModularAssembler assembler, @Nullable IModular modular, @Nullable IStorage storage, @Nonnull IStoragePosition position);

	@Nonnull
	IStoragePage createSecondPage(@Nonnull IStoragePosition position);
}
