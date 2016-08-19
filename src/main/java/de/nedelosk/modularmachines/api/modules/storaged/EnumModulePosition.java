package de.nedelosk.modularmachines.api.modules.storaged;

import java.util.Locale;

import de.nedelosk.modularmachines.common.utils.Translator;

public enum EnumModulePosition {
	TOP, DOWN, BACK, SIDE, INTERNAL;

	public String getLocName(){
		return Translator.translateToLocal("module.storage." + getName() + ".name");
	}

	public String getName(){
		return name().toLowerCase(Locale.ENGLISH);
	}
}
