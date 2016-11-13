package modularmachines.api.modules.storage.module;

import modularmachines.api.modules.IModuleCasing;
import modularmachines.api.modules.ModuleProperties;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.position.EnumModulePositions;
import modularmachines.api.modules.position.IModulePostion;
import modularmachines.api.modules.position.IStoragePosition;
import modularmachines.api.modules.storage.IStorageModuleProperties;

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
		for (IModulePostion pos : storagePosition.getPostions()) {
			for (IModulePostion position : positions) {
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
