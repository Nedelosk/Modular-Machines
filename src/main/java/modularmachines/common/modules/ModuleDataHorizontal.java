package modularmachines.common.modules;

import modularmachines.api.modules.positions.EnumCasingPositions;
import modularmachines.common.modules.data.ModuleData;

public class ModuleDataHorizontal extends ModuleData {
	public ModuleDataHorizontal() {
		super(EnumCasingPositions.RIGHT, EnumCasingPositions.LEFT, EnumCasingPositions.FRONT, EnumCasingPositions.BACK);
	}
}
