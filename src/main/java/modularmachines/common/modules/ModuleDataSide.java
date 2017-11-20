package modularmachines.common.modules;

import modularmachines.api.modules.positions.EnumRackPositions;
import modularmachines.common.modules.data.ModuleData;

public class ModuleDataSide extends ModuleData {
	
	public ModuleDataSide() {
		super(EnumRackPositions.UP, EnumRackPositions.CENTER, EnumRackPositions.DOWN);
	}
	
}
