package nedelosk.nedeloskcore.common.core;

import net.minecraftforge.common.config.Configuration;

public class NedeloskCoreConfig {

	public static void preInit()
	{
		Configuration config = new Configuration(NedeloskCore.configNedelsokCore);
		config.load();
		generateOre = config.get("World Generation", "Ore Generation", new boolean[]{ true, true, true, true, true }, "Ore Generation for Copper, Tin, Silver, Lead, Nickel.").getBooleanList();
		config.save();
		
	}
	
	public static boolean[] generateOre;
	
}
