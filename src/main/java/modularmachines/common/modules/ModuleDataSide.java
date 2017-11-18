package modularmachines.common.modules;

import modularmachines.api.modules.EnumRackPositions;
import modularmachines.api.modules.ModuleData;

public class ModuleDataSide extends ModuleData {
	
	public ModuleDataSide() {
		super(EnumRackPositions.UP, EnumRackPositions.CENTER, EnumRackPositions.DOWN);
	}
	
}
