package nedelosk.nedeloskcore.common.core;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.message.MessageFormatMessage;

public class Log {

    public static void log(String modID, Level level, String msg, Object... args) {
        LogManager.getLogger(modID).log(level, new MessageFormatMessage(msg, args));
    }
    
    public static void log(Level level, String msg, Object... args) {
        log("Nedelosk Core", level, msg, args);
    }
    
    public static void err(String msg, Object... args)
    {
    	log("Nedelosk Core Error", Level.ERROR, msg, args);
    }
	
}
