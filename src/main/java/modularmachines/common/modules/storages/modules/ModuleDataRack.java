package modularmachines.common.modules.storages.modules;

import modularmachines.api.modules.EnumCasingPositions;
import modularmachines.api.modules.ModuleData;

public class ModuleDataRack extends ModuleData {
	
	public ModuleDataRack() {
		super(EnumCasingPositions.LEFT, EnumCasingPositions.RIGHT);
	}
}
