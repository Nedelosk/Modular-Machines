package de.nedelosk.modularmachines.api.modules.storaged;

import java.util.Locale;

public enum EnumPosition {
	TOP(0, 3), DOWN(4, 7), BACK(4, 7), LEFT(8, 11), RIGHT(12, 15), INTERNAL(16, 25);

	public int startSlotIndex;
	public int endSlotIndex;

	private EnumPosition(int startSlotIndex, int endSlotIndex) {
		this.startSlotIndex = startSlotIndex;
		this.endSlotIndex = endSlotIndex;
	}

	public String getName(){
		return name().toLowerCase(Locale.ENGLISH);
	}
}