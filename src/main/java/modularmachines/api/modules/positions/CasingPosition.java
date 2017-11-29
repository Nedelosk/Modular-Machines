package modularmachines.api.modules.positions;

import java.util.Locale;

import net.minecraft.util.text.translation.I18n;

/**
 * A enum that contains all possible casing positions.
 */
public enum CasingPosition implements IModulePosition {
	CENTER, //The position of the casing itself
	FRONT, //The position at the front ot the module container
	TOP, //The position at the top ot the module container
	BOTTOM, //The position at the bottom ot the module container
	BACK((float) (Math.PI)), //The position at the back ot the module container
	RIGHT((float) (Math.PI / 2)), //The position at the right side ot the module container
	LEFT(-(float) (Math.PI / 2)); //The position at the left side ot the module container
	
	public static final CasingPosition[] HORIZONTAL = new CasingPosition[]{FRONT, BACK, RIGHT, LEFT};
	public static final CasingPosition[] SIDES = new CasingPosition[]{RIGHT, LEFT};
	public static final CasingPosition[] VERTICAL = new CasingPosition[]{TOP, BOTTOM};
	
	private final float rotation;
	
	CasingPosition() {
		this(0.0F);
	}
	
	CasingPosition(float rotation) {
		this.rotation = rotation;
	}
	
	@Override
	public float getRotationAngle() {
		return rotation;
	}
	
	@SuppressWarnings("deprecation")
	public String getName() {
		return I18n.translateToLocal("modularmachines.position.casing." + toString().toLowerCase(Locale.ENGLISH) + ".name");
	}
}
