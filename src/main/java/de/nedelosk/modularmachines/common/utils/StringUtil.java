package de.nedelosk.modularmachines.common.utils;

import java.util.IllegalFormatException;

public class StringUtil {

	public static String localizeAndFormatRaw(String key, Object... args) {
		String text = Translator.translateToLocal(key).replace("\\n", "\n").replace("@", "%").replace("\\%", "@");
		try {
			return String.format(text, args);
		} catch (IllegalFormatException ex) {
			return "Format error: " + text;
		}
	}
}
