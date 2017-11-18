package modularmachines.common.modules;

import modularmachines.api.modules.EnumCasingPositions;
import modularmachines.api.modules.ModuleData;

public class ModuleDataHorizontal extends ModuleData {
	public ModuleDataHorizontal() {
		super(EnumCasingPositions.RIGHT, EnumCasingPositions.LEFT, EnumCasingPositions.FRONT, EnumCasingPositions.BACK);
	}
}
