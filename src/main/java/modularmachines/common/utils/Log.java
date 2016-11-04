package modularmachines.common.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.message.MessageFormatMessage;

import modularmachines.common.core.Constants;

public class Log {

	public static void log(String modID, Level level, String msg, Object... args) {
		LogManager.getLogger(modID).log(level, new MessageFormatMessage(msg, args));
	}

	public static void log(Level level, String msg, Object... args) {
		log(Constants.NAME, level, msg, args);
	}

	public static void logPluginManager(Level level, String msg, Object... args) {
		log(Constants.NAME + " PluginManager", level, msg, args);
	}

	public static void err(String msg, Object... args) {
		log(Constants.NAME, Level.ERROR, msg, args);
	}

	public static void info(String msg, Object... args) {
		log(Constants.NAME, Level.INFO, msg, args);
	}

	public static void severe(String message, Object param) {
		log(Level.FATAL, message, param);
	}

	public static void severe(String message, Object... params) {
		log(Level.FATAL, message, params);
	}

	public static void warn(String message, Object... params) {
		log("Modular Machines", Level.WARN, message, params);
	}

	public static void fatal(String message, Object... params) {
		log("Modular Machines", Level.FATAL, message, params);
	}
}
