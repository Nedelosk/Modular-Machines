package nedelosk.modularmachines.common.config;

import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.basic.modular.module.IModule;
import nedelosk.modularmachines.common.ModularMachines;
import net.minecraftforge.common.config.Configuration;

public class ModularConfig {

	public static void load()
	{
		ModularMachines.config.load();
	}
	
	public static void preInit()
	{
		load();
		
		Configuration config = ModularMachines.config;
		
		generateAluminiumOre = config.get("OreGen", "Aluminium", true).getBoolean();
		generateColumbiteOre = config.get("OreGen", "Columbite", true).getBoolean();
		
		save();
	}
	
	public static void postInit()
	{
		load();
		Configuration config = ModularMachines.config;
		for(IModule module : ModularMachinesApi.getModules().values())
		{
			if(!config.get("Modules", module.getName(), true).getBoolean())
				ModularMachinesApi.getModules().remove(module.getName(), module);
		}
		save();
	}
	
	public static void save()
	{
		ModularMachines.config.save();
	}
	
	public static boolean generateColumbiteOre;
	public static boolean generateAluminiumOre;
}
