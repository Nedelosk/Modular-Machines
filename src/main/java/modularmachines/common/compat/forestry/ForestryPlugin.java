package modularmachines.common.compat.forestry;

import modularmachines.common.compat.ICompatPlugin;

public class ForestryPlugin implements ICompatPlugin {
	
	@Override
	public void preInit() {
		ForestryModuleDefinition.preInit();
	}
}
