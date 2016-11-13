package modularmachines.api.modular;

import java.util.Locale;

import net.minecraft.util.text.translation.I18n;

import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.position.EnumModulePositions;
import modularmachines.api.modules.position.IModulePostion;
import modularmachines.api.modules.position.IStoragePosition;

public enum SingleStoragePositions implements IStoragePosition {
	CASING(EnumModuleSizes.LARGEST, EnumModulePositions.CASING), SIDE(EnumModuleSizes.LARGE, EnumModulePositions.SIDE), TOP(EnumModuleSizes.LARGE, EnumModulePositions.TOP);

	public static final IStoragePosition[] VALUES = values();
	EnumModuleSizes size;
	IModulePostion position;

	private SingleStoragePositions(EnumModuleSizes size) {
		this(size, null);
	}

	private SingleStoragePositions(EnumModuleSizes size, IModulePostion position) {
		this.size = size;
		this.position = position;
	}

	public IModulePostion getPosition() {
		return position;
	}

	@Override
	public EnumModuleSizes getSize() {
		return size;
	}

	@Override
	public float getRotation() {
		return 0F;
	}

	@Override
	public String getLocName() {
		return I18n.translateToLocal("module.storage." + getName() + ".name");
	}

	@Override
	public String getName() {
		return name().toLowerCase(Locale.ENGLISH);
	}

	@Override
	public IModulePostion[] getPostions() {
		return new IModulePostion[] { position };
	}

	@Override
	public int getProperty(IStoragePosition position) {
		if (position instanceof SingleStoragePositions) {
			if (((SingleStoragePositions) position).ordinal() > ordinal()) {
				return -1;
			}
			return 1;
		}
		return 1;
	}

	@Override
	public String toString() {
		return getLocName();
	}
}
