package nedelosk.nedeloskcore.common.core;

import net.minecraftforge.common.config.Configuration;

public class NedeloskCoreConfig {

	public static void preInit()
	{
		Configuration config = new Configuration(NedeloskCore.configNedelsokCore);
		config.load();
		
		config.save();
		
	}
	
}
