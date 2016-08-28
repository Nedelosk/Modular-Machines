package de.nedelosk.modularmachines.api.modules;

import java.util.Locale;

import net.minecraft.util.text.translation.I18n;

public enum EnumModuleSize {
	UNKNOWN, SMALL, MEDIUM, LARGE;

	public static final EnumModuleSize[] VALUES = values();

	public static EnumModuleSize getNewSize(EnumModuleSize firstSize, EnumModuleSize secondSize){
		if(firstSize == null && secondSize != null){
			return secondSize;
		}else if(firstSize != null && secondSize == null){
			return firstSize;
		}else if(firstSize == null && secondSize == null){
			return UNKNOWN;
		}
		int newSize = firstSize.ordinal() + secondSize.ordinal();
		if(VALUES.length > newSize){
			return VALUES[newSize];
		}
		return UNKNOWN;
	}

	public String getLocalizedName(){
		return I18n.translateToLocal("module.size." + name().toLowerCase(Locale.ENGLISH) + ".name");
	}

	public String getName(){
		return name().toLowerCase(Locale.ENGLISH);
	}
}
