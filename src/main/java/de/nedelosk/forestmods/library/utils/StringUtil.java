package de.nedelosk.forestmods.library.utils;

import java.util.IllegalFormatException;

import net.minecraft.util.StatCollector;

public class StringUtil {

	public static String localizeAndFormatRaw(String key, Object... args) {
		String text = StatCollector.translateToLocal(key).replace("\\n", "\n").replace("@", "%").replace("\\%", "@");
		try {
			return String.format(text, args);
		} catch (IllegalFormatException ex) {
			return "Format error: " + text;
		}
	}
}
