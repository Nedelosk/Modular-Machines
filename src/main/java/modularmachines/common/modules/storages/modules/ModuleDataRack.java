package modularmachines.common.modules.storages.modules;

import modularmachines.api.modules.positions.EnumCasingPositions;
import modularmachines.common.modules.data.ModuleData;

public class ModuleDataRack extends ModuleData {
	
	public ModuleDataRack() {
		super(EnumCasingPositions.LEFT, EnumCasingPositions.RIGHT);
	}
}
