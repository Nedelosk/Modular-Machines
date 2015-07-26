package nedelosk.forestday.common.core;

import java.util.Locale;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.message.MessageFormatMessage;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.oredict.OreDictionary;

public class Defaults {
	
	public static final String MOD = "Forest Day";
	public static final String MOD_ID = "ForestDay";
    public static final String VERSION = "0.1.1";
	
	public static final int BUSRENDERER_ID = RenderingRegistry.getNextAvailableRenderId();
	public static final int BUSRENDERER_METAL_ID = RenderingRegistry.getNextAvailableRenderId();
	
	public static String BLOOD_MAGIC_MOD_ID = "AWWayofTime";
	public static String FORESTRY_MOD_ID = "Forestry";
	public static String GREGTECH_MOD_ID = "gregtech";
	public static String IC2_MOD_ID = "IC2";
	public static String BIOMESOPLENTY_MOD_ID = "BiomesOPlenty";
	public static String RAILCRAFT_MOD_ID = "Railcraft";
	public static String THAUMCRAFT_MOD_ID = "Thaumcraft";
	public static String ThermalExpansion_MOD_ID = "ThermalExpansion";
	public static String NotEnoughItems_MOD_ID = "NotEnoughItems";
	
    public static void log(Level level, String msg, Object... args) {
        LogManager.getLogger(Defaults.MOD_ID).log(level, new MessageFormatMessage(msg, args));
    }
    
    public static void logModule(Level level, String msg, Object... args)
    {
    	LogManager.getLogger("Forestday Module").log(level, new MessageFormatMessage(msg, args));
    }

}
