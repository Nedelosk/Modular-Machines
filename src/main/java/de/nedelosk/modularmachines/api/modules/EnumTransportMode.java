package de.nedelosk.modularmachines.api.modules;

import java.util.Locale;

import de.nedelosk.modularmachines.api.recipes.IToolMode;

public enum EnumTransportMode implements IToolMode {
	
	INPUT, OUTPUT;

	@Override
	public String getName() {
		return name().toLowerCase(Locale.ENGLISH);
	}
}
