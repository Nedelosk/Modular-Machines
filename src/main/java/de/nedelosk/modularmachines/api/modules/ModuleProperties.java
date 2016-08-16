package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;

public class ModuleProperties implements IModuleProperties {

	protected final int complexity;
	protected final EnumModuleSize size;

	public ModuleProperties(int complexity, EnumModuleSize size) {
		this.complexity = complexity;
		this.size = size;
	}

	@Override
	public int getComplexity(IModuleContainer container){
		return complexity;
	}

	@Override
	public EnumModuleSize getSize(IModuleContainer container){
		return size;
	}
}
