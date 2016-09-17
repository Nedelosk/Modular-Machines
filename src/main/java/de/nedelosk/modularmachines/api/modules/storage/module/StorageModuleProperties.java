package de.nedelosk.modularmachines.api.modules.storage.module;

import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.ModuleProperties;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.position.EnumModulePositions;
import de.nedelosk.modularmachines.api.modules.position.IModulePostion;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.storage.IStorageModuleProperties;

public class StorageModuleProperties extends ModuleProperties implements IStorageModuleProperties {

	protected final IModulePostion position;

	public StorageModuleProperties(int complexity, EnumModuleSizes size, IModulePostion position) {
		super(complexity, size);
		this.position = position;
	}

	@Override
	public boolean isValidForPosition(IStoragePosition position, IModuleContainer container) {
		for(IModulePostion pos : position.getPostions()){
			if(pos == this.position){
				if(this.position == EnumModulePositions.CASING && !(container.getModule() instanceof IModuleCasing)){
					return false;
				}
				return true;
			}
		}
		return false;
	}
}
