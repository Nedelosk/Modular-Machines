package de.nedelosk.modularmachines.api.modular;

import java.util.Locale;

public enum EnumLogicType implements IModularLogicType {
	ENGINE_STORAGE;

	int maxLogics;


	private EnumLogicType() {
		this(1);
	}

	private EnumLogicType(int maxLogics) {
		this.maxLogics = maxLogics;
	}

	@Override
	public String getName() {
		return name().toLowerCase(Locale.ENGLISH);
	}

	@Override
	public int getMaxLogics() {
		return maxLogics;
	}

}
