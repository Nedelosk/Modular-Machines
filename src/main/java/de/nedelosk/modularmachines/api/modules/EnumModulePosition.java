package de.nedelosk.modularmachines.api.modules;

import java.util.Locale;

import net.minecraft.util.text.translation.I18n;

public enum EnumModulePosition {
	TOP, DOWN, BACK, SIDE, INTERNAL;

	public String getLocName(){
		return I18n.translateToLocal("module.storage." + getName() + ".name");
	}

	public String getName(){
		return name().toLowerCase(Locale.ENGLISH);
	}
}
