package modularmachines.api.modules.positions;

import net.minecraft.util.EnumFacing;

import modularmachines.api.modules.IModuleHandler;

/**
 * Describes a possible position of a module in a {@link IModuleHandler}.
 */
public interface IModulePosition {
	
	default float getRotationAngle() {
		return 0.0F;
	}
	
	default EnumFacing getFacing() {
		return EnumFacing.fromAngle(-Math.toDegrees(getRotationAngle()));
	}
}
