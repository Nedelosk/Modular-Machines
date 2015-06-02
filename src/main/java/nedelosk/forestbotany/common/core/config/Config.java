package nedelosk.forestbotany.common.core.config;

import java.io.File;

import nedelosk.forestbotany.common.genetics.allele.AlleleRegistry;
import nedelosk.forestbotany.common.genetics.templates.crop.CropDefinition;
import net.minecraftforge.common.config.Configuration;

public class Config {

	public static void loadCropConfig(File file)
	{
		Configuration config = new Configuration(file);
		config.load();
		AlleleRegistry.setConfig(config);
		CropDefinition.initCrops(config);
		config.save();
	}
	
	public static void loadConfig(File file)
	{
		Configuration config = new Configuration(file);
		config.load();
		mutationTime = config.get("Botany", "Mutation Time", 7200).getInt(7200);
		config.save();
	}
	
	public static int mutationTime;
	
}
