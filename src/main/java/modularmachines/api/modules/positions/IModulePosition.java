package modularmachines.api.modules.positions;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;

import modularmachines.api.modules.IModuleHandler;

/**
 * Describes a possible position of a module in a {@link IModuleHandler}.
 */
public interface IModulePosition {
	
	/**
	 * @return Localized short, human-readable identifier used in the module tooltip.
	 */
	String getName();
	
	/**
	 * @return The rotation angle of the position.
	 */
	default float getRotationAngle() {
		return 0.0F;
	}
	
	/**
	 * @return The offset of the position.
	 */
	default Vec3d getOffset() {
		return Vec3d.ZERO;
	}
	
	/**
	 * @return The facing of the position relative to the world.
	 */
	default EnumFacing getFacing() {
		return EnumFacing.fromAngle(-Math.toDegrees(getRotationAngle()));
	}
}
