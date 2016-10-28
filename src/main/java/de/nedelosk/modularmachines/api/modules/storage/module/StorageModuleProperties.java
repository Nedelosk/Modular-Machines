package de.nedelosk.modularmachines.api.modules.storage.module;

import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.ModuleProperties;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.position.EnumModulePositions;
import de.nedelosk.modularmachines.api.modules.position.IModulePostion;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.storage.IStorageModuleProperties;

public class StorageModuleProperties extends ModuleProperties implements IStorageModuleProperties {

	protected final IModulePostion[] positions;

	public StorageModuleProperties(int complexity, IModulePostion... positions) {
		super(complexity);
		this.positions = positions;
	}

	@Override
	public IStoragePosition getSecondPosition(IModuleContainer container, IStoragePosition position) {
		return null;
	}

	@Override
	public boolean isValidForPosition(IStoragePosition storagePosition, IModuleContainer container) {
		for(IModulePostion pos : storagePosition.getPostions()) {
			for(IModulePostion position : positions) {
				if (pos == position) {
					if (position == EnumModulePositions.CASING && !(container.getModule() instanceof IModuleCasing)) {
						continue;
					}
					return true;
				}
			}
		}
		return false;
	}
}
