package modularmachines.api.modules.positions;

import java.util.Locale;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;

/**
 * A enum that contains all possible rack positions.
 */
public enum RackPosition implements IModulePosition {
	UP_LEFT(0.0F, 0.0F), //The position of the upper slot of the rack
	UP_RIGHT(0.375F, 0.0F),
	DOWN_LEFT(0.0F, -0.375F), //The position of the lower slot of the rack
	DOWN_RIGHT(0.375F, -0.375F);
	
	private float xOffset;
	private float yOffset;
	
	RackPosition(float xOffset, float yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	@Override
	public Vec3d getOffset() {
		return new Vec3d(xOffset, yOffset, 0.0F);
	}
	
	@SuppressWarnings("deprecation")
	public String getName() {
		return I18n.translateToLocal("modularmachines.position." + getUID() + ".name");
	}
	
	@Override
	public String getUID() {
		return "rack." + name().toLowerCase(Locale.ENGLISH);
	}
}
