package de.nedelosk.modularmachines.api.modules.storaged.tools;

import de.nedelosk.modularmachines.api.modules.ModuleProperties;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;

public class ModuleBoilerProperties extends ModuleProperties implements IModuleBoilerProperties {

	protected final int waterPerWork;

	public ModuleBoilerProperties(int complexity, EnumModuleSize size, int waterPerWork) {
		super(complexity, size);

		this.waterPerWork = waterPerWork;
	}

	@Override
	public int getWaterPerWork(IModuleState state) {
		return waterPerWork;
	}
}
