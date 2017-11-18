package modularmachines.api.modules;

import javax.annotation.Nullable;
import java.util.Locale;

import net.minecraft.util.text.translation.I18n;

public enum EnumModuleSizes {
	NONE(0),
	/* S * 1 */
	SMALL(1),
	/* S * 2 */
	/* M * 1 */
	MEDIUM(2),
	/* S * 3 */
	/* M * 1.5 */
	/* L * 1 */
	LARGE(3),
	/* S * 6 */
	/* M * 3 */
	/* L * 2 */
	LARGER(6),
	/* S * 9 */
	/* M * 4.5 */
	/* L * 3 */
	LARGEST(9),
	UNKNOWN(0);
	
	public int slotNumbers;
	public static final EnumModuleSizes[] VALUES = values();
	
	EnumModuleSizes(int slotNumbers) {
		this.slotNumbers = slotNumbers;
	}
	
	public static EnumModuleSizes getSize(@Nullable EnumModuleSizes firstSize, @Nullable EnumModuleSizes secondSize) {
		if (firstSize == null && secondSize != null) {
			return secondSize;
		} else if (firstSize != null && secondSize == null) {
			return firstSize;
		} else if (firstSize == null) {
			return UNKNOWN;
		} else if (firstSize == LARGE && secondSize == LARGE) {
			return LARGER;
		} else if (firstSize == LARGER && secondSize == LARGE) {
			return LARGEST;
		} else if (firstSize == LARGE && secondSize == LARGER) {
			return LARGEST;
		}
		int newSize = firstSize.ordinal() + secondSize.ordinal();
		if (VALUES.length > newSize) {
			return VALUES[newSize];
		}
		return UNKNOWN;
	}
	
	public String getLocalizedName() {
		return I18n.translateToLocal("module.size." + name().toLowerCase(Locale.ENGLISH) + ".name");
	}
	
	public String getName() {
		return name().toLowerCase(Locale.ENGLISH);
	}
}
