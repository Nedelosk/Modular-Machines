package de.nedelosk.forestmods.library;

import java.util.Locale;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

public enum RedstoneMode {
	IGNORE, ON, OFF;

	public String getTooltip() {
		return StatCollector.translateToLocal("gui.tooltip.redstoneMode." + name().toLowerCase(Locale.ENGLISH));
	}

	public static boolean isConditionMet(RedstoneMode redstoneControlMode, int powerLevel) {
		boolean redstoneCheckPassed = true;
		if (redstoneControlMode == RedstoneMode.ON) {
			if (powerLevel < 1) {
				redstoneCheckPassed = false;
			}
		} else if (redstoneControlMode == RedstoneMode.OFF) {
			if (powerLevel > 0) {
				redstoneCheckPassed = false;
			}
		}
		return redstoneCheckPassed;
	}

	public static boolean isConditionMet(RedstoneMode redstoneControlMode, TileEntity te) {
		return isConditionMet(redstoneControlMode, te.getWorldObj().getStrongestIndirectPower(te.xCoord, te.yCoord, te.zCoord));
	}

	public RedstoneMode next() {
		int ord = ordinal();
		if (ord == values().length - 1) {
			ord = 0;
		} else {
			ord++;
		}
		return values()[ord];
	}

	public RedstoneMode previous() {
		int ord = ordinal();
		ord--;
		if (ord < 0) {
			ord = values().length - 1;
		}
		return values()[ord];
	}
}
