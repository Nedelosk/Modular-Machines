package modularmachines.api.modules.storage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import modularmachines.api.modules.IModuleProperties;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.position.IStoragePosition;

public interface IStorageModuleProperties extends IModuleProperties {

	@Nullable
	IStoragePosition getSecondPosition(@Nonnull IModuleContainer container, @Nonnull IStoragePosition position);

	boolean isValidForPosition(@Nonnull IStoragePosition position, @Nonnull IModuleContainer container);
}
