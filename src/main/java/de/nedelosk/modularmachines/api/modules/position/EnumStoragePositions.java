package de.nedelosk.modularmachines.api.modules.position;

import java.util.Locale;

import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import net.minecraft.util.text.translation.I18n;

public enum EnumStoragePositions implements IStoragePosition{
	CASING(EnumModuleSizes.LARGEST, EnumModulePositions.CASING), LEFT(EnumModuleSizes.LARGE, EnumModulePositions.SIDE, -(float) (Math.PI / 2)), RIGHT(EnumModuleSizes.LARGE, EnumModulePositions.SIDE, (float) (Math.PI / 2)), TOP(EnumModuleSizes.LARGE), BACK(EnumModuleSizes.LARGE);

	float rotation;
	EnumModuleSizes size;
	IModulePostion position;

	private EnumStoragePositions(EnumModuleSizes size) {
		this(size, null);
	}

	private EnumStoragePositions(EnumModuleSizes size, IModulePostion position) {
		this(size, position, 0F);
	}

	private EnumStoragePositions(EnumModuleSizes size, IModulePostion position, float rotation) {
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
	public String getLocName(){
		return I18n.translateToLocal("module.storage." + getName() + ".name");
	}

	@Override
	public String getName(){
		return name().toLowerCase(Locale.ENGLISH);
	}

	@Override
	public IModulePostion[] getPostions() {
		return new IModulePostion[]{position};
	}

	@Override
	public int getProperty(IStoragePosition position) {
		if(position instanceof EnumStoragePositions){
			if(((EnumStoragePositions) position).ordinal() > ordinal()){
				return -1;
			}
			return 1;
		}
		return 1;
	}
}
