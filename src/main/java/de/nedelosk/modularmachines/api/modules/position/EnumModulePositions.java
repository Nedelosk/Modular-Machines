package de.nedelosk.modularmachines.api.modules.position;

import java.util.Locale;

import net.minecraft.util.text.translation.I18n;

public enum EnumModulePositions implements IModulePostion {
	CASING, SIDE, TOP, BACK;

	@Override
	public String getLocName() {
		return I18n.translateToLocal("module.storage." + getName() + ".name");
	}

	@Override
	public String getName() {
		return name().toLowerCase(Locale.ENGLISH);
	}

	@Override
	public String toString() {
		return getLocName();
	}
}
