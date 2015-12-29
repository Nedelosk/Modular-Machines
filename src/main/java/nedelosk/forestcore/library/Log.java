package nedelosk.forestcore.library;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.message.MessageFormatMessage;

import cpw.mods.fml.common.Loader;

public class Log {

	public static void log(String modID, Level level, String msg, Object... args) {
		LogManager.getLogger(modID).log(level, new MessageFormatMessage(msg, args));
	}

	public static void log(Level level, String msg, Object... args) {
		log(Loader.instance().activeModContainer().getModId(), level, msg, args);
	}

	public static void logPluginManager(Level level, String msg, Object... args) {
		log(Loader.instance().activeModContainer().getModId() + " PluginManager", level, msg, args);
	}

	public static void err(String msg, Object... args) {
		log(Loader.instance().activeModContainer().getModId() + " ERROR", Level.ERROR, msg, args);
	}

	public static void info(String msg, Object... args) {
		log(Loader.instance().activeModContainer().getModId() + " INFO", Level.INFO, msg, args);
	}

}
