package modularmachines.common.compat.thermalexpansion;

import modularmachines.common.compat.ICompatPlugin;

public class TEPlugin implements ICompatPlugin {
	
	@Override
	public void preInit() {
		TEModuleDefinition.preInit();
	}
}
