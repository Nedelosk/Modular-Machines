package modularmachines.api.modules.positions;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;

import modularmachines.api.modules.IModuleHandler;

/**
 * Describes a possible position of a module in a {@link IModuleHandler}.
 */
public interface IModulePosition {
	
	default float getRotationAngle() {
		return 0.0F;
	}
	
	default Vec3d getOffset() {
		return Vec3d.ZERO;
	}
	
	default EnumFacing getFacing() {
		return EnumFacing.fromAngle(-Math.toDegrees(getRotationAngle()));
	}
}
