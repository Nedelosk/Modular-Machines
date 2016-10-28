package de.nedelosk.modularmachines.api.modular;

import java.util.Locale;

import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.position.EnumModulePositions;
import de.nedelosk.modularmachines.api.modules.position.IModulePostion;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import net.minecraft.util.text.translation.I18n;

public enum ExpandedStoragePositions implements IStoragePosition {
	CASING(EnumModuleSizes.LARGEST, EnumModulePositions.CASING), LEFT(EnumModuleSizes.LARGE, EnumModulePositions.SIDE, -(float) (Math.PI / 2)), RIGHT(EnumModuleSizes.LARGE, EnumModulePositions.SIDE, (float) (Math.PI / 2)), TOP(EnumModuleSizes.LARGE,
			EnumModulePositions.TOP), BACK(EnumModuleSizes.LARGE, EnumModulePositions.BACK, (float) (Math.PI));

	public static final IStoragePosition[] VALUES = values();
	float rotation;
	EnumModuleSizes size;
	IModulePostion position;

	private ExpandedStoragePositions(EnumModuleSizes size) {
		this(size, null);
	}

	private ExpandedStoragePositions(EnumModuleSizes size, IModulePostion position) {
		this(size, position, 0F);
	}

	private ExpandedStoragePositions(EnumModuleSizes size, IModulePostion position, float rotation) {
		this.size = size;
		this.position = position;
		this.rotation = rotation;
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
		return rotation;
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
		if (position instanceof ExpandedStoragePositions) {
			if (((ExpandedStoragePositions) position).ordinal() > ordinal()) {
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
