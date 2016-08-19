package de.nedelosk.modularmachines.api.modules.storaged;

import java.util.Locale;

import de.nedelosk.modularmachines.common.utils.Translator;

public enum EnumStoragePosition {
	TOP(0, 3), DOWN(4, 7), BACK(4, 7), LEFT(8, 11), RIGHT(12, 15), INTERNAL(16, 25);

	public int startSlotIndex;
	public int endSlotIndex;

	private EnumStoragePosition(int startSlotIndex, int endSlotIndex) {
		this.startSlotIndex = startSlotIndex;
		this.endSlotIndex = endSlotIndex;
	}

	public String getLocName(){
		return Translator.translateToLocal("module.storage." + getName() + ".name");
	}

	public String getName(){
		return name().toLowerCase(Locale.ENGLISH);
	}
}
