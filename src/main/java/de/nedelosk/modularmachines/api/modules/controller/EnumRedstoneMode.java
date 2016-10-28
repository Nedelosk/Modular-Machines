package de.nedelosk.modularmachines.api.modules.controller;

import java.util.Locale;

import net.minecraft.util.text.translation.I18n;

public enum EnumRedstoneMode {
	ON, OFF, IGNORE;

	public static EnumRedstoneMode[] VALUES = values();

	public EnumRedstoneMode next() {
		int next = ordinal() + 1;
		if (VALUES.length == next) {
			next = 0;
		}
		return VALUES[next];
	}

	public EnumRedstoneMode previous() {
		int previous = ordinal() - 1;
		if (previous < 0) {
			previous = VALUES.length - 1;
		}
		return VALUES[previous];
	}

	public String getLocName() {
		return I18n.translateToLocal("mode.redstone." + getName() + ".name");
	}

	public String getName() {
		return name().toLowerCase(Locale.ENGLISH);
	}
}
