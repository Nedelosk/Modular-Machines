package de.nedelosk.modularmachines.api.modules;

import java.util.Locale;

import net.minecraft.util.text.translation.I18n;

public enum EnumStoragePosition {
	TOP(0, 3), DOWN(4, 7), BACK(4, 7), LEFT(8, 11), RIGHT(12, 15), INTERNAL(16, 25);

	public int startSlotIndex;
	public int endSlotIndex;

	private EnumStoragePosition(int startSlotIndex, int endSlotIndex) {
		this.startSlotIndex = startSlotIndex;
		this.endSlotIndex = endSlotIndex;
	}

	public String getLocName(){
		return I18n.translateToLocal("module.storage." + getName() + ".name");
	}

	public String getName(){
		return name().toLowerCase(Locale.ENGLISH);
	}
}
