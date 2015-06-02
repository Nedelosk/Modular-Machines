package nedelosk.forestday.common.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ModuleConfig {
	
	public static void loadConfig(File file)
	{
		Configuration config = new Configuration(file);
		
		config.load();
		
		loadModuleWood = config.get("Module", "Module Wood", true, "Load Module Wood").getBoolean();
		
		config.save();
		
	}
	
	public static boolean loadModuleWood;

}
