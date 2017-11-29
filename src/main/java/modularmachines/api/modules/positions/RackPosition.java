package modularmachines.api.modules.positions;

import java.util.Locale;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;

/**
 * A enum that contains all possible rack positions.
 */
public enum RackPosition implements IModulePosition {
	UP(0.0F), //The position of the upper slot of the rack
	CENTER(-0.25F), //The position of the center slot of the rack
	DOWN(-0.5F); //The position of the lower slot of the rack
	
	private float yOffset;
	
	RackPosition(float yOffset) {
		this.yOffset = yOffset;
	}
	
	@Override
	public Vec3d getOffset() {
		return new Vec3d(0.0F, yOffset, 0.0F);
	}
	
	@SuppressWarnings("deprecation")
	public String getName() {
		return I18n.translateToLocal("modularmachines.position.rack." + toString().toLowerCase(Locale.ENGLISH) + ".name");
	}
}
