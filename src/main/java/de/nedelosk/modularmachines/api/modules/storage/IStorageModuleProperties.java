package de.nedelosk.modularmachines.api.modules.storage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;

public interface IStorageModuleProperties extends IModuleProperties {

	@Nullable
	IStoragePosition getSecondPosition(@Nonnull IModuleContainer container, @Nonnull IStoragePosition position);

	boolean isValidForPosition(@Nonnull IStoragePosition position, @Nonnull IModuleContainer container);
}
